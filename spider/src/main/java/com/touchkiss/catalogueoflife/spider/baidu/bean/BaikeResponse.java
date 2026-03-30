package com.touchkiss.catalogueoflife.spider.baidu.bean;

import com.touchkiss.catalogueoflife.response.BaseResponse;

import java.util.List;

/**
 * Created on 2020/04/07 16:58
 *
 * @author Touchkiss
 */
public class BaikeResponse extends BaseResponse<List<BaikeItemResponse>> {
    public void addAllProperties(List<BaikeItemResponse> items) {
        getData().addAll(items);
    }

    public void addProperty(String key, String value) {
        getData().add(new BaikeItemResponse(key, value));
    }
}
