package com.touchkiss.catalogueoflife.es.repositories.impl;

import com.google.gson.reflect.TypeToken;
import com.touchkiss.catalogueoflife.es.annotations.EsDocument;
import com.touchkiss.catalogueoflife.es.annotations.EsField;
import com.touchkiss.catalogueoflife.es.repositories.BaseRepository;
import com.touchkiss.catalogueoflife.es.utils.EsUtil;
import com.touchkiss.catalogueoflife.utils.GsonUtil;
import com.touchkiss.catalogueoflife.utils.MapUtil;
import com.touchkiss.catalogueoflife.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ES 基础仓储实现
 *
 * <p>封装 High Level REST Client 的 CRUD 及搜索操作。
 * {@code RestHighLevelClient} 自 ES 7.15 起标记为 deprecated，待服务端升级至 ES 8.x 后
 * 应迁移至官方 {@code ElasticsearchClient}（elasticsearch-java）。</p>
 *
 * @author Touchkiss
 */
@SuppressWarnings("deprecation")
@Service
@Slf4j
public class BaseRepositoryImpl implements BaseRepository {
    protected static final String IGNORE_REGEX = "[-,:,/\"]";

    protected final RestHighLevelClient restHighLevelClient;

    public BaseRepositoryImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public boolean checkIndexExists(Class clazz) {
        String index = EsUtil.getEsTableAnnotation(clazz).index();
        try {
            return restHighLevelClient.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("查询index是否存在出错, index: {}", index, e);
        }
        return false;
    }

