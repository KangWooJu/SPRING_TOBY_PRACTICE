package org.kangwooju.spring_toby_practice.domain.user.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class JdbcContext {


    // UserDAO에서 분리
    private DataSource dataSource;

    @Autowired
    public JdbcContext(DataSource dataSource){
        this.dataSource = dataSource;
    }


    // Template
    public void workWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.dataSource.getConnection();
            preparedStatement = statementStrategy.makePreparedStatement(connection);

            preparedStatement.executeUpdate();
        } catch ( SQLException e ){
            throw e;
        } finally {
            if(preparedStatement != null){try {preparedStatement.close();} catch ( SQLException e ){}}
            if(connection != null){try {connection.close();} catch ( SQLException e ){}}
        }
    }


    // UserDAO의 excuteSql 메소드를 DAO 전체가 공유할 수 있도록 이관
    // Client : Template을 사용
    public void excuteSql(final String query) throws SQLException{
        workWithStatementStrategy(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                        return connection.prepareStatement(query);
                    }
                }
        );
    }
}
