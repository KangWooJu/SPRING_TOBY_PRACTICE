package org.kangwooju.spring_toby_practice;

import org.junit.jupiter.api.Test;
import org.kangwooju.spring_toby_practice.domain.user.Service.Interface.UserService;
import org.kangwooju.spring_toby_practice.domain.user.Service.MockMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringTobyPracticeApplicationTests {

    @Autowired
    UserService userService;



    @Test
    void contextLoads() {
    }

    @Test
    public void upgradeLevels() throws Exception{
        MockMailSender mockMailSender = new MockMailSender();
        // mailSender 의존성 주입 필요
    }

}
