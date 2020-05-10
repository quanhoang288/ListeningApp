/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import Model.History;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Admin
 */
public class HistoryDAO {
    public List<History> getAllHistory() throws SQLException{
    List<History> list = new ArrayList<History>();
    Connection connection = JDBCConnection.getJDBCConnection();
    String sql = "SELECT * FROM test.history";
    try{
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        ResultSet rs = prepareStatement.executeQuery();
        while (rs.next()){
            History history = new History();
            history.setID(rs.getInt("ID"));
            history.setLevel(rs.getInt("Level"));
            history.setTopic(rs.getString("Topic"));
            history.setScore(rs.getInt("Score"));
            history.setDate(rs.getString("Date"));
            list.add(history);
        }
    } catch (SQLException e){
        e.printStackTrace();
        }
    return list;
    }
    public void addHistory(History history){
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "INSERT INTO test.history(ID, Level, Topic, Score, Date) VALUE(?,?,?,?,?)";
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, history.getID());
            prepareStatement.setInt(2, history.getLevel());
            prepareStatement.setString(3, history.getTopic());
            prepareStatement.setInt(4, history.getScore());
            prepareStatement.setString(5, history.getDate());
            int rs = prepareStatement.executeUpdate();
            System.out.println(rs);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
