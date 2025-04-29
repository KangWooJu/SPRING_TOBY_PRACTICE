package org.kangwooju.spring_toby_practice.domain.user.DAO;

import org.kangwooju.spring_toby_practice.domain.user.Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy{

    User user;

    public AddStatement(User user){
        this.user = user;
    }


    @Override
    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO USERS(id,name,password) VALUES (?,?,?)");

        preparedStatement.setString(1,user.getId());
        preparedStatement.setString(2,user.getName());
        preparedStatement.setString(3,user.getPassword());

        return preparedStatement;
    }
}
