package org.kangwooju.spring_toby_practice.domain.user.DAO;

import org.kangwooju.spring_toby_practice.Global.Config.ConnectionMaker;
import org.kangwooju.spring_toby_practice.domain.user.Entity.User;
import org.kangwooju.spring_toby_practice.domain.user.Service.DConnectionMaker;
import org.kangwooju.spring_toby_practice.domain.user.Service.SimpleConnectionMaker;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;

public class UserDAO {

    private DataSource dataSource;

    @Autowired
    public UserDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }


    public void add(User user) throws ClassNotFoundException, SQLException {

        Connection connection = dataSource.getConnection(); // ConnectionMaker 대신 dataSource 인터페이스 사용
        /*
        Class.forName("com.mysql.cj.jdbc.Driver");


        // DB를 연결하는 로직을 분리
        Connection connection = simpleConnectionMaker.makeNewConnection();

        Connection connection = ConnectionMaker.makeConnection();


         */
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO users(id, name, password) VALUES (?, ?, ?)"
        );

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        connection.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        /*
        // DB를 연결하는 로직을 분리
        Connection connection = simpleConnectionMaker.makeNewConnection();
         */
        Connection connection = dataSource.getConnection();


        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM users WHERE id = ?"
        );
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        connection.close();

        return user;
    }
}
