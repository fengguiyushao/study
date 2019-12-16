package com.robinzhou.simpleframework.controller;

import com.alibaba.fastjson.JSON;
import com.robinzhou.simpleframework.bean.Data;
import com.robinzhou.simpleframework.util.CodecUtil;
import com.robinzhou.simpleframework.util.ReflectionUtil;
import com.robinzhou.simpleframework.util.StreamUtil;
import com.robinzhou.simpleframework.bean.Handler;
import com.robinzhou.simpleframework.bean.Param;
import com.robinzhou.simpleframework.bean.View;
import com.robinzhou.simpleframework.helper.BeanHelper;
import com.robinzhou.simpleframework.helper.ConfigHelper;
import com.robinzhou.simpleframework.helper.ControllerHelper;
import com.robinzhou.simpleframework.helper.HelperLoader;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();

        ServletRegistration jspRegistration = servletContext.getServletRegistration("jsp");
        jspRegistration.addMapping(ConfigHelper.getAppJspPath() + "*");

        ServletRegistration defaultRegistration = servletContext.getServletRegistration("default");
        defaultRegistration.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getRequestURI();

        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();

            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if (StringUtils.isNoneEmpty(body)) {
                String[] params = StringUtils.split(body, "&");
                if (ArrayUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtils.split(param, "=");
                        if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }

            Param param = new Param(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);

            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNoneEmpty(path)) {
                    if (path.startsWith("/")) {
                        response.sendRedirect(request.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }

                        request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
                    }
                } else if (result instanceof Data) {
                    Data data = (Data) result;
                    Object model = data.getModel();
                    if (model != null) {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        PrintWriter writer = response.getWriter();
                        String json = JSON.toJSONString(model);
                        writer.write(json);
                        writer.flush();
                        writer.close();
                    }
                }

            }


        }
    }
}
