package com.example.core.controller;

import com.example.core.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/management/api/v1/students/")
public class StudentManagementController {

  private static final List<Student> students =
          Arrays.asList(new Student(1L, "Riaz"), new Student(2L, "Student2"));

  @ResponseBody
  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
  public List<Student> students() {
    return students;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('student:write')")
  public void register(@RequestBody Student student) {
    System.out.println(student);
  }

  @DeleteMapping("{studentId}")
  @PreAuthorize("hasAuthority('student:write')")
  public void delete(@PathVariable("studentId") Long id) {
    System.out.println("Deleted:" + id);
  }

  @PutMapping("{studentId}")
  @PreAuthorize("hasAuthority('student:write')")
  public void update(@PathVariable("studentId") Long id) {
    System.out.println("Update: " + id);
  }
}
