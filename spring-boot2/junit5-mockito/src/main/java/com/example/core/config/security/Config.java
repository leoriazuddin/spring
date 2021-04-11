package com.example.core.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Set;

import static com.example.core.security.Roles.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Config extends WebSecurityConfigurerAdapter {

  private static final String ROLE_STUDENT = "STUDENT";
  private static final String ROLE_ADMIN = "ADMIN";
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public Config(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Bean
  protected UserDetailsService userDetailsService() {
    String pwdRiaz = passwordEncoder.encode("pass");
    String pwdMd = passwordEncoder.encode("pass123");

    UserDetails student = createUser(pwdRiaz, "riaz", STUDENT.getGrantedAuthorities());
    UserDetails admin = createUser(pwdMd, "md", ADMIN.getGrantedAuthorities());
    UserDetails adminTrainee =
            createUser(pwdMd, "adminTrainee", ADMIN_TRAINEE.getGrantedAuthorities());
    return new InMemoryUserDetailsManager(student, admin, adminTrainee);
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
            .loginPage("/login")
            .defaultSuccessUrl("/courses", true)
            .permitAll();
  }
}
