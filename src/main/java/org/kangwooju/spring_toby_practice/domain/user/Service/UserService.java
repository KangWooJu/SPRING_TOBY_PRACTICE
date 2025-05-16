package org.kangwooju.spring_toby_practice.domain.user.Service;

import lombok.Data;
import org.kangwooju.spring_toby_practice.domain.user.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import javax.mail.*;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Service
public class UserService {


    private User user;

    @Autowired
    private DataSource dataSource;


    // 1st. PlatFormTransactionManager 을 DI한 후 @Autowired를 통해 오브젝트 가져오기
    @Autowired
    private PlatformTransactionManager platformTransactionManager;



    public void upgradeLevels() throws Exception{

        /* 트랜잭션 추상화 전
        TransactionSynchronizationManager.initSynchronization();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        connection.setAutoCommit(false); -> 자동 커밋 끄기

         */


        // 2nd. TransactionStatus를 통해 트렌잭션 가져오기
        TransactionStatus status = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        // 기타 로직
        try{

            // connection.commit(); -> 추상화 도입전
            // 기타로직
            platformTransactionManager.commit(status);

        } catch ( Exception e ){
            // 에러 처리 로직
            //connection.rollback();
            platformTransactionManager.rollback(status);

        } finally {


            // 트랜잭션 추상화 적용전

                    // 1st. TransactionSynchronizationManager.unbindResource(dataSource); // 리소스 풀기
                    // 2nd. DataSourceUtils.releaseConnection(connection,dataSource);  // 커넥션 풀기
                    // 3rd. TransactionSynchronizationManager.clearSynchronization(); // 동기화 제거
                    // 4th. ResultSet.close(); // ResultSet 비우기
                    // 5th. PrepareedStatement.close(); // PreparedStatement 비우기

                }
    }

    private void sendEmail(User user){
        Properties properties = new Properties();
        properties.put("mail.smtp.host","mail.ksug.org");
        Session session = Session.getInstance(properties,null);

    }



}

