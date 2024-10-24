package com.nhnacademy.jdbc.student.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

public class Student {

    public enum GENDER{
        M,F
    }
    @Getter @Setter
    private final String id;
    @Getter @Setter
    private final String name;
    @Getter @Setter
    private final GENDER gender;
    @Getter @Setter
    private final Integer age;
    @Getter @Setter
    private final LocalDateTime createdAt;

    //todo#0 필요한 method가 있다면 추가합니다.
    public Student(String id, String name, GENDER gender, Integer age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.createdAt = LocalDateTime.now();
    }

}
