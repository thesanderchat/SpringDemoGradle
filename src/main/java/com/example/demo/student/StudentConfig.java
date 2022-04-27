package com.example.demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

  @Bean
  CommandLineRunner commandLineRunner(StudentRepository repository){
    return args -> {
      Student vladislav = new Student("Vladislav", "pishenkovladyslav@gmail.com",
          LocalDate.of(2001, Month.JUNE, 16));
      Student oleksandr = new Student ("Oleksandr", "sasha.dzuyniak@gmail.com",
          LocalDate.of(2002, Month.OCTOBER, 18));
      repository.saveAll(List.of(oleksandr,vladislav));
    };
  }
}
