package org.kangwooju.spring_toby_practice.Global.Config;

import org.kangwooju.spring_toby_practice.domain.user.DAO.AccountDAO;
import org.kangwooju.spring_toby_practice.domain.user.DAO.JdbcContext;
import org.kangwooju.spring_toby_practice.domain.user.DAO.MessageDAO;
import org.kangwooju.spring_toby_practice.domain.user.DAO.UserDAO;
import org.kangwooju.spring_toby_practice.domain.user.Service.*;
import org.kangwooju.spring_toby_practice.domain.user.Service.Interface.UserService;
import org.kangwooju.spring_toby_practice.domain.user.factory.TxProxyFactoryBean;
import org.kangwooju.spring_toby_practice.domain.user.factory.advice.TransactionAdvice;
import org.kangwooju.spring_toby_practice.domain.user.factory.pointcut.NameMatchClassMethodPointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
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
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

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
    /* 수동 데코레이터 패턴으로 연결
    @Bean
    public UserService userService(){
        UserServiceImpl userService = new UserServiceImpl();

        return new UserServiceTx(platformTransactionManager(dataSource()),userService);
    }

     */

    /*
    @Bean
    public Hello hello(){
        return new HelloTarget(); // 타켓 생성
    }

     */

    @Bean
    public HelloUppercase helloUppercase(){
        return new HelloUppercase(hello()); // 데코레이팅 생성
    }

    // 다이나믹 프록시 도입 -> 프록시 생성
    @Bean
    public Hello hello(){
        Hello hello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { Hello.class },
                new UppercaseHandler(new HelloTarget()));

        return hello;
    }

    // 다이나믹 프록시 도입 : UserService

    /*
    @Bean
    public UserService userService() {
        UserServiceImpl target = new UserServiceImpl(); // 실제 서비스 구현

        TransactionHandler txHandler = new TransactionHandler(
                target,
                "upgrade", // 트랜잭션을 적용할 메서드 이름 패턴
                platformTransactionManager(dataSource()) // 수동으로 전달
        );

        return (UserService) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{UserService.class},
                txHandler
        );
    }

     */



    /*
    // factoryBean 도입 -> 다이나믹 프록시 자동생성 , 이제 구현체들을 빈에 등록하지 않아도 된다. !!
    // ProxyFactoryBean 도입 -> 이제 파라미터에는 어드바이저 객체만 받으면 된다. 또한 Target이 필요없어짐.
    @Bean
    public TxProxyFactoryBean txProxyFactoryBean
            (UserServiceTx userServiceTx,
             PlatformTransactionManager platformTransactionManager){

        return new TxProxyFactoryBean(
                userServiceTx,
                platformTransactionManager,
                "upgrade",
                UserService.class
        );
    }

     */

    // ProxyFactoryBean 등록
    // 빈 자동 생성기 도입으로 인해 삭제해도 무방
    /*
    @Bean
    public ProxyFactoryBean userService(Advisor transactionAdvisor){
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();

        // 실제 대상 객체
        proxyFactoryBean.setTarget(new UserServiceImpl());

        // 적용할 어드바이저
        proxyFactoryBean.addAdvisor(transactionAdvisor);

        // 인터페이스 타입 지정 ( 필수 x : 스프링이 인터페이스를 자동으로 감지해서 동적 프록시 생성 )
        // proxyFactoryBean.setProxyInterfaces(new Class[]{UserService.class});

        return proxyFactoryBean;
    }

     */
    // 빈 자동생성기를 통해 초기 상태로 회귀
    @Bean
    public UserService userService(DataSource dataSource,JavaMailSender javaMailSender){
        return new UserServiceImpl(dataSource,javaMailSender);
    }

    // 어드바이스 DI
    @Bean
    public TransactionAdvice transactionAdvice(PlatformTransactionManager platformTransactionManager){
        return new TransactionAdvice(platformTransactionManager);
    }

    // 포인트 컷 설정
    // 클래스 필터를 적용한 포인트 컷 설정
    @Bean
    public NameMatchClassMethodPointcut transactionPointcut(){
        NameMatchClassMethodPointcut nameMatchClassMethodPointcut = new NameMatchClassMethodPointcut();
        nameMatchClassMethodPointcut.setClassFilter(clazz -> clazz.getSimpleName().endsWith("ServiceImpl"));
        nameMatchClassMethodPointcut.setMappedName("upgrade*");

        return nameMatchClassMethodPointcut;
    }

    // 어드바이저 생성 ( 어드바이저 = 어드바이스 + 포인트 컷 )
    @Bean
    public Advisor transactionAdvisor(Pointcut transactionPointcut,TransactionAdvice transactionAdvice){
        return new DefaultPointcutAdvisor(transactionPointcut,transactionAdvice);
    }

    // 자동 프록시 생성기 등록
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }
}
