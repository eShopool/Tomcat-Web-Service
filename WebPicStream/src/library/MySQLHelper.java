package library;

import java.sql.*;

/**
 * This class is a tool class for MySQL connection by JDBC.
 * This class can do operations about searching information from remote database and return searching results.
 * @author Group 10
 */
public class MySQLHelper {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String dbUrl;
    private String tableName;
    private String sql;
    public Connection conn;
    private int ipIndex;

    FileHelper fileHelper;

    //Basic information of Database
    static final String USER = "admin";
    static final String password = "eshopool";
    static final String DB = "eshopool";
    static final String[] writeServer = {"192.168.0.171", "192.168.0.50"};
    static final String[] readServer = {"192.168.0.50"};

    /**
     * This is the Construction of this class.
     * @param table the table which wanted to be used.
     * @throws ClassNotFoundException
     */
    public MySQLHelper(String table) throws ClassNotFoundException{

        Class.forName(JDBC_DRIVER);
        LoadBalance lb = new LoadBalance(readServer);
        ipIndex = lb.readIP();
        System.out.println("ip: " + readServer[ipIndex]);
        dbUrl = "jdbc:mysql://" + readServer[ipIndex] + ":3306/" + DB + "?serverTimezone=UTC&?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true";
        tableName = table;
        connectToMySQL(lb);
    }

    /**
     * This method id used to connect the remote MySQL.
     */
    public void connectToMySQL(LoadBalance lb){
        try {
            System.out.print(dbUrl);
            conn = DriverManager.getConnection(dbUrl, USER, password);
            lb.effectWeightUp(ipIndex);
        } catch (SQLException e) {
            lb.effectWeightDown(ipIndex);
        }
    }

    /**
     * This class is used to close the connection to remote MySQL.
     * @throws SQLException
     */
    public void closedMySQL() throws SQLException {
        conn.close();
    }

    /**
     * This method is used to update the information about necessity commodities list.
     * @return the searching result
     * @throws SQLException
     */
    public String updateNecessities() throws SQLException {
        String result = "";
        sql = "SELECT c_img_id FROM " + tableName + " WHERE category_ID = 1 AND c_num > 0;";
        System.out.println("sql: " + sql);
        result = executeUpdate(sql);
        return result;
    }

    /**
     * This method is used to update the information about electricity commodities list.
     * @return the searching result
     * @throws SQLException
     */
    public String updateElectronic() throws SQLException {
        String result = "";
        sql = "SELECT c_img_id FROM " + tableName + " WHERE category_ID = 2 AND c_num > 0;";
        System.out.println("sql: " + sql);
        result = executeUpdate(sql);
        return result;
    }

    /**
     * This method is used to update the information about cosmetic commodities list.
     * @return the searching result
     * @throws SQLException
     */
    public String updateCosmetic() throws SQLException{
        String result = "";
        sql = "SELECT c_img_id FROM " + tableName + " WHERE category_ID = 3 AND c_num > 0;";
        System.out.println("sql: " + sql);
        result = executeUpdate(sql);
        return result;
    }

    /**
     * This method is used to update whole commodities which is used at App homepage.
     * @return the searching result
     * @throws SQLException
     */
    public String updateHomepage() throws SQLException {
        String result = "";
        sql = "SELECT c_img_id FROM " + tableName + " WHERE c_num > 0" + ";";
        System.out.println("sql: " + sql);
        result = executeUpdate(sql);
        return result;
    }

    /**
     * This method is used for Fuzzy Search and get relative research from remote database.
     * @param item the searching word from the user
     * @return the searching result
     * @throws SQLException
     */
    public String updateItem(String item) throws SQLException {
        String result = "";
        sql = "SELECT c_img_id FROM " + tableName + " WHERE c_name LIKE '%" + item + "%' AND c_num > 0;";
        System.out.println("sql: " + sql);
        result = executeItem(sql);
        return result;
    }

    /**
     * This method is used to do the searching operation by imported sql sentence.
     * @param sql the imported sql sentence.
     * @return the searching result
     */
    public String executeUpdate(String sql) {
        String result = "";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                result = result + resultSet.getString("c_img_id") + ".jpg\n";
            }
            if(result == null){
                result = "Empty!";
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(result);
        return result;
    }

    /**
     * This method is used to do the fuzzy searching operation by imported sql sentence.
     * @param sql the imported sql sentence.
     * @return the searching result
     */
    public  String executeItem(String sql){
        String result = "";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result = result + resultSet.getString("c_img_id") + ".jpg\n";
            }
            if(result.length() == 0){
                result = "eShopool does not have the product you want";
            }
            resultSet.close();
            statement.close();
            closedMySQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("result: " + result);
        return result;
    }

}
