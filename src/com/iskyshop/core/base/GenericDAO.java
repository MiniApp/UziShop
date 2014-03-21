package com.iskyshop.core.base;

import com.iskyshop.core.dao.IGenericDAO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class GenericDAO<T> implements IGenericDAO<T> {
    protected Class<T> entityClass;

    @Autowired
    @Qualifier("genericEntityDao")
    private GenericEntityDao geDao;

    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public GenericEntityDao getGeDao() {
        return this.geDao;
    }

    public void setGeDao(GenericEntityDao geDao) {
        this.geDao = geDao;
    }

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        this.entityClass = ((Class<T>) ((java.lang.reflect.ParameterizedType) super.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0]);
    }

    public GenericDAO(Class<T> type) {
        this.entityClass = type;
    }

    public int batchUpdate(String jpql, Object[] params) {
        return this.geDao.batchUpdate(jpql, params);
    }

    @SuppressWarnings("rawtypes")
    public List executeNamedQuery(String queryName, Object[] params, int begin, int max) {
        return this.geDao.executeNamedQuery(queryName, params, begin, max);
    }

    @SuppressWarnings("rawtypes")
    public List executeNativeNamedQuery(String nnq) {
        return this.geDao.executeNativeNamedQuery(nnq);
    }

    @SuppressWarnings("rawtypes")
    public List executeNativeQuery(String nnq, Object[] params, int begin, int max) {
        return this.geDao.executeNativeQuery(nnq, params, begin, max);
    }

    public int executeNativeSQL(String nnq) {
        return this.geDao.executeNativeSQL(nnq);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List find(String query, Map params, int begin, int max) {
        return getGeDao().find(this.entityClass, query, params, begin, max);
    }

    public void flush() {
        this.geDao.flush();
    }

    @SuppressWarnings("unchecked")
    public T get(Serializable id) {
        return (T) getGeDao().get(this.entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public T getBy(String propertyName, Object value) {
        return (T) getGeDao().getBy(this.entityClass, propertyName, value);
    }

    @SuppressWarnings("rawtypes")
    public List query(String query, Map params, int begin, int max) {
        return getGeDao().query(query, params, begin, max);
    }

    public void remove(Serializable id) {
        getGeDao().remove(this.entityClass, id);
    }

    public void save(Object newInstance) {
        getGeDao().save(newInstance);
    }

    public void update(Object transientObject) {
        getGeDao().update(transientObject);
    }
}