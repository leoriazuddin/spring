package com.example.core.config.security;

import com.example.core.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static com.example.core.security.Roles.STUDENT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Config extends WebSecurityConfigurerAdapter {
  private final PasswordEncoder passwordEncoder;
  private final ApplicationUserService applicationUserService;

  @Autowired
  public Config(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
    this.passwordEncoder = passwordEncoder;
    this.applicationUserService = applicationUserService;
  }

//  commented due to switch to daoAuthenticationProvider
//  @Override
//  @Bean
//  protected UserDetailsService userDetailsService() {
//    String pwdRiaz = passwordEncoder.encode("pass");
//    String pwdMd = passwordEncoder.encode("pass123");
//
//    UserDetails student = createUser(pwdRiaz, "riaz", STUDENT.getGrantedAuthorities());
//    UserDetails admin = createUser(pwdMd, "md", ADMIN.getGrantedAuthorities());
//    UserDetails adminTrainee =
//            createUser(pwdMd, "adminTrainee", ADMIN_TRAINEE.getGrantedAuthorities());
//    return new InMemoryUserDetailsManager(student, admin, adminTrainee);
//  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(applicationUserService);
    return provider;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  private UserDetails createUser(
          String password, String userName, Set<GrantedAuthority> authorities) {
    return User.builder()
            .username(userName)
            .password(password)
            //            .roles(role)
            .authorities(authorities)
            .build();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .csrf()
            .disable()
//            .csrf()
//              .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
            .authorizeRequests()
            .antMatchers("/")
            .permitAll()
            .antMatchers("/api/**").hasRole(STUDENT.name())
//            .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(Permissions.COURSE_WRITE.getPermission())
//            .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(Permissions.COURSE_WRITE.getPermission())
//            .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(Permissions.COURSE_WRITE.getPermission())
//            .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
            .anyRequest()
            .authenticated()
            .and()
//            .httpBasic();
            .formLogin()
            .permitAll()
            .loginPage("/login")
            .defaultSuccessUrl("/courses", true)
            .and()
            .logout()
            .logoutUrl("/logout")
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID", "remember-me")
            .logoutSuccessUrl("/login");
  }
}
