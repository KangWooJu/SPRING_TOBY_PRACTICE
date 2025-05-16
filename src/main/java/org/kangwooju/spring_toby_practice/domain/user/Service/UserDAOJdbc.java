package org.kangwooju.spring_toby_practice.domain.user.Service;

import org.kangwooju.spring_toby_practice.domain.user.DAO.TobyDAO;
import org.kangwooju.spring_toby_practice.domain.user.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;


public class UserDAOJdbc implements TobyDAO {

    @Autowired
    private DataSource dataSource;

    public UserDAOJdbc(DataSource dataSource){
        this.dataSource = dataSource;
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
