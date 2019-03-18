package ru.pf.controller;

import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author a.kakushin
 */
public class ControllerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        Object controller = ((HandlerMethod) handler).getBean();
        if (controller instanceof PfController) {
            modelAndView.addObject("url", "/" + ((PfController) controller).getUrl());
            modelAndView.addObject("name", ((PfController) controller).getName());
        }
    }
}
