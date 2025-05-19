package org.kangwooju.spring_toby_practice.domain.user.Service;

import com.sun.mail.util.MessageRemovedIOException;
import lombok.Data;
import org.kangwooju.spring_toby_practice.domain.user.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import javax.mail.*;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Service
public class UserService {


    private User user;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JavaMailSender javaMailSender;


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

        } catch ( RuntimeException e ){
            // 에러 처리 로직
            //connection.rollback();
            platformTransactionManager.rollback(status);
            throw e;

        } finally {


            // 트랜잭션 추상화 적용전

                    // 1st. TransactionSynchronizationManager.unbindResource(dataSource); // 리소스 풀기
                    // 2nd. DataSourceUtils.releaseConnection(connection,dataSource);  // 커넥션 풀기
                    // 3rd. TransactionSynchronizationManager.clearSynchronization(); // 동기화 제거
                    // 4th. ResultSet.close(); // ResultSet 비우기
                    // 5th. PrepareedStatement.close(); // PreparedStatement 비우기

                }

    }

    /* Java EE에서 제공하는 이메일 API ( 저수준 , 추상화 x )
    private void sendEmail(User user) {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "mail.ksug.org");

            Session session = Session.getInstance(properties, null);
            MimeMessage mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress("useradmin@gmail.com"));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            mimeMessage.setSubject("유저 등급 안내");
            mimeMessage.setText("사용자님의 등급이 " + user.getLevel() + " 로 업그레이드 되었습니다.");

            Transport.send(mimeMessage);

        } catch(AddressException e){
            throw new RuntimeException("잘못된 메일 주소",e);
        }
        catch (MessagingException e) {
            throw new RuntimeException("메일 전송 중 오류 발생", e);
        }
    }

     */

    // Spring에서 제공하는 JavaMailSender 인터페이스 -> DI를 통해 Host 정보등록
    private void sendUpgradeEmail(User user){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("mail.ex.com");
        mailMessage.setSubject("메일 전송 예시");
        mailMessage.setText("안에 들어갈 텍스트");

        javaMailSender.send(mailMessage);

    }

}

