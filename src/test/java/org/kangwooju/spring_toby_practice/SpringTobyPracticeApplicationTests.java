package org.kangwooju.spring_toby_practice;

import org.junit.jupiter.api.Test;
import org.kangwooju.spring_toby_practice.domain.user.Service.Interface.UserService;
import org.kangwooju.spring_toby_practice.domain.user.Service.MockMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


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

    // Java의 Reflection API 학습 테스트
    @Test
    public void invokeMethod() throws Exception{
        String name = "Spring";

        assertThat(name.length(),is(6));

        Method lengthMethod = String.class.getMethod("length");
        assertThat((Integer)lengthMethod.invoke(name),is(6));

        assertThat(name.charAt(0),is('S'));

        Method charAtMethod = String.class.getMethod("charAt");
        assertThat((Character)charAtMethod.invoke(name),is('S'));
    }

}
