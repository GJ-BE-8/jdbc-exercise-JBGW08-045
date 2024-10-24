package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class PreparedStatementUserRepository implements UserRepository {
    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        //todo#11 -PreparedStatement- 아이디 , 비밀번호가 일치하는 회원조회
        String q = "select * from jdbc_users where user_id = ? and user_password = ?";
        try(Connection connection = DbUtils.getConnection();
            PreparedStatement statemnt = connection.prepareStatement(q)) {

            statemnt.setString(1, userId);
            statemnt.setString(2, userPassword);

            ResultSet result = statemnt.executeQuery();

            if(result.next()) {
                User user = new User(
                        result.getString("user_Id"),
                        result.getString("user_Name"),
                        result.getString("user_Password")
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
        //todo#12-PreparedStatement-회원조회
        String q = "select * from jdbc_users where user_id = ?";

        try(Connection connection = DbUtils.getConnection();
            PreparedStatement statemnt = connection.prepareStatement(q)){

            statemnt.setString(1, userId);

            ResultSet result = statemnt.executeQuery();
            if(result.next()) {
                User user = new User(
                        result.getString("user_Id"),
                        result.getString("user_Name"),
                        result.getString("user_Password")
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
        //todo#13-PreparedStatement-회원저장
        String q = "insert into jdbc_users (user_id, user_name, user_password) values (?, ?, ?)";

        log.debug("save user, Id: {}", q);

        try(Connection connection = DbUtils.getConnection();
            PreparedStatement statemnt = connection.prepareStatement(q)) {
            statemnt.setString(1, user.getUserId());
            statemnt.setString(2, user.getUserName());
            statemnt.setString(3, user.getUserPassword());

            int result = statemnt.executeUpdate();
            log.debug("save user, Id: {}", result);

            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateUserPasswordByUserId(String userId, String userPassword) {
        //todo#14-PreparedStatement-회원정보 수정
        String q = "update jdbc_users set user_password = ? where user_id = ?";
        log.debug("update user, {}", q);

        try(Connection connection = DbUtils.getConnection();
            PreparedStatement statemnt = connection.prepareStatement(q)){

            statemnt.setString(1, userPassword);
            statemnt.setString(2, userId);

            int result = statemnt.executeUpdate();
            log.debug("update user, {}", result);
            return result;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#15-PreparedStatement-회원삭제
        String q = "delete from jdbc_users where user_id = ?";
        log.debug("delete user, {}", q);

        try(Connection connection = DbUtils.getConnection();
            PreparedStatement statemnt = connection.prepareStatement(q)){

            statemnt.setString(1, userId);
            int result = statemnt.executeUpdate();

            log.debug("delete user, {}", result);
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
