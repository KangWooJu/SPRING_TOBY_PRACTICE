package org.kangwooju.spring_toby_practice.domain.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class TransactionHandler implements InvocationHandler {

    private Object target;
    private String pattern;

    private PlatformTransactionManager platformTransactionManager;

    public TransactionHandler(Object target,String pattern,PlatformTransactionManager platformTransactionManager){
        this.target = target;
        this.platformTransactionManager = platformTransactionManager;
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().startsWith(pattern)){
            return invokeInTransaction(method,args);
        }
        return method.invoke(target,args);
    }

    private Object invokeInTransaction(Method method,Object[] args) throws Throwable{

        TransactionStatus transactionStatus =
                this.platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Object ret = method.invoke(target,args);
            this.platformTransactionManager.commit(transactionStatus);
            return ret;

        } catch (InvocationTargetException e){
            this.platformTransactionManager.rollback(transactionStatus);
            throw e.getTargetException();
        }
    }

}
