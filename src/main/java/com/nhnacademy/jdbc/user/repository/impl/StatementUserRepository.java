package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class StatementUserRepository implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        //todo#1 아이디, 비밀번호가 일치하는 User 조회
        String q = String.format("select * from jdbc_users where user_id = '%s' and user_password = '%s'",
                userId,
                userPassword
        );
        log.debug("find user, Id and password: {}", q);

        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(q)){

            if(resultSet.next()) {
                User user = new User(
                        resultSet.getString("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_password")
                        );
                return Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //#todo#2-아이디로 User 조회
        String q = String.format("select * from jdbc_users where user_id = '%s'", userId);
        log.debug("find by id, user: {}", q);

        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery(q);
            if(resultSet.next()) {
                User user = new User(resultSet.getString("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_password")
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3- User 저장
        String q = String.format("insert into jdbc_users (user_id, user_name, user_password) values ('%s', '%s', '%s')",
                user.getUserId(),
                user.getUserName(),
                user.getUserPassword()
        );
        log.debug("save user, Id: {}", q);


        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement()){
            int result = statement.executeUpdate(q);
            log.debug("save user, Id: {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int updateUserPasswordByUserId(String userId, String userPassword) {
        //todo#4-User 비밀번호 변경
        return 0;
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#5 - User 삭제
        String q = String.format("delete from jdbc_users where user_id = '%s'", userId);
        log.debug("delete by id, user: {}", q);

        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement()){
            int result = statement.executeUpdate(q);
            log.debug("delete user, result: {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
