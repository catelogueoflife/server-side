package com.touchkiss.catalogueoflife.es.services;

import com.touchkiss.catalogueoflife.es.annotations.EsField;
import com.touchkiss.catalogueoflife.es.bean.ESPage;
import com.touchkiss.catalogueoflife.es.repositories.BaseRepository;
import com.touchkiss.catalogueoflife.es.utils.EsUtil;
import com.touchkiss.catalogueoflife.utils.MapUtil;
import com.touchkiss.catalogueoflife.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2020/04/09 23:40
 *
 * @author Touchkiss
 */
@Slf4j
public abstract class BaseESServiceImpl<T> implements BaseESService<T> {
    protected final BaseRepository baseRepository;

    protected BaseESServiceImpl(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public List<T> getMulti(String[] fields, Object... ids) {
        try {
            MultiGetResponse mget = baseRepository.mget(getTClass(), ids);
            if (ArrayUtils.isEmpty(fields)) {
                return Arrays.stream(mget.getResponses())
                        .map(MultiGetItemResponse::getResponse)
                        .map(GetResponse::getSourceAsMap)
                        .map(m -> MapUtil.mapToObject(m, getTClass()))
                        .collect(Collectors.toList());
            } else {
                Set<String> fieldSet = Arrays.stream(fields).collect(Collectors.toSet());
                return Arrays.stream(mget.getResponses())
                        .map(MultiGetItemResponse::getResponse)
                        .map(GetResponse::getSourceAsMap)
                        .map(m -> m.entrySet().stream()
                                .filter(ma -> fieldSet.contains(ma.getKey()))
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                        .map(m -> MapUtil.mapToObject(m, getTClass()))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("es获取多数据出错, ids: {}", Arrays.toString(ids), e);
        }
        return Collections.emptyList();
    }

    @Override
    public ESPage<T> search(String[] fields, Map<String, Object> must, Map<String, Object> should, Map<String, Object> must_not, int pageNo, int pageSize) {
        try {
            pageSize = pageSize == 0 ? 10 : pageSize;
            int from = pageNo <= 0 ? 0 : ((pageNo - 1) * pageSize);
            String datatableorderby = CollectionUtils.isEmpty(must) ? "" : (must.containsKey("datatableorderby") ? must.get("datatableorderby").toString() : "");
            datatableorderby = datatableorderby.contains(",") ? datatableorderby.substring(0, datatableorderby.indexOf(",")) : datatableorderby;
            String orderby = (datatableorderby.contains("order by") ? datatableorderby.substring(datatableorderby.indexOf("order by") + 8).trim() : "").trim();
            orderby = StringUtils.isBlank(orderby) ? "" : orderby.substring(0, orderby.indexOf(" "));
            SearchResponse search = baseRepository.search(EsUtil.getEsTableAnnotation(getTClass()).index(), analyseOrderby(orderby), datatableorderby.contains("asc"), fields, null, filterMap(must), filterMap(should), filterMap(must_not), null, from, pageSize);
            return new ESPage<>(search.getHits().getTotalHits().value, Arrays.stream(search.getHits().getHits())
                    .map(hit -> MapUtil.mapToObject(hit.getSourceAsMap(), getTClass()))
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("es分页查询数据出错, pageNo: {}, pageSize: {}", pageNo, pageSize, e);
        }
        return null;
    }

    @Override
    public long count(Map<String, Object> must, Map<String, Object> should, Map<String, Object> must_not) {
        try {
            return baseRepository.count(EsUtil.getEsTableAnnotation(getTClass()).index(), null, must, should, must_not).getCount();
        } catch (Exception e) {
            log.error("es查询数据数量出错", e);
        }
        return 0L;
    }

    @Override
    public boolean batchUpdate(Map<String, Object> updates, Object... ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return false;
        }
        try {
            return Arrays.stream(baseRepository.mupdate(getTClass(), updates, ids).getItems())
                    .noneMatch(BulkItemResponse::isFailed);
        } catch (Exception e) {
            log.error("es批量更新数据出错, ids: {}", Arrays.toString(ids), e);
        }
        return false;
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        try {
            IndexResponse indexResponse = baseRepository.save(entity);
            DocWriteResponse.Result result = indexResponse.getResult();
            boolean createSuccess = DocWriteResponse.Result.CREATED.equals(result);
            if (createSuccess) {
                Field idField = getTClass().getDeclaredField(EsUtil.getEsTableAnnotation(getTClass()).id());
                idField.setAccessible(true);
                if (StringUtil.isNullOrBlank(idField.get(entity))) {
                    idField.set(entity, indexResponse.getId());
                    return saveOrUpdate(entity);
                }
            }
            return createSuccess || DocWriteResponse.Result.UPDATED.equals(result);
        } catch (Exception e) {
            log.error("es保存或更新数据出错, entity: {}", entity, e);
        }
        return false;
    }

    @Override
    public boolean delete(Object... ids) {
        try {
            return Arrays.stream(baseRepository.mdelete(getTClass(), ids).getItems())
                    .map(BulkItemResponse::getResponse)
                    .map(obj -> (DocWriteResponse) obj)
                    .map(DocWriteResponse::getResult)
                    .allMatch(DocWriteResponse.Result.DELETED::equals);
        } catch (Exception e) {
            log.error("es删除数据出错, ids: {}", Arrays.toString(ids), e);
        }
        return false;
    }

    @Override
    public T getOne(Object id) {
        try {
            Class<T> tClass = getTClass();
            return MapUtil.mapToObject(baseRepository.get(tClass, id).getSource(), tClass);
        } catch (Exception e) {
            log.error("es获取单条数据出错, id: {}", id, e);
        }
        return null;
    }

    Class<T> getTClass() {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }

    Map<String, Object> filterMap(Map<String, Object> map) {
        if (CollectionUtils.isEmpty(map)) {
            return map;
        } else {
            Set<String> fieldNames = Arrays.stream(getTClass().getDeclaredFields()).map(Field::getName).collect(Collectors.toSet());
            return map.entrySet().stream().filter(stringObjectEntry -> fieldNames.contains(stringObjectEntry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

    String analyseOrderby(String orderby) {
        try {
            Field declaredField = getTClass().getDeclaredField(orderby);
            if (declaredField.isAnnotationPresent(EsField.class)) {
                if ("java.lang.String".equals(declaredField.getType().getName())) {
                    EsField esField = declaredField.getAnnotation(EsField.class);
                    if (esField.type().equals(EsField.FieldType.Auto) || esField.type().equals(EsField.FieldType.Text)) {
                        return null;
                    }
                }
                return orderby;
            }
        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
        }
        return null;
    }
}
