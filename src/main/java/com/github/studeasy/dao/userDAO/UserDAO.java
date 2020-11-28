package com.github.studeasy.dao.userDAO;

import com.github.studeasy.dao.DAO;

public interface UserDAO {
    void searchUser(String email, String password);
}