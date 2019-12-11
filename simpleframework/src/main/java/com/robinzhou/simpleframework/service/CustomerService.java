package com.robinzhou.simpleframework.service;

import com.robinzhou.simpleframework.helper.PropsUtil;
import com.robinzhou.simpleframework.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CustomerService {

    public static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("can not load jdbc driver", e);
        }
    }


    public List<Customer> getCustomerList() {
        //todo
        return null;
    }

    public Customer getCustomer(long id) {
        //todo
        return null;
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        //todo
        return false;
    }

    public boolean updateCustomer(long id,Map<String, Object> fieldMap) {
        //todo
        return false;
    }

    public boolean deleteCustomer(long id) {
        //todo
        return false;
    }
}
