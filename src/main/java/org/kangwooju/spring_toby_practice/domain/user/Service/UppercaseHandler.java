package org.kangwooju.spring_toby_practice.domain.user.Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

    private Hello hello;

    public UppercaseHandler(Hello hello){
        this.hello = hello;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String ret = (String)method.invoke(hello,args); // 타켓으로 위임. 인터페이스 메소드 호출에 모두 적용된다.
        return ret.toUpperCase();
    }
}
