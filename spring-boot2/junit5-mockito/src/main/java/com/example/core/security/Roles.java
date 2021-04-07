package com.example.core.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public enum Roles {
  STUDENT(Collections.emptySet()),
  ADMIN_TRAINEE(Sets.newHashSet(Permissions.COURSE_READ)),
  ADMIN(
          Sets.newHashSet(
                  Permissions.COURSE_READ,
                  Permissions.COURSE_WRITE,
                  Permissions.STUDENT_READ,
                  Permissions.STUDENT_WRITE));

  private final Set<Permissions> permissions;

  Roles(Set<Permissions> permissions) {
    this.permissions = permissions;
  }

  public Set<Permissions> getPermissions() {
    return permissions;
  }

  public Set<GrantedAuthority> getGrantedAuthorities() {
    Set<GrantedAuthority> authorities =
            getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                    .collect(Collectors.toSet());

    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
