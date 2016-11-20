package explicitteam.miptevents.Database;



import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by dmitry on 19.11.16.
 */
public class CDatabase implements Closeable{


    private Connection connection;

    private Statement statement;

    private ResultSet resultSet;

    private int userID = -1;

    private HashSet<Integer> bannedTags = new HashSet<Integer>();

    private Session session= null;


    public CDatabase(String user) {
        try {
            connect();
            String query = "SELECT ID FROM wp_users WHERE user_login =" +
                    " '" + user + "';";

            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                userID = resultSet.getInt("ID");
            }
           if(userID == -1) {
               resultSet.close();
               addNewUser(user);
            }
            initBannedSet();
        } catch (Exception e) {
            close();
        }

    }

    public int getUserID() {
        return userID;
    }

    private void addNewUser(String user)  {
        try {
            String query = "INSERT INTO wp_users (user_login,user_pass,user_nicename,user_email,user_url,user_activation_key,user_status,display_name) " +
                    "VALUES (" +
                    "'" + user + "','" + user + "','" + user + "',' no ',' no ',' no ',0,' " + user + "');";
            resultSet = statement.executeQuery(query);
            //resultSet.next();
            query = "SELECT ID FROM wp_users WHERE user_login =" +
                    "'" + user + "';";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                userID = resultSet.getInt("ID");
            }
        } catch (Exception e) {

        }
    }

    public ProfilePackage getProfile() {
        if(userID == -1) {
            return new ProfilePackage(false);
        }
        String query = "SELECT user_nicename,user_email FROM wp_users WHERE ID = " + userID + ";";

        try {
            resultSet = statement.executeQuery(query);

            String nicename = resultSet.getString("user_nicename");

            String email = resultSet.getString("user_email");
            return new ProfilePackage(nicename,email);
        } catch (Exception e) {
            return null;
        }
    }

    private void connect() throws SQLException {

        int lport=1126;
        String rhost="explicit.vdi.mipt.ru";
        String host="explicit.vdi.mipt.ru";
        int rport=3306;
        String user="root";
        String password="re1re1re1";
        String dbuserName = "root";
        String dbpassword = "re1re1re1";
        String url = "jdbc:mysql://localhost:"+lport+"/mipt_afisha";
        String driverName="com.mysql.jdbc.Driver";

        try{
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session=jsch.getSession(user, "10.55.166.246", 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            int assinged_port=session.setPortForwardingL(lport, rhost, rport);
            Class.forName(driverName).newInstance();
            connection = DriverManager.getConnection (url, dbuserName, dbpassword);
            statement = connection.createStatement();
        }catch(Exception e) {
            System.out.println(e);
            session.disconnect();
            connection.close();
        }
    }

    private void initBannedSet() throws SQLException {
        String query = "SELECT term_id FROM wp_banned_term WHERE user_id = " + userID + ";";
        resultSet = statement.executeQuery(query);
        while(resultSet.next()) {
            int num = resultSet.getInt("term_id");
            bannedTags.add(num);
        }
    }


    private DatabasePackage getNext() throws SQLException {

        if(resultSet.next()) {
            long id = resultSet.getLong("event_id");

            long ownerId = resultSet.getLong("event_owner");

            String eventName = resultSet.getString("event_name");

            Date eventDate = resultSet.getDate("event_start_date");

            Date eventTime = resultSet.getDate("event_start_time");

            String content = resultSet.getString("post_content");

            long locationId = resultSet.getLong("location_id");
            System.out.println(eventName);
            return new DatabasePackage(id, ownerId, eventName, eventDate, eventTime, content, locationId);
        };
        return null;
    }

    public String getName(long id)  {
        try {
            String query = "SELECT user_nicename FROM wp_users WHERE ID = " + id +  ";";
            String name = null;
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                name = resultSet.getString("user_nicename");
            }
            return name;
        } catch (Exception e) {
            return null;
        }

    }

    public String getLocation(long id) {
        try {
            String query = "SELECT location_name FROM wp_em_locations WHERE location_id =" + id + ";";

            String name = null;
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                name = resultSet.getString("location_name");
            }
            return name;
        } catch (Exception e) {
            return null;
        }
    }


    private ArrayList<DatabasePackage> getTempList() throws SQLException {
        String query = "SELECT * FROM wp_em_events WHERE event_start_date >= CURDATE() ORDER BY event_start_date;";
        resultSet = statement.executeQuery(query);
        ArrayList<DatabasePackage> result = new ArrayList<DatabasePackage>();
        DatabasePackage pack = getNext();
        while(pack != null) {
            result.add(pack);
            pack = getNext();
        }
        return result;
    }

    public ArrayList<DatabasePackage> getList() {
        try {
            System.out.println(userID);
            ArrayList<DatabasePackage> tempResult = getTempList();
            ArrayList<DatabasePackage> result = new ArrayList<DatabasePackage>();
            for (DatabasePackage item : tempResult) {
                HashSet<String> set = getTagCloud(item.getEventId());
                if (set != null) {
                    item.setTags(set);
                    result.add(item);
                }
            }
            return result;
        } catch(Exception e) {
            return null;
        }
    }

    public HashSet<String> getTagCloud(long id)  {
        try {
            String query = "SELECT term_id,name FROM wp_terms WHERE term_id IN " +
                    "(SELECT term_taxonomy_id FROM wp_term_relationships WHERE object_id = " + id + ");";

            resultSet = statement.executeQuery(query);
            HashSet<String> result = new HashSet<String>();
            while (resultSet.next()) {
                int tagId = resultSet.getInt("term_id");
                String tagName = resultSet.getString("name");
                if (!checkBanned(tagId)) {
                    return null;
                }

                result.add(tagName);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }


    private boolean checkBanned(int tagID) {
        if(bannedTags.contains(tagID)) {
            return false;
        }
        return true;
    }

    public void close() {
        session.disconnect();
        try {
            connection.close();
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            return;
        }
    }

}