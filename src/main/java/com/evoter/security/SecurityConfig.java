//package com.evoter.security;
//
//import com.evoter.general.enums.ResponseCodeAndMessage;
//import com.evoter.security.jwt.JwtFilter;
//import com.evoter.user.service.implementation.CustomUserDetailsService;
//import jakarta.servlet.http.HttpServletResponse;
//import net.minidev.json.JSONObject;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.BeanIds;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final JwtFilter jwtFilter;
//    private final CustomUserDetailsService userDetailsService;
//
//    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtFilter jwtFilter) {
//        this.userDetailsService = userDetailsService;
//        this.jwtFilter = jwtFilter;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().configurationSource(corsConfigurationSource())
//                .and().csrf().disable().authorizeRequests()
//                .antMatchers("/api/v1/auth/login", "/api/v1/users/registration", "/api/v1/users//getLoggedInUserInfo")
//                .permitAll().anyRequest().authenticated()
//                .and().exceptionHandling().and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        //Exception handling configuration for failed login
//        http.exceptionHandling().authenticationEntryPoint((request, response, e) ->
//        {
//            response.setContentType("application/json;charset=UTF-8");
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.getWriter().write(new JSONObject()
//                    .put("responseCode", ResponseCodeAndMessage.AUTHENTICATION_FAILED_95.responseCode)
//                    .put("responseMessage", ResponseCodeAndMessage.AUTHENTICATION_FAILED_95.responseMessage)
//                    .toString());
//        });
//
//        //exception handling for access denied
//        http.exceptionHandling().accessDeniedHandler((request, response, e) ->
//        {
//            response.setContentType("application/json;charset=UTF-8");
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.getWriter().write(new JSONObject()
//                    .put("responseCode", ResponseCodeAndMessage.CLIENT_NOT_ALLOWED_97.responseCode)
//                    .put("responseMessage", "Access denied")
//                    .toString());
//        });
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(
//                Arrays.asList("http://localhost:9701/"));
//        configuration.setAllowedMethods(Collections.singletonList("*"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(Collections.singletonList("*"));
//        configuration.setExposedHeaders(Collections.singletonList("*"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//
//    }
//}