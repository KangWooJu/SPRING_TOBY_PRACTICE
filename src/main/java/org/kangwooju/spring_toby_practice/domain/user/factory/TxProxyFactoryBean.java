package org.kangwooju.spring_toby_practice.domain.user.factory;

import org.kangwooju.spring_toby_practice.domain.user.Service.TransactionHandler;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

public class TxProxyFactoryBean implements FactoryBean<Object> {

    public Object target;
    public PlatformTransactionManager platformTransactionManager;
    public String pattern;
    public Class<?> serviceInterface;

    public TxProxyFactoryBean
            (Object target,
             PlatformTransactionManager platformTransactionManager,
             String pattern,
             Class<?> serviceInterface){

        this.target = target;
        this.platformTransactionManager = platformTransactionManager;
        this.pattern = pattern;
        this.serviceInterface = serviceInterface;
    }

    // 트랜잭션 동적 프록시를 생성하는 메소드
    @Override
    public Object getObject() throws Exception {
        TransactionHandler transactionHandler
                = new TransactionHandler(target,pattern,platformTransactionManager); // 객체 초기화

        return Proxy.newProxyInstance(
                getClass().getClassLoader(),new Class[] { serviceInterface },
                transactionHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
