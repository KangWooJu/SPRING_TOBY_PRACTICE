package org.kangwooju.spring_toby_practice.domain.user.Service;

import org.kangwooju.spring_toby_practice.Global.Config.ConnectionMaker;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // D사의 커넥션 로직 구성
        return null;
    }
}
