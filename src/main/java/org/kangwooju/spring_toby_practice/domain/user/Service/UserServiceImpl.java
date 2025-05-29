package org.kangwooju.spring_toby_practice.domain.user.Service;

import org.kangwooju.spring_toby_practice.domain.user.Entity.Level;
import org.kangwooju.spring_toby_practice.domain.user.Entity.User;
import org.kangwooju.spring_toby_practice.domain.user.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@Service
public class UserServiceImpl implements UserService {


    private User user;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JavaMailSender javaMailSender;


    // 1st. PlatFormTransactionManager 을 DI한 후 @Autowired를 통해 오브젝트 가져오기
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    public UserServiceImpl(DataSource dataSource,JavaMailSender javaMailSender){
        this.dataSource = dataSource;
        this.javaMailSender = javaMailSender;
    }



    public void upgradeLevels(User user) throws Exception{

        /* 트랜잭션 추상화 전
        TransactionSynchronizationManager.initSynchronization();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        connection.setAutoCommit(false); -> 자동 커밋 끄기
         */

        // 2nd. TransactionStatus를 통해 트렌잭션 가져오기 -> AOP 도입으로 삭제


        // 기타 로직
        try{

            // connection.commit(); -> 추상화 도입전
            // 기타로직


        } catch ( RuntimeException e ){
            // 에러 처리 로직
            //connection.rollback();

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
    // DB 작업이 있는 경우 !!
    public void sendUpgradeEmail(User user){


        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setFrom("mail.ex.com");
            mailMessage.setSubject("메일 전송 예시");
            mailMessage.setText("안에 들어갈 텍스트");

            javaMailSender.send(mailMessage);
            // GOLD로 바꾸는 로직이 추가 되는 경우 -> SRP 위반이긴 하나 만약 DB에 접근하는 로직일 경우.....
            user = User.builder()
                    .level(Level.GOLD)
                    .build();


        }catch ( RuntimeException e ){
            throw e;
        }
    }

}

