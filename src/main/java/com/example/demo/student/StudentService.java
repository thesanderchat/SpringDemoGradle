package com.example.demo.student;

import com.example.demo.group.Group;
import com.example.demo.group.GroupRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.groupRepository = groupRepository;
    }

    public List<StudentDto> getStudents() {
        return studentRepository.findAllByOrderByIdAsc().stream()
                .map(studentMapper::toStudentDto)
                .collect(Collectors.toList());
    }

    public StudentDto getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .map(studentMapper::toStudentDto).orElseThrow(() -> new IllegalStateException("No student"));
    }

    public void addNewStudent(StudentDto studentDto) {
        Group group = groupRepository.findById(studentDto.getGroupId()).orElseThrow(
                () -> new IllegalStateException("group with id " + studentDto.getGroupId() + " does not exists"));
        Student student = studentMapper.toStudent(studentDto);
        group.addNewStudentToStudentList(student);
        studentRepository.save(student);

    }

    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("student with id " + studentId + " does not exists"));
        Group group = groupRepository.findById(student.getGroup().getId()).orElseThrow(
                () -> new IllegalStateException("group with id " + student.getGroup().getId() + " does not exists"));
        group.removeStudentFromStudentList(student);
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, StudentDto studentDto) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("student with id " + studentId + " does not exists"));
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());
        student.setDateOfBirth(studentDto.getDateOfBirth());
        Group group = groupRepository.findById(studentDto.getGroupId()).orElseThrow(
                () -> new IllegalStateException("group with id " + studentDto.getGroupId() + " does not exists"));
        group.addNewStudentToStudentList(student);
    }
}
