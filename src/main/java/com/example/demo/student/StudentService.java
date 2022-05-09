package com.example.demo.student;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;
  public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
    this.studentRepository = studentRepository;
    this.studentMapper = studentMapper;
  }

  public List<StudentDto> getStudents() {
    return studentRepository.findAllByOrderByIdAsc().stream()
            .map(studentMapper::toStudentDto)
            .collect(Collectors.toList());
  }
  public StudentDto getStudentById(Long studentId){
    return studentRepository.findById(studentId)
            .map(studentMapper::toStudentDto).orElseThrow(() -> new IllegalStateException("No student"));
  }

  public void addNewStudent(StudentDto studentDto) {
    Optional<Student> studentOptional = studentRepository.findStudentByEmail(studentDto.getEmail());
    if (studentOptional.isPresent()) {
      throw new IllegalStateException("email taken");
    }
    studentRepository.save(studentMapper.toStudent(studentDto));
  }

  public void deleteStudent(Long studentId) {
    boolean exists = studentRepository.existsById(studentId);
    if (!exists) {
      throw new IllegalStateException("student with id " + studentId + " does not exists");
    }
    studentRepository.deleteById(studentId);
  }

  @Transactional
  public void updateStudent(Long studentId,StudentDto studentDto) {
    Student student = studentRepository.findById(studentId).orElseThrow(
        () -> new IllegalStateException("student with id " + studentId + " does not exists"));
    student.setName(studentDto.getName());
    student.setEmail(studentDto.getEmail());
    student.setDateOfBirth(studentDto.getDateOfBirth());
    student.setGroup(studentDto.getGroup());
    }
}
