package org.kangwooju.spring_toby_practice.domain.user.Service.Interface;

import org.kangwooju.spring_toby_practice.domain.user.Entity.User;

public interface UserService {
    void upgradeLevels(User user) throws Exception;
    void sendUpgradeEmail(User user);

}
