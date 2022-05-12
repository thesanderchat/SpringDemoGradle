package com.example.demo.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDto> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    public StudentDto getStudents(@PathVariable("studentId") Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewStudent(@RequestBody @Valid StudentDto studentDto) {
        studentService.addNewStudent(studentDto);
    }

    @DeleteMapping(path = "{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestBody @Valid StudentDto studentDto) {
        studentService.updateStudent(studentId, studentDto);
    }
}
