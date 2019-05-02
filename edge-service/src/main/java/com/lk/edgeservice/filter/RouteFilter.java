package com.lk.edgeservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

/*  Refer below link for more details
    https://github.com/Netflix/zuul/wiki/Writing-Filters
 */

public class RouteFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        return null;
    }
}
