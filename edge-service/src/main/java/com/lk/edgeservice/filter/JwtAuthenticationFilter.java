package com.lk.edgeservice.filter;

import com.lk.edgeservice.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {



    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenProvider jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse
            res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");
        String username = null;
        String authToken = null;
        Claims  claims  =   null;

        if (header != null && header.startsWith("Bearer ")) {
            authToken = header.replace("Bearer ","");
            System.out.format("Token %s from client ",authToken);
            try {
                claims = jwtUtil.getUsernameFromToken(authToken);
                username    =   claims.getSubject();
            } catch (IllegalArgumentException e) {
                System.out.format("IllegalArgumentException %s",e.getMessage());
            } catch (ExpiredJwtException e) {
                System.out.format("ExpiredJwtException %s",e.getMessage());
            } catch(SignatureException e){
                System.out.format("SignatureException %s",e.getMessage());
            }
        } else {
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            List<GrantedAuthority> authorities =   new ArrayList<>();
            GrantedAuthority authority  =   new SimpleGrantedAuthority(claims.get("role",String.class));
            authorities.add(authority);

            UserDetails userDetails =   new User(claims.get("email",String.class), claims.get("password",String.class), authorities);

            if (jwtUtil.validateToken(authToken)) {

                userDetails.getAuthorities().stream().forEach(a->System.out.println(((GrantedAuthority) a).getAuthority()));

                UsernamePasswordAuthenticationToken authentication = new
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(req));
                        SecurityContextHolder.getContext().setAuthentication(
                                authentication);
            }
        }
        chain.doFilter(req, res);
    }
}
