package explicitteam.miptevents.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by dmitry on 19.11.16.
 */
public class CDatabase {

    private Connection connection;

    private Statement statement;

    private ResultSet resultSet;

    private String url;

    private String login;

    private String password;

    private Set<Integer> forbiddenTypes;

    private Set<Integer> forbiddenThemes;

    private Set<Integer> forbiddenDepartments;


    public CDatabase(Set<Integer> forbiddenTypes,Set<Integer> forbiddenThemes,Set<Integer> forbiddenDepartments) {
        this.forbiddenDepartments = forbiddenDepartments;
        this.forbiddenThemes = forbiddenThemes;
        this.forbiddenTypes = forbiddenTypes;
    }

    public void connect() throws SQLException {
        connection = null;
        connection = DriverManager.getConnection(url,login,password);
        statement = connection.createStatement();
    }


    public void openTables() throws SQLException {
        String query = "CREATE TABLE 'events' ( 'id' INTEGER NOT NULL, 'title' TEXT NOT NULL, " +
                "'description' TEXT NOT NULL, 'place' TEXT NOT NULL, 'date' DATETIME, " +
                "'tagType' INTEGER, 'tagTheme' INTEGER , 'reference' text, 'tagDepartment' INTEGER, PRIMARY KEY('id') )";
        statement.executeQuery(query);
        query = "CREATE TABLE IF NOT EXISTS 'typeTable' ('id' INTEGER NOT NULL, 'tag' VARCHAR(30) )";
        statement.executeQuery(query);
        query = "CREATE TABLE IF NOT EXISTS 'themeTable' ('id' INTEGER NOT NULL, 'tag' VARCHAR(30) )";
        statement.executeQuery(query);
        query = "CREATE TABLE IF NOT EXISTS 'departementTable' ('id' INTEGER NOT NULL, 'tag' VARCHAR(30) )";
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
        String query = "SELECT * FROM 'events'";
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

}
