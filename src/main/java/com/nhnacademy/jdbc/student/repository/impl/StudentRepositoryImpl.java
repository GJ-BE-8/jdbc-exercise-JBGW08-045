package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

    /*

    CREATE TABLE `jdbc_students` (
  `id` varchar(50) NOT NULL COMMENT '학생-아이디',
  `name` varchar(50) NOT NULL COMMENT '학생-이름',
  `gender` varchar(1) NOT NULL COMMENT '성별 (M | F)',
  `age` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='학생';
     */

    @Override
    public int save(Connection connection, Student student){
        //todo#2 학생등록
        String q = "insert into jdbc_students(id,name,gender,age) values(?,?,?,?)";

        try(PreparedStatement statement = connection.prepareStatement(q)) {

            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getGender().toString());
            statement.setInt(4, student.getAge());

            int result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Student> findById(Connection connection,String id){
        //todo#3 학생조회
        String q = "select * from jdbc_students where id=?";
        ResultSet rs = null;

        try(PreparedStatement statement = connection.prepareStatement(q)){

            statement.setString(1, id);
            rs = statement.executeQuery();
            if(rs.next()){
                Student student = new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        Student.GENDER.valueOf(rs.getString("gender")),
                        rs.getInt("age")
                );
                return Optional.of(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int update(Connection connection,Student student){
        //todo#4 학생수정
        String q = "update jdbc_students set name=?,gender=?,age=? where id=?";

        try(PreparedStatement statement = connection.prepareStatement(q)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getGender().toString());
            statement.setInt(3, student.getAge());
            statement.setString(4, student.getId());

            int result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public int deleteById(Connection connection,String id){
        //todo#5 학생삭제
        String q = "delete from jdbc_students where id=?";

        try(PreparedStatement statement = connection.prepareStatement(q)) {

            statement.setString(1, id);
            int result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}