package com.robinzhou.security.dao;

import com.robinzhou.security.bean.DemoInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by robinzhou on 2017/3/23.
 */
public interface DemoInfoRepository extends CrudRepository<DemoInfo, Long>, CustomRepository {
}
