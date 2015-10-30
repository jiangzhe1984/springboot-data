package sanjiang.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import sanjiang.model.NativeQueryModel;
import sanjiang.model.PageModel;
import sanjiang.model.Pageable;
import sanjiang.model.SearchCoditionModel;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/9/18.
 */
public class BaseService<T> {


    @Autowired
    private EntityManager entityManager;

    /**
     * 根据sql分页查询
     * @param nativeQueryModel 本地查询sql Model
     * @param clazz 返回记录类型
     * @return 分页对象
     */
    protected PageModel nativeSearch(NativeQueryModel nativeQueryModel, Class clazz) {
        long total    = this.nativeCount(nativeQueryModel);
        List contents = null;
        if (total > 0) {
            contents = this.nativeContents(nativeQueryModel, clazz);
        }
        if (null == contents) {
            contents = new ArrayList();
        }
        return new PageModel<T>(contents, total, new Pageable(nativeQueryModel.getPage(), nativeQueryModel.getSize()));
    }

    /**
     * 根据sql查询列表
     * @param nativeQueryModel 本地查询sql Model
     * @param clazz 返回记录类型
     * @return 列表
     */
    protected List nativeContents(NativeQueryModel nativeQueryModel, Class clazz) {
        String contentSql = new StringBuffer(" SELECT ").append(nativeQueryModel.getSelectFields()).append(nativeQueryModel.getFromWhereClause()).append(" ORDER BY ").append(nativeQueryModel.getOrderByClause()).toString();
        Query query = this.createNativeQuery(contentSql, clazz);
        if (null != nativeQueryModel.getPage() && null != nativeQueryModel.getSize()) {
            int page = nativeQueryModel.getPage();
            int size = nativeQueryModel.getSize();
            query.setFirstResult((page * size));
            query.setMaxResults(size);
        }
        this.setQueryParams(query, nativeQueryModel.getParams());
        return query.getResultList();
    }

    /**
     * 根据sql查询对象Query统计记录总数
     * @param nativeQueryModel nativeQueryModel
     * @return long 记录总数
     */
    protected long nativeCount(NativeQueryModel nativeQueryModel) {
        String countSql = new StringBuffer(" SELECT count(1) ").append(nativeQueryModel.getFromWhereClause()).toString();
        return this.count(this.createNativeQuery(countSql, nativeQueryModel.getParams()));
    }

    /**
     * 根据sql查询对象Query统计记录总数
     * @param query Query
     * @return long 记录总数
     */
    private Long count(Query query) {
        BigInteger count = (BigInteger) query.getSingleResult();
        return null != count ? count.longValue() : 0L;
    }

    /**
     * 创建SQL查询对象
     * @param sql sql查询语句
     * @param params 参数数组
     * @return Query query
     */
    private Query createNativeQuery(String sql, List<Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        this.setQueryParams(query, params);
        return query;
    }

    /**
     * 创建SQL查询对象
     * @param sql sql查询语句
     * @param clazz
     * @return Query query
     */
    private Query createNativeQuery(String sql, Class clazz) {
        if (null == clazz) {
            return entityManager.createNativeQuery(sql);
        }
        return entityManager.createNativeQuery(sql, clazz);
    }

    /**
     * 设置查询参数键值对<位置参数>
     * @param query Query 语句构造器
     * @param params 查询参数值
     */
    private void setQueryParams(Query query, List<Object> params) {
        Assert.notNull(query);
        if (!CollectionUtils.isEmpty(params)) {
            int i = 1;
            for (Object param : params) {
                query.setParameter(i ++, param);
            }
        }
    }

    /**
     * 构建PageRequest对象
     * @param searchCoditionModel 分页查询条件
     * @return PageRequest对象
     */
    protected PageRequest buildPageRequest(SearchCoditionModel searchCoditionModel) {
        return new PageRequest(searchCoditionModel.getPage(), searchCoditionModel.getSize());
    }

    /**
     * 构建分页Model对象
     * @param searchCoditionModel 分页查询条件
     * @param page 分页对象
     * @return 分页Model对象
     */
    protected PageModel<T> buildPageModel(SearchCoditionModel searchCoditionModel, Page<T> page){
        Pageable pageable = new Pageable(searchCoditionModel.getPage(), searchCoditionModel.getSize());
        if (null != page) {
            return new PageModel(page.getContent(), page.getTotalElements(), pageable);
        }
        return new PageModel(new ArrayList(), 0, pageable);
    }
}
