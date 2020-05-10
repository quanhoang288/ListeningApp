/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Model.History;
/**
 *
 * @author Admin
 */
public class JDBCConnection {
    public static Connection getJDBCConnection(){
        final String url ="jdbc:mysql://localhost:3306/test";
        final String user ="root";
        final String password ="";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
/*
    public static void main(String args[]) throws SQLException{
        Connection connection = getJDBCConnection();
        if (connection == null){
            System.out.println("Fail");
        }else
            System.out.println("Succesful");
        History history = new History(4, 2, "The World", 70,"Tomorrow");
        HistoryDAO historyDAO = new HistoryDAO();
//        historyDAO.addHistory(history);
        for (History x : historyDAO.getAllHistory()){
            System.out.println(x.toString());
        }
    }
*/
}
