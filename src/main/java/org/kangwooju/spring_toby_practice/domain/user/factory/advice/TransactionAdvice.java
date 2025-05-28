package org.kangwooju.spring_toby_practice.domain.user.factory.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.kangwooju.spring_toby_practice.domain.user.Service.TransactionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {


    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    public TransactionAdvice(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        TransactionStatus transactionStatus =
                this.platformTransactionManager
                        .getTransaction(new DefaultTransactionDefinition());

        try{
            Object ret = invocation.proceed();
            this.platformTransactionManager
                    .commit(transactionStatus);

            return ret;

        }catch( RuntimeException e){
            this.platformTransactionManager
                    .rollback(transactionStatus);

            throw e;
        }
    }
}
