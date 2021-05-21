package com.nab.ecommerce.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.ecommerce.contants.Constants;
import com.nab.ecommerce.exception.AuthoException;
import com.nab.ecommerce.payload.response.ApiResponse;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

  @Autowired
  CustomUserDetailsService customUserDetailsService;

  @Autowired
  JWTTokenProvider jwtTokenProvider;


  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      String token = getJwtFromRequest(httpServletRequest);
      if (StringUtils.isNotEmpty(token) && jwtTokenProvider.validateToken(token)
//          || httpServletRequest.getRequestURL().equals(Constants.INIT_DATA)
      ) {

        long userId = jwtTokenProvider.getUserIdFromToken(token);

        UserDetails user = customUserDetailsService.loadUserById(userId);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
      }
      filterChain.doFilter(httpServletRequest, httpServletResponse);

    } catch (Exception ex) {
      logger.error("token invalidate: " + ex.getMessage());
      ApiResponse response = new ApiResponse(false, ex.getMessage());
      httpServletResponse.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
      httpServletResponse.getWriter().write(convertObjectToJson(response));
    }

  }

  public String convertObjectToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    return "";
  }
}
