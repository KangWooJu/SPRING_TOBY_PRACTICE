package org.kangwooju.spring_toby_practice.domain.user.Service;

// 데코레이터
public class HelloUppercase implements Hello {

    private Hello hello;

    public HelloUppercase(Hello hello){
        this.hello = hello;
    }

    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankyou(String name) {
        return hello.sayThankyou(name).toUpperCase();
    }
}
