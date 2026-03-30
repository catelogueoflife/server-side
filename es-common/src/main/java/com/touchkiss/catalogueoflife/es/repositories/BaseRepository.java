package com.touchkiss.catalogueoflife.es.repositories;

import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020/04/09 11:42
 * <p>
 * 请不要直接使用该接口
 *
 * @author Touchkiss
 */
public interface BaseRepository {
    boolean checkIndexExists(Class clazz);

    CreateIndexResponse createIndex(Class clazz);

    AcknowledgedResponse deleteIndex(Class clazz);

    IndexResponse save(Object obj);

    BulkResponse msave(Object... obj);

    SearchResponse search(String indexs, String orderBy, boolean isAsc, String[] fields, String simpleQueryString, Map<String, Object> must, Map<String, Object> should, Map<String, Object> must_not, Map<String, List<Object>> ranges, int from, int pageSize);

    CountResponse count(String indexs, String simpleQueryString, Map<String, Object> must, Map<String, Object> should, Map<String, Object> must_not);

    GetResponse get(Class clazz, Object id);

    MultiGetResponse mget(Class clazz, Object... ids);

    BulkResponse mdelete(Class clazz, Object... ids);

    DeleteResponse delete(Class clazz, Object id);

    UpdateResponse update(Class clazz, Map<String, Object> updates, Object id);

    BulkResponse mupdate(Class clazz, Map<String, Object> updates, Object... ids);

}
