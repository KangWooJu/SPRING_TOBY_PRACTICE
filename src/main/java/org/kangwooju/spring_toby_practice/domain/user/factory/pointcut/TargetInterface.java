package org.kangwooju.spring_toby_practice.domain.user.factory.pointcut;

public interface TargetInterface {

    void hello();
    void hello(String a);
    int minus(int a,int b) throws RuntimeException;
    int plus(int a,int b);
}
