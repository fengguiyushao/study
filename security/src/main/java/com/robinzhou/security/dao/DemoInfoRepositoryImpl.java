package com.robinzhou.security.dao;

import com.robinzhou.security.bean.DemoInfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by robinzhou on 2017/3/23.
 */
public class DemoInfoRepositoryImpl implements CustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DemoInfo> findByLikeTitle(String title) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<DemoInfo> query = builder.createQuery(DemoInfo.class);
        Root<DemoInfo> root = query.from(DemoInfo.class);
        query.where(builder.like(root.get("title"), "%" + title + "%"));
        return em.createQuery(query.select(root)).getResultList();
//        return em.createNativeQuery("select * from DemoInfo where 1=1").getResultList();
    }
}
