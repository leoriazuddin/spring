package com.example.core.repository;

import com.example.core.model.security.ApplicationUser;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.core.security.Roles.*;

@Repository
public class ApplicationUserDao {

  @Autowired
  private PasswordEncoder passwordEncoder;

  public ApplicationUserDao(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public ApplicationUser getUser(String userName) {
    return getApplicationUsers().stream()
            .filter(user -> user.getUsername().equals(userName))
            .findFirst()
            .get();
  }

  private List<ApplicationUser> getApplicationUsers() {
    return Lists.newArrayList(
            new ApplicationUser("riaz_student", passwordEncoder.encode("password"), STUDENT.getGrantedAuthorities()),
            new ApplicationUser("md_admin", passwordEncoder.encode("pass123"), ADMIN.getGrantedAuthorities()),
            new ApplicationUser("admin_trainee", passwordEncoder.encode("pass"), ADMIN_TRAINEE.getGrantedAuthorities()));
  }
}
