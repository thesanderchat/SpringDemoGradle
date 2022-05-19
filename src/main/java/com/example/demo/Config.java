package com.example.demo;

import com.example.demo.group.Group;
import com.example.demo.group.GroupRepository;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class Config {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, GroupRepository groupRepository) {
        return args -> {
            Group group1 = new Group("math", LocalDate.of(2000, Month.JULY, 18));
            Group group2 = new Group("it", LocalDate.of(2000, Month.JULY, 18));
            Student vladislav = new Student("Vladislav", "pishenkovladyslav@gmail.com",
                    LocalDate.of(2001, Month.JUNE, 16));
            Student oleksandr = new Student("Oleksandr", "sasha.dzuyniak@gmail.com",
                    LocalDate.of(2002, Month.OCTOBER, 18));
            group1.addNewStudentToStudentList(oleksandr);
            group2.addNewStudentToStudentList(vladislav);
            groupRepository.saveAll(List.of(group1,group2));
            studentRepository.saveAll(List.of(vladislav, oleksandr));
        };
    }
}
