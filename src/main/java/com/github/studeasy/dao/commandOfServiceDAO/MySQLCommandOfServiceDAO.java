package com.github.studeasy.dao.commandOfServiceDAO;

import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Command of Service DAO using a MySQL database
 * Contains all the methods accessing Command of Service related data
 */
public class MySQLCommandOfServiceDAO extends CommandOfServiceDAO{

    @Override
    public void acceptTransaction(CommandOfService c) throws Exception {

    }

    @Override
    public void declineTransaction(CommandOfService c) throws Exception {

    }

    @Override
    public void applyForService(Service s) throws Exception {

    }

    @Override
    public void buyService(Service s) throws Exception {

    }

    @Override
    public void addFeedback(CommandOfService c) throws Exception {

    }
}
