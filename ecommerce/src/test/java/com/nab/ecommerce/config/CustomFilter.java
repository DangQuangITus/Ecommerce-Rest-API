package com.nab.ecommerce.config;

import com.nab.ecommerce.enums.RoleName;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

public class CustomFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("admin","admin",
        Collections.singletonList(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.name())));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;

    HttpSession session = httpServletRequest.getSession();
    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
        SecurityContextHolder.getContext());

    chain.doFilter(request,response);
  }

  @Override
  public void destroy() {

  }
}