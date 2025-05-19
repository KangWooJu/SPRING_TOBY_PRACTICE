package org.kangwooju.spring_toby_practice.domain.user.Service;

import org.kangwooju.spring_toby_practice.domain.user.Entity.Level;
import org.kangwooju.spring_toby_practice.domain.user.Entity.User;
import org.kangwooju.spring_toby_practice.domain.user.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class UserServiceTx implements UserService {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private UserService userService;

    private User user;


    @Override
    public void upgradeLevels(User user) throws Exception {
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        try{
            userService.upgradeLevels(user); // DI를 통해 UserServiceImpl의 upgradeLevels를 가져온다.

        }catch (RuntimeException e){
            platformTransactionManager.rollback(transactionStatus);
            throw e;
        }
    }

    @Override
    public void sendUpgradeEmail(User user) {
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        try{
           userService.sendUpgradeEmail(user); // AOP 도입으로 userServiceImpl의 sendUpgradeEmail을 가져온다.
            platformTransactionManager.commit(transactionStatus);

        }catch ( RuntimeException e ){
            platformTransactionManager.rollback(transactionStatus);
            throw e;
        }
    }

}
