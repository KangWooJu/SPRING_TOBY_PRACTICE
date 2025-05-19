package org.kangwooju.spring_toby_practice.Global.Config;

import org.kangwooju.spring_toby_practice.domain.user.DAO.AccountDAO;
import org.kangwooju.spring_toby_practice.domain.user.DAO.JdbcContext;
import org.kangwooju.spring_toby_practice.domain.user.DAO.MessageDAO;
import org.kangwooju.spring_toby_practice.domain.user.DAO.UserDAO;
import org.kangwooju.spring_toby_practice.domain.user.Service.DConnectionMaker;
import org.kangwooju.spring_toby_practice.domain.user.Service.Interface.UserService;
import org.kangwooju.spring_toby_practice.domain.user.Service.UserDAOJdbc;
import org.kangwooju.spring_toby_practice.domain.user.Service.UserServiceImpl;
import org.kangwooju.spring_toby_practice.domain.user.Service.UserServiceTx;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

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
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("email.ex.com");
        return javaMailSender;
    }

    @Bean
    public UserDAOJdbc userDAOJdbc(){
        return new UserDAOJdbc(dataSource());
    }

    @Bean
    public JdbcContext jdbcContext(){
        return new JdbcContext(dataSource());
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource); // JDBC의 트랜잭션 추상화로 DI
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public UserDAO userDAO(JdbcContext jdbcContext, JdbcTemplate jdbcTemplate, DataSource dataSource){
        return new UserDAO(dataSource, jdbcContext, jdbcTemplate);
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


    // AOP를 위한 분리
    @Bean
    public UserService userService(){
        UserServiceImpl userService = new UserServiceImpl();

        return new UserServiceTx(platformTransactionManager(dataSource()),userService);
    }
}
