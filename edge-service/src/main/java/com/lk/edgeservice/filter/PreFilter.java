package com.lk.edgeservice.filter;

import com.lk.edgeservice.security.JwtTokenProvider;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class PreFilter extends ZuulFilter {

    @Autowired
    private JwtTokenProvider jwtUtil;


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        // Return to true to enable filter
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx =   RequestContext.getCurrentContext();
        HttpServletRequest req =   ctx.getRequest();
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String authToken = header.replace("Bearer ","");
            try {
                Claims claims = jwtUtil.getUsernameFromToken(authToken);
                String userId    =   claims.get("userId").toString();
                ctx.addZuulRequestHeader("userId",userId);
            } catch (IllegalArgumentException e) {
                System.out.format("IllegalArgumentException %s",e.getMessage());
            } catch (ExpiredJwtException e) {
                System.out.format("ExpiredJwtException %s",e.getMessage());
            } catch(SignatureException e){
                System.out.format("SignatureException %s",e.getMessage());
            }
        }

        return null;
    }
}
