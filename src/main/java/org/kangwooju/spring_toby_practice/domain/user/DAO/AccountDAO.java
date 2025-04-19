package org.kangwooju.spring_toby_practice.domain.user.DAO;

import org.kangwooju.spring_toby_practice.Global.Config.ConnectionMaker;

public class AccountDAO {

    private ConnectionMaker connectionMaker;
    public AccountDAO(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }
}
