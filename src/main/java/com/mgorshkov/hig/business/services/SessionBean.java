package com.mgorshkov.hig.business.services;

/**
 * Created by mkgo on 23/02/15.
 */

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class SessionBean {

    @PersistenceContext(unitName = "hig20150218")
    EntityManager entityManager;

    public Object saveData(Object data) {
        Object dataPersist = entityManager.merge(data);
        return dataPersist;
    }

    public boolean removeData(Object data) {
        boolean removeOk = true;
        try {
            Object o = entityManager.merge(data);
            entityManager.remove(o);
        } catch (Exception enfe) {
            System.out.println("ERROR:" + enfe);
            removeOk = false;
        } finally{
            System.out.println("finally:"+removeOk);
            return removeOk;
        }
    }

    public boolean executeQuery(String queryString, Object... params) {
        try {
            Query query = entityManager.createQuery(queryString);
            for (int i = 1; i <= params.length; i++) {
                query.setParameter(i, params[i - 1]);
            }
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List executeQueryList(String queryString, Object... params) {
        try {
            Query query = entityManager.createQuery(queryString);
            for (int i = 1; i <= params.length; i++) {
                Object param = params[i - 1];
                query.setParameter(i, param);
            }
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("ERROR:" + e);
            e.printStackTrace();
            return null;
        }
    }

    public List executeNativeQueryList(String queryString, Object... params) {
        try {
            Query query = entityManager.createNativeQuery(queryString);
            for (int i = 1; i <= params.length; i++) {
                Object param = params[i - 1];
                query.setParameter(i, param);
            }
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("ERROR:" + e);
            e.printStackTrace();
            return null;
        }
    }

    public int executeQueryCount(String queryString, Object... params) {
        try {
            Query query = entityManager.createQuery(queryString);
            for (int i = 1; i <= params.length; i++) {
                query.setParameter(i, params[i - 1]);
            }
            Object count = query.getSingleResult();
            return ((Number) count).intValue();
        } catch (Exception e) {
            System.out.println("ERROR:" + e);
            e.printStackTrace();
            return -1;
        }
    }

    public List<Class<?>> executeNamedQuery(Class classe, String queryString){
        TypedQuery<Class<?>> query = entityManager.createNamedQuery(queryString, classe);
        List<Class<?>> list = query.getResultList();
        return list;
    }

    public List executeNamedQuerySpecific(Class classe, String queryString){
        TypedQuery query = entityManager.createNamedQuery(queryString, classe);
        List list = query.getResultList();
        return list;
    }
}
