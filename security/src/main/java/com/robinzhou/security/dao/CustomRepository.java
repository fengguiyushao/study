package com.robinzhou.security.dao;

import com.robinzhou.security.bean.DemoInfo;

import java.util.List;

/**
 * Created by robinzhou on 2017/3/23.
 */
public interface CustomRepository {

    List<DemoInfo> findByLikeTitle(String title);
}
