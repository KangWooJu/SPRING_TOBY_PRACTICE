package org.kangwooju.spring_toby_practice;

import org.kangwooju.spring_toby_practice.Global.Config.DAOFactory;
import org.kangwooju.spring_toby_practice.domain.user.DAO.UserDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class SpringTobyPracticeApplication {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(DAOFactory.class);
        UserDAO userDAO = context.getBean("UserDAO", UserDAO.class);
        //

        SpringApplication.run(SpringTobyPracticeApplication.class, args);
    }

}
