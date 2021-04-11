package com.example.core.controller;

import com.example.core.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1/students/")
public class StudentController {

  private static final List<Student> students =
          Arrays.asList(new Student(1L, "Riaz"), new Student(2L, "Md"));

  @ResponseBody
  @GetMapping("/")
  public List<Student> students() {
    return students;
  }

  @ResponseBody
  @GetMapping("{id}")
  public Student student(@PathVariable Long id) {
    return students.stream()
            .filter(student -> student.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid userId: " + id));
  }
}
