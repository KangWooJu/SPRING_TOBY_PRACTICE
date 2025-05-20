package org.kangwooju.spring_toby_practice.domain.user.Service;

// 타겟 클래스
public class HelloTarget implements Hello {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public String sayHi(String name) {
        return "Hi " + name;
    }

    @Override
    public String sayThankyou(String name) {
        return "Thank you " + name ;
    }
}
