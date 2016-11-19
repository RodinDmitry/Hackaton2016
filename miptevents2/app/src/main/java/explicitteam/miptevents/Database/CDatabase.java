package explicitteam.miptevents.Database;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by dmitry on 19.11.16.
 */
public class CDatabase {

    private Connection connection;

    private Statement statement;

    private ResultSet resultSet;

    private String url = "jdbc:mysql:/10.55.166.246/base";

    private String login = "root";

    private String password = "root";

    private Set<Integer> forbiddenTypes;

    private Set<Integer> forbiddenThemes;

    private Set<Integer> forbiddenDepartments;

    private String user;

    public CDatabase(String user) throws SQLException {
        connect();
        openTables();
        initSet(user);
        this.user = user;
    }

    private void initSet(String user) throws SQLException {
        String query ="SELECT * FROM users WHERE user = '" + user+"'";
        resultSet = statement.executeQuery(query);
        while(resultSet.next()){
            String type = resultSet.getString("type");
            String theme = resultSet.getString("theme");
            String department = resultSet.getString("department");
            StringTokenizer tokenizerType = new StringTokenizer(type,"\\n",false);
            StringTokenizer tokenizerTheme = new StringTokenizer(theme,"\\n",false);
            StringTokenizer tokenizerDepartment = new StringTokenizer(department,"\\n",false);
            String token;
            while(tokenizerType.hasMoreTokens()) {
                token = tokenizerType.nextToken();
                forbiddenTypes.add(Integer.parseInt(token));
            }
            while(tokenizerTheme.hasMoreTokens()) {
                token = tokenizerTheme.nextToken();
                forbiddenThemes.add(Integer.parseInt(token));
            }
            while(tokenizerDepartment.hasMoreTokens()) {
                token = tokenizerDepartment.nextToken();
                forbiddenDepartments.add(Integer.parseInt(token));
            }
        }
    }

    public void connect() throws SQLException {
        connection = null;
        connection = DriverManager.getConnection(url,login,password);
        statement = connection.createStatement();
    }


    public void openTables() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS events ( id INTEGER NOT NULL, title TEXT NOT NULL, " +
                "description TEXT NOT NULL, place TEXT NOT NULL, date DATETIME, " +
                "tagType INTEGER, tagTheme INTEGER , reference text, tagDepartment INTEGER, PRIMARY KEY(id) )";
        statement.executeQuery(query);
        query = "CREATE TABLE IF NOT EXISTS users (id INTEGER NOT NULL, user text NOT NULL, type text NOT NULL, theme text NOT NULL" +
                ", department text)";
        statement.executeQuery(query);
        query = "CREATE TABLE IF NOT EXISTS favourites (id INTEGER NOT NULL, user text NOT NULL ," +
                "eventID INTEGER NOT NULL )";
        query = "CREATE TABLE IF NOT EXISTS typeTable (id INTEGER NOT NULL, tag VARCHAR(30) )";
        statement.executeQuery(query);
        query = "CREATE TABLE IF NOT EXISTS themeTable (id INTEGER NOT NULL, tag VARCHAR(30) )";
        statement.executeQuery(query);
        query = "CREATE TABLE IF NOT EXISTS departementTable (id INTEGER NOT NULL, tag VARCHAR(30) )";
        statement.executeQuery(query);

    }

    private DatabasePackage getNext() throws SQLException {
        if(resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String place  = resultSet.getString("place");
            Date date = resultSet.getDate("date");
            int tagType = resultSet.getInt("tagType");
            int tagTheme = resultSet.getInt("tagTheme");
            int tagDepartment = resultSet.getInt("tagDepartment");
            String reference = resultSet.getString("reference");
            return new DatabasePackage(id,title,description,place,date,tagType,tagTheme,tagDepartment,reference);
        }
        return null;
    }

    public List<DatabasePackage> getQueryResult() throws SQLException {
        String query = "SELECT * FROM events";
        resultSet = statement.executeQuery(query);
        List<DatabasePackage> result = new ArrayList<DatabasePackage>();
        DatabasePackage pack = getNext();
        while(pack != null) {
            if(checkForbidden(pack.getTagType(),pack.getTagTheme(),pack.getTagDepartment())) {
                continue;
            }
            result.add(pack);
        }
        return result;
    }

    private boolean checkForbidden(int type,int theme,int department) {
        if(forbiddenTypes.contains(type)) {
            return false;
        }
        if(forbiddenDepartments.contains(department)) {
            return false;
        }
        if(forbiddenThemes.contains(theme)) {
            return false;
        }
        return  true;
    }

    public void addNewFilterType(int filter) {
        forbiddenTypes.add(filter);
    }

    public void addNewFilterTheme(int filter) {
        forbiddenThemes.add(filter);
    }

    public void addNewFilterDepartment(int filter) {
        forbiddenDepartments.add(filter);
    }

    public void removeFilterType(int filter) {
        forbiddenTypes.remove(filter);
    }

    public void removeFilterTheme(int filter) {
        forbiddenThemes.remove(filter);
    }

    public void removeFilterDepartement(int filter) {
        forbiddenDepartments.remove(filter);
    }

    public List<DatabasePackage> getFavourites() throws SQLException {
        String query = "SELECT * FROM events WHERE id IN (SELECT eventID FROM favourites WHERE user = '" + user + "' )";
        List<DatabasePackage> result = new ArrayList<DatabasePackage>();
        resultSet = statement.executeQuery(query);
        DatabasePackage pack = getNext();
        while(pack != null) {
            result.add(pack);
        }
        return result;
    }

    public void close() throws SQLException {
        String type = " ";
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        for (int id : forbiddenTypes) {
            builder.append(" ").append(id);
        }
        type = builder.toString();
        builder = new StringBuilder();
        for(int id : forbiddenThemes) {
            builder.append(" ").append(id);
        }
        String theme = " " + builder.toString();
        builder = new StringBuilder();
        for(int id : forbiddenDepartments) {
            builder.append(" ").append(id);
        }
        String departement = " " + builder.toString();
        String query = "UPDATE users SET type = '" + type + "', theme = '" +
                theme + "', department = '" + departement + "' WHERE user = '" + user + "'";
        statement.executeQuery(query);
        statement.close();
        resultSet.close();
        connection.close();
    }
}