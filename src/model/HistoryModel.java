package Model;
import java.sql.SQLException;
import java.util.ArrayList;
import DAO.HistoryDAO;

public class HistoryModel {
    private ArrayList<History> listHistory;
    public HistoryModel() {
        try {
            listHistory = HistoryDAO.getAllHistory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<History> getListHistory() {
        return listHistory;
    }
    
}