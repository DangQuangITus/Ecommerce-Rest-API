package com.nab.ecommerce.config;


import com.nab.ecommerce.security.CustomUserDetailsService;
import com.nab.ecommerce.security.JWTAuthenticationFilter;
import com.nab.ecommerce.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  CustomUserDetailsService customUserDetailsService;

  @Autowired
  JwtAuthenticationEntryPoint authenticationEntryPoint;

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  JWTAuthenticationFilter jwtAuthenticationFilter() {
    return new JWTAuthenticationFilter();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/api/admin/init", "/api/product/**", "/api/order/create");
    web.ignoring().antMatchers(HttpMethod.GET, "/h2");
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
        .userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder());

    authenticationManagerBuilder.inMemoryAuthentication()
        .passwordEncoder(passwordEncoder())
        .withUser("admin")
        .password(passwordEncoder().encode("admin"))
        .roles("ADMIN");
  }

  @Autowired
  private H2ConsoleProperties console;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    String path = this.console.getPath();
    String antPattern = (path.endsWith("/") ? path + "**" : path + "/**");

    HttpSecurity h2Console = http.antMatcher(antPattern);
    h2Console.csrf().disable();
    h2Console.httpBasic();
    h2Console.headers().frameOptions().sameOrigin();
    // config as you like

    http
        .cors()
        .and()
        .antMatcher(antPattern)
        .csrf().disable()
        .httpBasic()
        .and()
        .headers().frameOptions().sameOrigin()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js")
        .permitAll()
        .antMatchers("/api/auth/**", "/api/admin/**")
        .permitAll()
        .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/api/cart/**", "/api/users/**")
        .permitAll();
//        .anyRequest()
//        .authenticated();

    // config as you like
//    http.authorizeRequests().anyRequest().permitAll();

    // Add our custom JWT security filter
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

  }

}
