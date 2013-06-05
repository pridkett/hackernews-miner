/*
 * Copyright (c) 2012 IBM Corporation 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.wagstrom.research.hackernews;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.wagstrom.research.hackernews.dbobjs.Item;
import net.wagstrom.research.hackernews.dbobjs.Karma;
import net.wagstrom.research.hackernews.dbobjs.Story;
import net.wagstrom.research.hackernews.dbobjs.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseDriver {
    
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    private Logger logger = null;
    
    private static final String DERBY_TABLE_EXISTS = "X0Y32";
    
    private static final String UPDATE_TABLE = 
            "CREATE TABLE update" +
            "(id int primary key generated always as identity," +
            "create_date timestamp not null default CURRENT_TIMESTAMP)";
    private static final String PAGE_CONTENT_TABLE =
            "CREATE TABLE pagecontent" +
            "(id int primary key generated always as identity," +
            "item_id int not null," +
            "url varchar(4096)" +
            "content text," +
            "is_hn boolean," +
            "update_id int not null," +
            "create_date timestamp not null default CURRENT_TIMESTAMP)";
    private static final String ITEM_TABLE =
            "CREATE TABLE story" +
            "(id int primary key generated always as identity," +
            "item_id int not null," +
            "user_id int not null," +
            "parent_id int," +
            "votes int," +
            "update_id int not null)";
    private static final String USER_TABLE =
            "CREATE TABLE user" +
            "(id int primary key generated always as identity," +
            "name varchar(255) not null," +
            "hn_create_date timestamp," +
            "hn_create_date_text varchar(255) not null," +
            "create_date timestamp not null default CURRENT_TIMESTAMP)";
    private static final String KARMA_TABLE =
            "CREATE TABLE karma" +
            "(id int primary key generated always as identity," +
            "user_id int," +
            "update_id int not null," +
            "karma int not null," +
            "create_date timestamp not null default CURRENT_TIMESTAMP)";
    
    private Properties props; 
    
    private HashMap<String, Integer> categoryMap = null;
    private HashMap<String, Integer> repositoryMap = null;
    
    public DatabaseDriver() {
        logger = LoggerFactory.getLogger(DatabaseDriver.class);
        props = HackernewsMinerProperties.props();
        categoryMap = new HashMap<String, Integer>();
        repositoryMap = new HashMap<String, Integer>();
        try {

            Class.forName(props.getProperty(PropNames.JDBC_DRIVER, Defaults.JDBC_DRIVER)).newInstance();
            connect = DriverManager
                    .getConnection(props.getProperty(PropNames.JDBC_URL, Defaults.JDBC_URL));
            createTables();
        } catch (Exception e) {
            logger.error("Exception caught: ", e);
        }
    }

    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            logger.error("SQLException caught closing database: ", e);
        }
    }
    
    public void createTables() {
        createTable(UPDATE_TABLE);
        createTable(PAGE_CONTENT_TABLE);
        createTable(ITEM_TABLE);
        createTable(USER_TABLE);
        createTable(KARMA_TABLE);
    }
    
    private void createTable(String tableString) {
        try {
            Statement statement = connect.createStatement();
            statement.execute(tableString);
            statement.close();
        } catch (SQLException e) {
            if (e.getSQLState().equals(DERBY_TABLE_EXISTS)) {
                logger.info("Table already exists:\n{}", tableString);
            } else {
                logger.error("Error creating table state={}:\n{}", new Object[]{e.getSQLState(), tableString, e});
            }
        }
    }
    
    private int createUpdate() {
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO update", Statement.RETURN_GENERATED_KEYS);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                int rv = rs.getInt(1);
                rs.close();
                statement.close();
                return rv;
            }
        } catch (SQLException e) {
            logger.error("SQL exception creating update: ", e);
        }
        return -1;
    }
    
    private User getUser(String username) {
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT id, name, hn_create_date, hn_create_date_text, create_text FROM user WHERE name=?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User rv = new User();
                rv.setId(rs.getInt("id"));
                rv.setName(rs.getString("name"));
                rv.setHnCreateDate(rs.getDate("hn_create_date"));
                rv.setHnCreateDateText(rs.getString("hn_create_date_text"));
                rv.setCreateDate(rs.getDate("create_date"));
                rs.close();
                statement.close();
                return rv;
            }
        } catch (SQLException e) {
            logger.error("SQL exception getting user: ", e);
        }
        return null;
    }
    
    private User createUser(User u) {
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO user (name, hn_create_date, hn_create_date_text) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, u.getName());
            statement.setDate(2, (Date) u.getHnCreateDate());
            statement.setString(3, u.getHnCreateDateText());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                int id = rs.getInt(1);
                u.setId(rs.getInt(1));
                rs.close();
                statement.close();
                return u;
            }
        } catch (SQLException e) {
            logger.error("SQL exception creating user: {}", u, e);
        }
        return null;
    }
    
    private Karma createKarma(Karma k) {
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO karma(userId, updateId, karma) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, k.getUserId());
            statement.setInt(2, k.getUpdateId());
            statement.setInt(3, k.getKarma());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                k.setId(rs.getInt(1));
                rs.close();
                statement.close();
                return k;
            }
        } catch (SQLException e) {
            logger.error("SQL exception saving karma: {}", k, e);
        }
        return null;
    }
    
    private Story createStory(Story s) {
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO story(item_id, user_id, parent_id, votes, update_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, s.getItemId());
            statement.setInt(2, s.getUserId());
            statement.setInt(3, s.getParentId());
            statement.setInt(4, s.getVotes());
            statement.setInt(5, s.getUpdateId());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                s.setId(rs.getInt(1));
                rs.close();
                statement.close();
                return s;
            }
        } catch (SQLException e) {
            logger.error("SQL exception saving story: {}", s, e);
        }
        return null;
    }
    
    private Item createItem(Item i) {
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO item(item_id, url, text, is_hn, update_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, i.getItemId());
            statement.setString(2, i.getUrl());
            statement.setString(3, i.getText());
            statement.setBoolean(4, i.getIsHn());
            statement.setInt(5, i.getUpdateId());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                i.setId(rs.getInt(1));
                rs.close();
                statement.close();
                return i;
            }
        } catch (SQLException e) {
            logger.error("SQL exception saving item: {}", i, e);
        }
        return null;
    }
    
}
