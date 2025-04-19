package org.kangwooju.spring_toby_practice.Global.Config;

import org.kangwooju.spring_toby_practice.domain.user.DAO.AccountDAO;
import org.kangwooju.spring_toby_practice.domain.user.DAO.MessageDAO;
import org.kangwooju.spring_toby_practice.domain.user.DAO.UserDAO;
import org.kangwooju.spring_toby_practice.domain.user.Service.DConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DAOFactory {

    @Bean
    public UserDAO userDAO(){
        /*
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDAO userDAO = new UserDAO(connectionMaker);
        // 의존성을 주입해주는 로직
        return userDAO;
         */

        /*
        return new UserDAO(new DConnectionMaker()); // UserDAO 생성시에 DConnectionMaker를 받도록 설정
         */
        return new UserDAO(connectionMaker());
    }

    @Bean
    public AccountDAO accountDAO(){
        /*
        return new AccountDAO(new DConnectionMaker()); // AccountDAO 생성시에 DConnectionMaker를 받도록 설정
         */
        return new AccountDAO(connectionMaker());
    }

    @Bean
    public MessageDAO messageDAO(){
        /*
        return new MessageDAO(new DConnectionMaker()); // MessageDAO 생성시에 DConnectionMaker를 받도록 설정
         */
        return new MessageDAO(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker(){
        return new DConnectionMaker();
    }
}
