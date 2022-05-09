package com.example.demo.group;

import java.util.List;
import java.util.Optional;

import com.example.demo.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByOrderByIdAsc();
}