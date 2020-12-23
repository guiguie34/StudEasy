package com.github.studeasy.logic.utils;

import com.mysql.cj.MysqlConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesEnv {

    public static Properties getDatabaseProperties()
    {
        Properties p = new Properties();
        try (InputStream in = MysqlConnection.class.getClassLoader().getResourceAsStream("environment/database.properties"))
        {
            if (in == null)
            {
                throw new NullPointerException("You must specify a database.properties file");
            }
            p.load(new InputStreamReader(in));
        }
        catch ( IOException e)
        {
            e.printStackTrace();
        }
        return p;
    }

    public static Properties getEmailProperties()
    {
        Properties p = new Properties();
        try (InputStream in = MysqlConnection.class.getClassLoader().getResourceAsStream("environment/email.properties"))
        {
            if (in == null)
            {
                throw new NullPointerException("You must specify a email.properties file");
            }
            p.load(new InputStreamReader(in));
        }
        catch ( IOException e)
        {
            e.printStackTrace();
        }
        return p;
    }
}
