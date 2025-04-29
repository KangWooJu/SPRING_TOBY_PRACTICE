package org.kangwooju.spring_toby_practice.domain.user.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy{

    @Override
    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USERS");
        return preparedStatement;
    }
}
