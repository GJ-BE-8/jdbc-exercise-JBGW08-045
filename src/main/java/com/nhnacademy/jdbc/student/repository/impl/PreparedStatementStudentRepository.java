package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class PreparedStatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student){
        //todo#1 학생 등록
        String q = "insert into jdbc_students values(?,?,?,?)";

        try(Connection connection = DbUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(q)){
            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getGender().toString());
            statement.setInt(4,student.getAge());
            int result = statement.executeUpdate();
            log.debug("save:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 학생 조회
        String q = "select * from jdbc_students where id = ?";

        try(Connection connection = DbUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(q)){

            statement.setString(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                Student student = new Student(
                        result.getString("id"),
                        result.getString("name"),
                        Student.GENDER.valueOf(result.getString("gender")),
                        result.getInt("age"),
                        result.getTimestamp("created_at").toLocalDateTime()
                );

                return Optional.of(student);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int update(Student student){
        //todo#3 학생 수정 , name 수정
        String q = "update jdbc_students set name=?, gender=?, age=? where id=?";
        try(Connection connection = DbUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(q)) {

            statement.setString(1, student.getName());
            statement.setString(2, student.getGender().toString());
            statement.setInt(3,student.getAge());
            statement.setString(4, student.getId());

            int result = statement.executeUpdate();
            log.debug("update:{}",result);
            return result;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int deleteById(String id){
        //todo#4 학생 삭제
        String q = "delete from jdbc_students where id = ?";
        try(Connection connection = DbUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(q)){
            statement.setString(1, id);

            int result = statement.executeUpdate();
            log.debug("delete:{}",result);
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
