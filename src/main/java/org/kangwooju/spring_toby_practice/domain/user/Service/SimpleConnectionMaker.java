package org.kangwooju.spring_toby_practice.domain.user.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {

    public Connection makeNewConnection() throws ClassNotFoundException
            , SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Kangwooju", "root", "kanguju1102!"
        );

        return connection;
    }

}
