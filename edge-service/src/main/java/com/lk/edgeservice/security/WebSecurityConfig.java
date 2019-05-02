package com.lk.edgeservice.security;

import com.lk.edgeservice.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws
            Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws
            Exception {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

         http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","/authservice/login","/authservice/register","/register","/accountservice/register","/accountservice/v2/api-docs","/edgeservice/v2/api-docs","/authservice/v2/api-docs","/eventservice/v2/api-docs","/v2/api-docs").permitAll()
                .antMatchers(HttpMethod.GET,"/eventservice/api/v1/events","/api/v1/events").hasAnyRole("USER","ORGANIZER")
                .antMatchers(HttpMethod.POST,"/eventservice/api/v1/events","/api/v1/events").hasRole("ORGANIZER")
                .antMatchers(HttpMethod.PUT,"/eventservice/api/v1/events","/api/v1/events").hasRole("ORGANIZER")
                .antMatchers(HttpMethod.DELETE,"/eventservice/api/v1/events/**","/api/v1/events/**").hasRole("ORGANIZER")
                .antMatchers(HttpMethod.GET,"/eventservice/api/v1/events/**","/api/v1/events/**").hasAnyRole("USER","ORGANIZER")
                .antMatchers(HttpMethod.GET,"/eventservice/api/v1/events/organizer/**","/api/v1/events/organizer/**").hasRole("ORGANIZER")
                 .antMatchers(HttpMethod.GET,"/accountservice/api/v1/users/**","/api/v1/users/**").hasAnyRole("USER","ORGANIZER")
                 .anyRequest().authenticated()
                 .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationTokenFilterBean(),UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
