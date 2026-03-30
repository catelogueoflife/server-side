package com.touchkiss.catalogueoflife.spider.species2000cn.processor;

import com.google.gson.reflect.TypeToken;
import com.touchkiss.catalogueoflife.spider.species2000cn.bean.TreeChildrenItemResponse;
import com.touchkiss.catalogueoflife.spider.species2000cn.bean.TreeItemResponse;
import com.touchkiss.catalogueoflife.utils.GsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class Species2000CnProcessor implements PageProcessor {
    public static final String BRANKETS_REGEX = "<['a-z =\\\\-\\\\\\\"/_0-9]+>";
    public static final Pattern MATCH_CN = Pattern.compile("[\\u4e00-\\u9fa5]+");

    /**
     * CSRF Token，在认证回调时自动刷新，使用 AtomicReference 保证可见性
     */
    private static final AtomicReference<String> TOKEN =
            new AtomicReference<>("62b11a75-93af-4961-aa0d-696479dc5ab1");
    /**
     * JSESSIONID Cookie，在认证回调时自动刷新，使用 AtomicReference 保证可见性
     */
    private static final AtomicReference<String> COOKIE =
            new AtomicReference<>("BDF64CD5C3C78E3DABD5D739A1AA6A6D");

    public static Request buildDataRequest(String id) {
        Request request = new Request("http://www.sp2000.org.cn/browse/taxa_tree_children");
        request.setMethod(HttpConstant.Method.POST);
        Map<String, Object> params = new HashMap<>();
        params.put("otherParam", "zTreeAsyncTest");
        if (StringUtils.isNotBlank(id)) {
            params.put("id", id);
        }
        request.setRequestBody(HttpRequestBody.form(params, "UTF-8"));
        request.addHeader("X-CSRF-TOKEN", TOKEN.get());
        request.addHeader("Cookie", "JSESSIONID=" + COOKIE.get());
        request.putExtra("isData", true);
        request.putExtra("originalId", id);
        return request;
    }

    @Override
    public void process(Page page) {
        if (page.getRequest().getExtra("isData") != null) {
            String response = page.getRawText();
            ArrayList<TreeChildrenItemResponse> responseArrayList = null;
            try {
                responseArrayList = GsonUtil.fromJson(response, new TypeToken<ArrayList<TreeChildrenItemResponse>>() {
                }.getType());
            } catch (Exception e) {
                // Not JSON maybe
            }
            if (CollectionUtils.isEmpty(responseArrayList)) {
                if (response.contains("Forbidden")) {
                    Request authRequest = new Request("http://www.sp2000.org.cn/browse/browse_taxa");
                    authRequest.putExtra("isAuth", true);
                    authRequest.putExtra("originalId", page.getRequest().getExtra("originalId"));
                    page.addTargetRequest(authRequest);
                    page.setSkip(true);
                }
            } else {
                List<TreeItemResponse> parsedData = responseArrayList.stream().map(treeChildrenItemResponse -> {
                    String pureName = treeChildrenItemResponse.getName().replaceAll(BRANKETS_REGEX, "").replaceAll("[ \t]+", " ");
                    TreeItemResponse treeItemResponse = new TreeItemResponse();
                    treeItemResponse.setId(treeChildrenItemResponse.getId());
                    Matcher matcher = MATCH_CN.matcher(pureName);
                    if (matcher.find()) {
                        treeItemResponse.setCnName(matcher.group(0));
                        pureName = pureName.substring(0, pureName.indexOf(treeItemResponse.getCnName()));
                    } else {
                        treeItemResponse.setCnName(pureName);
                    }
                    if (StringUtils.startsWithAny(pureName, "Animalia", "Bacteria", "Chromista", "Fungi", "Plantae", "Protozoa", "Viruses")) {
                        treeItemResponse.setEnName(pureName.substring(0, pureName.indexOf(" ")));
                    } else if (StringUtils.startsWithAny(pureName, "Phylum", "Class", "Order", "Family", "Genus")) {
                        treeItemResponse.setEnName(pureName.split(" ")[1]);
                    } else {
                        treeItemResponse.setEnName(pureName.trim());
                    }
                    treeItemResponse.setClassName(judgeClassName(pureName));
                    treeItemResponse.setParent(treeChildrenItemResponse.isParent());
                    treeItemResponse.setPinyin(treeChildrenItemResponse.getPinyin());
                    treeItemResponse.setUrl(treeChildrenItemResponse.getUrl());
                    return treeItemResponse;
                }).collect(Collectors.toList());
                page.putField("results", parsedData);
            }
        } else if (page.getRequest().getExtra("isAuth") != null) {
            String token = page.getHtml().xpath("//input[@name='_csrf']/@value").get();
            if (token != null) {
                TOKEN.set(token);
            }
            if (page.getHeaders() != null && page.getHeaders().containsKey("Set-Cookie")) {
                List<String> cookies = page.getHeaders().get("Set-Cookie");
                if (!CollectionUtils.isEmpty(cookies)) {
                    String value = cookies.get(0);
                    if (value.contains("JSESSIONID=")) {
                        int start = value.indexOf("JSESSIONID=") + 11;
                        int end = value.indexOf(";", start);
                        if (end > start) {
                            COOKIE.set(value.substring(start, end));
                        }
                    }
                }
            }
            page.addTargetRequest(buildDataRequest((String) page.getRequest().getExtra("originalId")));
            page.setSkip(true);
        }
    }

    private String judgeClassName(String pureName) {
        String firstName = pureName.split("[ \t]")[0];
        switch (firstName) {
            case "Animalia":
            case "Bacteria":
            case "Chromista":
            case "Fungi":
            case "Plantae":
            case "Protozoa":
            case "Viruses":
                return "界";
            case "Phylum":
                return "门";
            case "Class":
                return "纲";
            case "Order":
                return "目";
            case "Family":
                return "科";
            case "Genus":
                return "属";
            default:
                return "种";
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(3).setSleepTime(100).setDisableCookieManagement(true);
    }
}
