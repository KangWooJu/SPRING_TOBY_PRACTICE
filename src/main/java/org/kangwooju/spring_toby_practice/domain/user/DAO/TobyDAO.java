package org.kangwooju.spring_toby_practice.domain.user.DAO;

import org.kangwooju.spring_toby_practice.domain.user.Entity.User;

import java.util.List;

public interface TobyDAO {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();

}