    @Override
    public CreateIndexResponse createIndex(Class clazz) {
        EsDocument esDocument = EsUtil.getEsTableAnnotation(clazz);
        String index = esDocument.index();
        try {
            if (checkIndexExists(clazz)) {
                log.info("创建index--出错--index已存在, index:{}", index);
                return null;
            }

            Map<String, Object> properties = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(EsField.class))
                    .collect(Collectors.toMap(Field::getName, field -> {
                        EsField esField = field.getAnnotation(EsField.class);
                        Map<String, Object> propMap = new HashMap<>(8);
                        propMap.put("type", EsUtil.fitFieldType(field));
                        if (StringUtils.isNotBlank(esField.analyzer())) {
                            propMap.put("analyzer", esField.analyzer());
                        }
                        if (StringUtils.isNotBlank(esField.search_analyzer())) {
                            propMap.put("searchAnalyzer", esField.search_analyzer());
                        }
                        propMap.put("index", esField.index());
                        return propMap;
                    }));

            Map<String, Object> mapping = new HashMap<>(4);
            mapping.put("properties", properties);

            CreateIndexRequest createIndexRequest = new CreateIndexRequest(index)
                    .settings(Settings.builder()
                            .put("index.number_of_shards", esDocument.shards())
                            .put("index.number_of_replicas", esDocument.replicas()))
                    .mapping(mapping);
            return restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("创建index出错, index: {}", index, e);
        }
        return null;
    }

    @Override
    public AcknowledgedResponse deleteIndex(Class clazz) {
        String index = EsUtil.getEsTableAnnotation(clazz).index();
        try {
            return restHighLevelClient.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("删除index出错, index: {}", index, e);
        }
        return null;
    }

    @Override
    public IndexResponse save(Object obj) {
        EsDocument esDocument = EsUtil.getEsTableAnnotation(obj.getClass());
        try {
            return restHighLevelClient.index(getIndexRequest(esDocument, obj), RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es保存数据出错, index: {}, obj: {}", esDocument.index(), obj, e);
        }
        return null;
    }

    @Override
    public BulkResponse msave(@Nullable Object... objs) {
        try {
            if (ArrayUtils.isEmpty(objs)) {
                log.info("es保存数据不能为空");
                return null;
            }
            Object firstDoc = Arrays.stream(objs).filter(Objects::nonNull).findFirst().orElse(null);
            if (firstDoc == null) {
                log.info("es保存数据不能为空");
                return null;
            }
            EsDocument esDocument = EsUtil.getEsTableAnnotation(firstDoc.getClass());
            BulkRequest bulkRequest = new BulkRequest();
            Arrays.stream(objs)
                    .filter(Objects::nonNull)
                    .map(obj -> getIndexRequest(esDocument, obj))
                    .forEach(bulkRequest::add);
            return restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es批量保存数据出错, objs: {}", Arrays.stream(objs).map(Object::toString).collect(Collectors.joining(";")), e);
        }
        return null;
    }

    private IndexRequest getIndexRequest(EsDocument esDocument, Object obj) {
        IndexRequest request = new IndexRequest(esDocument.index());
        Map<String, Object> source = MapUtil.toMap(obj, false);
        if (source.containsKey(esDocument.id())) {
            request.id(source.get(esDocument.id()).toString());
        }
        request.source(source, XContentType.JSON);
        return request;
    }

    BoolQueryBuilder buildQueryBuilder(Map<String, Object> must, Map<String, Object> should, Map<String, Object> must_not) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        HighlightBuilder highlight = new HighlightBuilder();
        boolean mustNotEmpty = !CollectionUtils.isEmpty(must), shouldNotEmpty = !CollectionUtils.isEmpty(should), must_notNotEmpty = !CollectionUtils.isEmpty(must_not);
        if (mustNotEmpty) {
            must.entrySet().stream()
                    .filter(entry -> !entry.getKey().matches(IGNORE_REGEX))
                    .forEach(entry -> {
                        String key = entry.getKey();
                        String value = (entry.getValue() instanceof String ? entry.getValue().toString() : GsonUtil.toJson(entry.getValue())).trim();
                        if (!StringUtils.isAnyBlank(key, value)) {
                            if (value.startsWith("[") && value.endsWith("]")) {
                                BoolQueryBuilder child = addShould(key, value);
                                boolquery.must(child);
                            } else {
                                if (!value.matches(IGNORE_REGEX)) {
                                    boolquery.must(QueryBuilders.matchQuery(key, value));
                                }
                            }
                        }
                        highlight.field(key);
                    });
        }
        if (shouldNotEmpty) {
            should.entrySet().stream()
                    .filter(entry -> !entry.getKey().matches(IGNORE_REGEX))
                    .forEach(entry -> {
                        String key = entry.getKey();
                        String value = (entry.getValue() instanceof String ? entry.getValue().toString() : GsonUtil.toJson(entry.getValue())).trim();
                        if (!StringUtils.isAnyBlank(key, value)) {
                            if (value.startsWith("[") && value.endsWith("]")) {
                                BoolQueryBuilder child = addShould(key, value);
                                boolquery.should(child);
                            } else {
                                if (!value.matches(IGNORE_REGEX)) {
                                    boolquery.should(QueryBuilders.matchQuery(key, value));
                                }
                            }
                        }
                        highlight.field(key);
                    });
        }
        if (must_notNotEmpty) {
            must_not.entrySet().stream()
                    .filter(entry -> !entry.getKey().matches(IGNORE_REGEX))
                    .forEach(entry -> {
                        String key = entry.getKey();
                        String value = (entry.getValue() instanceof String ? entry.getValue().toString() : GsonUtil.toJson(entry.getValue())).trim();
                        if (!StringUtils.isAnyBlank(key, value)) {
                            if (value.startsWith("[") && value.endsWith("]")) {
                                BoolQueryBuilder child = addShould(key, value);
                                boolquery.mustNot(child);
                            } else {
                                if (!value.matches(IGNORE_REGEX)) {
                                    boolquery.mustNot(QueryBuilders.matchQuery(key, value));
                                }
                            }
                        }
                        highlight.field(key);
                    });
        }
        return boolquery;
    }

    @Override
    public SearchResponse search(String indexs, String orderBy, boolean isAsc, String[] fields, String simpleQueryString, Map<String, Object> must, Map<String, Object> should, Map<String, Object> must_not, Map<String, List<Object>> ranges, int from, int pageSize) {
        try {
            if (StringUtils.isBlank(indexs)) {
                indexs = "_all";
            }
            SearchSourceBuilder search = new SearchSourceBuilder()
                    .size(pageSize)
                    .from(from);
            BoolQueryBuilder boolquery = buildQueryBuilder(must, should, must_not);
            if (!CollectionUtils.isEmpty(must) || !CollectionUtils.isEmpty(should) || !CollectionUtils.isEmpty(must_not)) {
                search.query(boolquery);
            } else if (StringUtils.isNotBlank(simpleQueryString)) {
                search.query(QueryBuilders.simpleQueryStringQuery(simpleQueryString));
            }
            search.explain(false);
            if (StringUtils.isNotBlank(orderBy)) {
                search.sort(new FieldSortBuilder(orderBy).order(isAsc ? SortOrder.ASC : SortOrder.DESC));
            }
            if (fields != null && fields.length > 0) {
                String[] excludes = new String[]{"_type"};
                search.fetchSource(fields, excludes);
            }

            SearchRequest request = new SearchRequest()
                    .searchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .source(search)
                    .indices(indexs.split(","));
            return restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es查询数据出错, indexs: {}, must: {}, should: {}, must_not: {}", indexs, must, should, must_not, e);
        }
        return null;
    }

    @Override
    public CountResponse count(String indexs, String simpleQueryString, Map<String, Object> must, Map<String, Object> should, Map<String, Object> must_not) {
        try {
            if (StringUtils.isBlank(indexs)) {
                indexs = "_all";
            }
            CountRequest countRequest = new CountRequest(indexs.split(","));
            BoolQueryBuilder boolquery = buildQueryBuilder(must, should, must_not);
            if (!CollectionUtils.isEmpty(must) || !CollectionUtils.isEmpty(should) || !CollectionUtils.isEmpty(must_not)) {
                countRequest.query(boolquery);
            } else if (StringUtils.isNotBlank(simpleQueryString)) {
                countRequest.query(QueryBuilders.simpleQueryStringQuery(simpleQueryString));
            }
            return restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es查询数据数量出错, indexs: {}, must: {}, should: {}, must_not: {}", indexs, must, should, must_not, e);
        }
        return null;
    }

    private BoolQueryBuilder addShould(String key, String value) {
        BoolQueryBuilder child = QueryBuilders.boolQuery();
        List<String> values = GsonUtil.fromJson(value, new TypeToken<List<String>>() {
        }.getType());
        values.stream()
                .filter(v -> !v.matches(IGNORE_REGEX))
                .forEach(v -> child.should(QueryBuilders.matchQuery(key, v)));
        return child;
    }

    @Override
    public GetResponse get(Class clazz, Object id) {
        String index = EsUtil.getEsTableAnnotation(clazz).index();
        try {
            return restHighLevelClient.get(new GetRequest(index, id.toString()), RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es获取数据出错, index: {}, id: {}", index, id, e);
        }
        return null;
    }

    @Override
    public MultiGetResponse mget(Class clazz, Object... ids) {
        EsDocument esDocument = EsUtil.getEsTableAnnotation(clazz);
        String index = esDocument.index();
        try {
            if (ArrayUtils.isEmpty(ids)) {
                log.info("es获取数据---出错--id列表不能为空");
                return null;
            }
            MultiGetRequest multiGetRequest = new MultiGetRequest();
            Arrays.stream(ids).forEach(id -> multiGetRequest.add(index, id.toString()));
            return restHighLevelClient.mget(multiGetRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es批量获取数据出错, index: {}, ids: {}", index, Arrays.toString(ids), e);
        }
        return null;
    }

    @Override
    public BulkResponse mdelete(Class clazz, Object... ids) {
        EsDocument esDocument = EsUtil.getEsTableAnnotation(clazz);
        String index = esDocument.index();
        try {
            if (ArrayUtils.isEmpty(ids)) {
                log.info("es删除数据---出错--id列表不能为空");
                return null;
            }
            BulkRequest bulkRequest = new BulkRequest();
            Arrays.stream(ids)
                    .filter(StringUtil::notNullOrBlank)
                    .map(id -> new DeleteRequest(index, id.toString()))
                    .forEach(bulkRequest::add);
            return restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es批量删除数据出错, index: {}, ids: {}", index, Arrays.toString(ids), e);
        }
        return null;
    }

    @Override
    public DeleteResponse delete(Class clazz, Object id) {
        String index = EsUtil.getEsTableAnnotation(clazz).index();
        try {
            return restHighLevelClient.delete(new DeleteRequest(index, id.toString()), RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es删除数据出错, index: {}, id: {}", index, id, e);
        }
        return null;
    }

    @Override
    public UpdateResponse update(Class clazz, Map<String, Object> updates, Object id) {
        EsDocument esDocument = EsUtil.getEsTableAnnotation(clazz);
        String index = esDocument.index();
        try {
            Set<String> fieldNames = Arrays.stream(clazz.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toSet());
            return restHighLevelClient.update(new UpdateRequest(index, id.toString()).doc(
                    updates.entrySet().stream()
                            .filter(entry -> fieldNames.contains(entry.getKey()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                    , XContentType.JSON), RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es更新数据出错, index: {}, id: {}", index, id, e);
        }
        return null;
    }

    @Override
    public BulkResponse mupdate(Class clazz, Map<String, Object> updates, Object... ids) {
        EsDocument esDocument = EsUtil.getEsTableAnnotation(clazz);
        String index = esDocument.index();
        try {
            if (ArrayUtils.isEmpty(ids)) {
                log.info("es更新数据---出错--id列表不能为空");
                return null;
            }
            BulkRequest bulkRequest = new BulkRequest();
            Set<String> fieldNames = Arrays.stream(clazz.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toSet());
            Map<String, Object> finalUpdates = updates.entrySet().stream()
                    .filter(entry -> fieldNames.contains(entry.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            Arrays.stream(ids).filter(StringUtil::notNullOrBlank)
                    .map(id -> new UpdateRequest(index, id.toString()).doc(finalUpdates, XContentType.JSON))
                    .forEach(bulkRequest::add);
            return restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es批量更新数据出错, index: {}, ids: {}", index, Arrays.toString(ids), e);
        }
        return null;
    }

}
