package org.kangwooju.spring_toby_practice.domain.user.DAO;

import org.kangwooju.spring_toby_practice.Global.Config.ConnectionMaker;

public class MessageDAO {

    private ConnectionMaker connectionMaker;
    public MessageDAO(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }
}
