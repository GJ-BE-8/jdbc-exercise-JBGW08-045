package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class StatementStudentRepository implements StudentRepository {
    Map<String, Optional<Student>> studentsMap = new ConcurrentHashMap<>();
    @Override
    public int save(Student student){
        //todo#1 insert student
        try{
            studentsMap.put(student.getId(), Optional.of(student));
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 student 조회
        if(studentsMap.containsKey(id)){
            return studentsMap.get(id);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public int update(Student student){
        //todo#3 student 수정, name <- 수정합니다.
        if (studentsMap.containsKey(student.getId())) {
            studentsMap.put(student.getId(), Optional.of(student));
        }
        return 0;
    }

    @Override
    public int deleteById(String id){
       //todo#4 student 삭제
        studentsMap.remove(id);
        return 0;
    }

}
