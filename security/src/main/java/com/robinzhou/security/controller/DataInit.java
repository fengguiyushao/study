package com.robinzhou.security.controller;

import com.robinzhou.security.bean.DemoInfo;
import com.robinzhou.security.dao.DemoInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by robinzhou on 2017/3/23.
 */
@Component
public class DataInit {

    @Autowired
    DemoInfoRepository demoInfoRepository;

    @PostConstruct
    public void dataInit(){
        demoInfoRepository.save(new DemoInfo("title1", "content1"));
        demoInfoRepository.save(new DemoInfo("title2", "content2"));
        demoInfoRepository.save(new DemoInfo("title3", "content3"));
        demoInfoRepository.save(new DemoInfo("title4", "content4"));
        demoInfoRepository.save(new DemoInfo("title5", "content5"));
    }

}