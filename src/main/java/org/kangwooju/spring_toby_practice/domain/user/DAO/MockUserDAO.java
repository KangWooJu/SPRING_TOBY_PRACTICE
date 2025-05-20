package org.kangwooju.spring_toby_practice.domain.user.DAO;

import org.kangwooju.spring_toby_practice.domain.user.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class MockUserDAO implements TobyDAO{

    private List<User> users;
    private List<User> updated = new ArrayList<User>();

    private MockUserDAO(List<User> users){
        this.users = users;
    }

    @Override
    public void add(User user) {

    }

    @Override
    public User get(String id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int getCount() {
        return 0;
    }
}
