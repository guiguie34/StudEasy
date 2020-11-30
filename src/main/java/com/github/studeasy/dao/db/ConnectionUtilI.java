package com.github.studeasy.dao.db;

import java.sql.Connection;

/**
 * Interface read-only to get the connection to the database
 */
public interface ConnectionUtilI {
    /**
     * Allows to retrieve the connection to the database
     * @return the connection to the database
     */
    Connection getDb();
}
