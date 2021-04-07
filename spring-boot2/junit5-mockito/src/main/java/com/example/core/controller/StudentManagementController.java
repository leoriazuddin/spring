package com.example.core.controller;

import com.example.core.model.Student;
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
  public List<Student> students() {
    return students;
  }

  @PostMapping
  public void register(@RequestBody Student student) {
    System.out.println(student);
  }

  @DeleteMapping("{studentId}")
  public void delete(@PathVariable("studentId") Long id) {
    System.out.println("Deleted:" + id);
  }

  @PutMapping("{studentId}")
  public void update(@PathVariable("studentId") Long id) {
    System.out.println("Update: " + id);
  }
}
