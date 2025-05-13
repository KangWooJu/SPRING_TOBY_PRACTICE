package org.kangwooju.spring_toby_practice.domain.user.Service;

import lombok.Data;
import org.kangwooju.spring_toby_practice.domain.user.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class UserService {


    private User user;

    @Autowired
    private DataSource dataSource;






    public void upgradeLevels() throws Exception{

        Connection connection = dataSource.getConnection();

        // 기타 로직
        try{
            connection.setAutoCommit(false);
            // 기타로직
            connection.commit();

        } catch ( Exception e ){
            // 에러 처리 로직
            connection.rollback();

        } finally {
            if (connection != null) {
                try {
                    connection.close(); // 자원 정리
                    // ResultSet , PreparedStatement 도 닫아주기
                } catch (SQLException e) {
                    // connection 닫을 때 예외가 나면 로깅만
                    e.printStackTrace();
                }
            }
        }

    }

}
