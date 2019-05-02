package com.lk.edgeservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import java.util.List;

public class PostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {

        // Stdout for debug purpose
        final List<String> routingDebug = (List<String>) RequestContext.getCurrentContext().get("routingDebug");
        routingDebug.forEach(System.out::println);
        return null;
    }
}
