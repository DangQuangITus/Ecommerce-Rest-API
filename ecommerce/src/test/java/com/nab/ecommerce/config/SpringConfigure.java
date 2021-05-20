package com.nab.ecommerce.config;

import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class SpringConfigure extends WebSecurityConfigurerAdapter {

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    configureAuthorization(http);
    configureAuthentication(http);
  }


  private void configureAuthorization(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/api/**").authenticated().and()
        .addFilterBefore(new CustomFilter(), SecurityContextPersistenceFilter.class);
  }


  private void configureAuthentication(HttpSecurity http) throws Exception {
    AuthenticationEntryPoint authenticationEntryPoint = (request, response, e) -> response.sendError(
        HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
  }
}