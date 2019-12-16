package com.robinzhou.simpleframework.controller;

import com.robinzhou.simpleframework.annotation.Action;
import com.robinzhou.simpleframework.annotation.Inject;
import com.robinzhou.simpleframework.bean.Data;
import com.robinzhou.simpleframework.annotation.Controller;
import com.robinzhou.simpleframework.bean.Param;
import com.robinzhou.simpleframework.bean.View;
import com.robinzhou.simpleframework.model.Customer;
import com.robinzhou.simpleframework.service.CustomerService;

import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    @Action("get:/customer")
    public View index(Param param) {
        List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", customerList);
    }

    @Action("get:/customer_show")
    public View show(Param param) {
        long id = param.getLong("id");
        Customer customer = customerService.getCustomer(id);
        return new View("customer_show.jsp").addModel("customer", customer);
    }

    @Action("get:/customer_create")
    public View create(Param param) {
        return new View("customer_create.jsp");
    }

    @Action("post:/customer_create")
    public Data createSubmit(Param param) {
        Map<String, Object> field = param.getMap();
        boolean result = customerService.createCustomer(field);
        return new Data(result);
    }

    @Action("get:/customer_edit")
    public Data editSubmit(Param param) {
        long id = param.getLong("id");
        Map<String, Object> fieldMap = param.getMap();
        boolean result = customerService.updateCustomer(id, fieldMap);
        return new Data(result);
    }

    @Action("delete:/customer_edit")
    public Data delete(Param param) {
        long id = param.getLong("id");
        boolean result = customerService.deleteCustomer(id);
        return new Data(result);
    }

}