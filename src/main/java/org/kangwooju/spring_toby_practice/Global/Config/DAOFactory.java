package org.kangwooju.spring_toby_practice.Global.Config;

import org.kangwooju.spring_toby_practice.domain.user.DAO.AccountDAO;
import org.kangwooju.spring_toby_practice.domain.user.DAO.MessageDAO;
import org.kangwooju.spring_toby_practice.domain.user.DAO.UserDAO;
import org.kangwooju.spring_toby_practice.domain.user.Service.DConnectionMaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DAOFactory {

    // 환경변수 설정
    @Value("${MYSQL_URL}")
    private String mysql_Url;

    @Value("${MYSQL_USERNAME}")
    private String mysql_Username;

    @Value("${MYSQL_PASSWORD}")
    private String mysql_Password;

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();


        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl(mysql_Url);// 환경변수 설정
        dataSource.setUsername(mysql_Username);// 환경변수 설정
        dataSource.setPassword(mysql_Password);// 환경변수 설정

        return dataSource;

    }

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

        return new UserDAO(connectionMaker());

         */
        UserDAO userDAO = new UserDAO(dataSource()); // DataSource 의존성 주입
        return userDAO;
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
