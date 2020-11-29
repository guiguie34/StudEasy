package com.github.studeasy.dao.userDAO;

import com.github.studeasy.logic.common.User;

public interface UserDAO {
    User loginUser(String email, String password) throws Exception;
}