package org.kangwooju.spring_toby_practice.domain.user.DAO;

import org.kangwooju.spring_toby_practice.Global.Config.ConnectionMaker;
import org.kangwooju.spring_toby_practice.domain.user.Entity.User;
import org.kangwooju.spring_toby_practice.domain.user.Service.DConnectionMaker;
import org.kangwooju.spring_toby_practice.domain.user.Service.SimpleConnectionMaker;

import java.sql.*;

public class UserDAO {

    private ConnectionMaker ConnectionMaker;

    public UserDAO(ConnectionMaker connectionMaker){
        /*
        this.simpleConnectionMaker = new SimpleConnectionMaker(); // 객체 생성하기
         */
        /*
        this.ConnectionMaker = new DConnectionMaker(); // D사의 커넥션 로직으로 변경 -> DIP 위배
         */
        this.ConnectionMaker = connectionMaker; // 인터페이스를 받아오도록 로직 변경
    }




    public void add(User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        /*
        // DB를 연결하는 로직을 분리
        Connection connection = simpleConnectionMaker.makeNewConnection();
         */
        Connection connection = ConnectionMaker.makeConnection();

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
        Connection connection = ConnectionMaker.makeConnection();


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
