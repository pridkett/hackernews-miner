package net.wagstrom.research.github.language;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseDriver {
    
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    private Logger logger = null;
    
    private static final String DERBY_TABLE_EXISTS = "X0Y32";
    
    private static final String UPDATE_TABLE = "CREATE TABLE GITHUBUPDATE (id int primary key generated always as identity," +
    		"create_date timestamp not null default CURRENT_TIMESTAMP)";
    private static final String LANGUAGE_TABLE = "CREATE TABLE LANGUAGE (id int primary key generated always as identity," +
    		"name varchar(64), create_date timestamp not null default CURRENT_TIMESTAMP)";
    private static final String REPOSITORY_TABLE = "CREATE TABLE REPOSITORY (id int primary key generated always as identity," +
    		"username varchar(64), reponame varchar(64), create_date timestamp not null default CURRENT_TIMESTAMP)";
    private static final String TOP_CATEGORY_TABLE = "CREATE TABLE TOPCATEGORY (id int primary key generated always as identity," +
    		"name varchar(64), create_date timestamp not null default CURRENT_TIMESTAMP)";
    private static final String PROJECT_UPDATE_TABLE = "CREATE TABLE PROJECTUPDATE(id int primary key generated always as identity," +
    		"update_id int not null constraint projectupdate_githubupdate_fk references GITHUBUPDATE(id)," +
    		"language_id int not null constraint projectudpate_language_fk references LANGUAGE(id)," +
    		"repository_id int not null constraint projectupdate_repository_fk references REPOSITORY(id)," +
    		"category_id int not null constraint projectupdate_topcategory_fk references TOPCATEGORY(id)," +
    		"rank int)";
    private static final String LANGUAGE_UPDATE = "CREATE TABLE languageupdate(id int primary key generated always as identity," +
            "update_id int not null constraint languageupdate_githubupdate_fk references GITHUBUPDATE(id)," +
    		"language_id int not null constraint languageupdate_language_fk references LANGUAGE(id)," +
    		"num_projects int not null," +
    		"rank int not null)";
    
    private Properties props; 
    
    private HashMap<String, Integer> categoryMap = null;
    private HashMap<String, Integer> repositoryMap = null;
    
    public DatabaseDriver() {
        logger = LoggerFactory.getLogger(DatabaseDriver.class);
        props = GitHubLanguageMinerProperties.props();
        categoryMap = new HashMap<String, Integer>();
        repositoryMap = new HashMap<String, Integer>();
        try {

            Class.forName(props.getProperty(PropNames.JDBC_DRIVER, Defaults.JDBC_DRIVER)).newInstance();
            connect = DriverManager
                    .getConnection(props.getProperty(PropNames.JDBC_URL, Defaults.JDBC_URL));
            createTables();
//            PreparedStatement statement = connect
//                    .prepareStatement("SELECT * from USERS");
//
//            resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                String user = resultSet.getString("name");
//                String number = resultSet.getString("number");
//                System.out.println("User: " + user);
//                System.out.println("ID: " + number);
//            }
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
        } catch (Exception e) {
            // do nothing
        }
    }
    
    public void createTables() {
        createTable(UPDATE_TABLE);
        createTable(LANGUAGE_TABLE);
        createTable(REPOSITORY_TABLE);
        createTable(TOP_CATEGORY_TABLE);
        createTable(PROJECT_UPDATE_TABLE);
        createTable(LANGUAGE_UPDATE);
    }
    
    private void createTable(String tableString) {
        try {
            PreparedStatement statement = connect.prepareStatement(tableString);
            statement.execute();
        } catch (SQLException e) {
            if (e.getSQLState().equals(DERBY_TABLE_EXISTS)) {
                logger.info("Table already exists:\n{}", tableString);
            } else {
                logger.error("Error creating table state={}:\n{}", new Object[]{e.getSQLState(), tableString, e});
            }
        }
    }
    
    private int getLanguage(String language) {
        int rv = -1;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT id FROM language WHERE name=?");
            statement.setString(1, language);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                rv = rs.getInt("id");
                rs.close();
                return rv;
            }
            return createLanguage(language);
        } catch (SQLException e) {
            logger.error("SQL exception getting language: {}", language, e);
        }
        return rv;
    }
    
    private int createLanguage(String language) {
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO language (name) values (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, language);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                int rv = rs.getInt(1);
                rs.close();
                return rv;
            }
        } catch (SQLException e) {
            logger.error("SQL exception creating language: {}", language, e);
        }
        return -1;
    }

    private int createGitHubUpdate() {
        try {
            Statement statement = connect.createStatement();
            statement.execute("INSERT INTO githubupdate (create_date) VALUES (CURRENT_TIMESTAMP)",
                    Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                logger.info("generated key: {}", rs.getInt(1));
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("SQL exception creating githubupdate:",e);
        }
        return -1;
    }

    private int createCategory(String categoryName) {
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO topcategory (name) values (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, categoryName);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                int rv = rs.getInt(1);
                rs.close();
                categoryMap.put(categoryName, rv);
                return rv;
            }
        } catch (SQLException e) {
            logger.error("SQL exception creating category: {}", categoryName, e);
        }
        return -1;
    }

    private int getCategory(String categoryName) {
        if (categoryMap.containsKey(categoryName)) { return categoryMap.get(categoryName); }
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT id FROM topcategory WHERE NAME=?");
            statement.setString(1, categoryName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int rv = rs.getInt("id");
                rs.close();
                statement.close();
                categoryMap.put(categoryName, rv);
                return rv;
            }
            return createCategory(categoryName);
        } catch (SQLException e) {
            logger.error("SQL exception getting category: {}", categoryName, e);
        }
        return -1;
    }
    
    private int createRepository(String username, String reponame) {
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO repository (username, reponame) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            statement.setString(2, reponame);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                int rv = rs.getInt(1);
                rs.close();
                repositoryMap.put(username + "/" + reponame, rv);
                return rv;
            }
        } catch (SQLException e) {
            logger.error("SQL exception creating repository: {}/{}", new Object[]{username, reponame, e});
        }
        return -1;
    }

    private int getRepository(String repositoryName) {
        repositoryName = repositoryName.trim();
        if (repositoryName.startsWith("/")) {
            repositoryName = repositoryName.substring(1);
        }
        // FIXME: this really should do more checking here...
        String [] parts = repositoryName.split("/");
        return getRepository(parts[0], parts[1]);
    }
    
    private int getRepository(String username, String reponame) {
        if (repositoryMap.containsKey(username + "/" + reponame)) {
            return repositoryMap.get(username + "/" + reponame);
        }
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT id FROM repository WHERE username=? and reponame=?");
            statement.setString(1, username);
            statement.setString(2, reponame);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int rv = rs.getInt("id");
                rs.close();
                statement.close();
                repositoryMap.put(username + "/" + reponame, rv);
                return rv;
            }
            return createRepository(username, reponame);
        } catch (SQLException e) {
            logger.error("SQL exception getting category: {}/{}", new Object[]{username, reponame, e});
        }
        return -1;
    }
    
    private void saveTopProjects(int update_id, int language_id, String categoryName, Collection<String> repositories) {
        int category_id = getCategory(categoryName);
        int ctr = 1;
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO PROJECTUPDATE(update_id, language_id, repository_id, category_id, rank) values (?, ?, ?, ?, ?)");
            for (String s : repositories) {
                int repository_id = getRepository(s);
                statement.setInt(1, update_id);
                statement.setInt(2, language_id);
                statement.setInt(3, repository_id);
                statement.setInt(4, category_id);
                statement.setInt(5, ctr++);
                statement.execute();
            }
        } catch (SQLException e) {
            logger.error("SQL exception saving top projects language: {}, category: {}", new Object[]{language_id, categoryName, e});
        }
    }

    private void saveLanguageUpdate(int update_id, int language_id, int num_projects, int rank) {
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO languageupdate(update_id, language_id, num_projects, rank) VALUES (?, ?, ?, ?)");
            statement.setInt(1, update_id);
            statement.setInt(2, language_id);
            statement.setInt(3, num_projects);
            statement.setInt(4, rank);
            statement.execute();
        } catch (SQLException e) {
            logger.error("SQL exception saving language update language: {}, update: {}, num_projects: {}, rank: {}", new Object[]{language_id, update_id, num_projects, rank, e});
        }
    }
    
    public void saveProjectRecords(HashMap<String, ProjectRecord> records) {
        int update_id = createGitHubUpdate();
        logger.info("update id: {}", update_id);
        for (String language : records.keySet()) {
            int language_id = getLanguage(language);
            ProjectRecord record = records.get(language);
            saveTopProjects(update_id, language_id, Defaults.MOST_WATCHED, record.getMostWatchedProjects());
            saveTopProjects(update_id, language_id, Defaults.MOST_WATCHED_TODAY, record.getMostWatchedToday());
            saveTopProjects(update_id, language_id, Defaults.MOST_FORKED_TODAY, record.getMostForkedToday());
            saveTopProjects(update_id, language_id, Defaults.MOST_WATCHED_THIS_WEEK, record.getMostWatchedThisWeek());
            saveTopProjects(update_id, language_id, Defaults.MOST_FORKED_THIS_WEEK, record.getMostForkedThisWeek());
            saveTopProjects(update_id, language_id, Defaults.MOST_WATCHED_THIS_MONTH, record.getMostWatchedThisMonth());
            saveTopProjects(update_id, language_id, Defaults.MOST_FORKED_THIS_MONTH, record.getMostForkedThisMonth());
            saveTopProjects(update_id, language_id, Defaults.MOST_WATCHED_OVERALL, record.getMostWatchedOverall());
            saveTopProjects(update_id, language_id, Defaults.MOST_FORKED_OVERALL, record.getMostForkedOverall());
            saveLanguageUpdate(update_id, language_id, record.getNumProjects(), record.getRank());
        }
    }
}
