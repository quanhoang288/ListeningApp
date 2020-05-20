package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Track;
import Model.Exercise;
public class ExerciseDAO {
    static ArrayList<Exercise> list = new ArrayList<>();
    public static ArrayList<Exercise> getAllExerciseByLv(int Lv) throws SQLException{
    Connection connection = JDBCConnection.getJDBCConnection();
    String sql = "SELECT exercise.Title,exercise.Lv "
            + "FROM exercise "
            + "WHERE exercise.Lv = " +Lv;
    try{
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        ResultSet rs = prepareStatement.executeQuery();
        while (rs.next()){
            Exercise ex = new Exercise();
            ex.setTitle(rs.getString("Title"));
            ex.setLevel(rs.getInt("Lv"));
            list.add(ex);
        }
    } catch (SQLException e){
        e.printStackTrace();
        }
    return list;
    }


    
    public static Exercise getExerciseByTitle(String title, int lv) throws SQLException{
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT track.Audio,  track.Transcript,track.Time FROM track INNER JOIN exercise On track.exerciseID = exercise.idExercise AND exercise.Title ="+ "\"" +title+ "\"";
        Exercise ex = new Exercise();
        try{
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            ResultSet rs = prepareStatement.executeQuery();
            ArrayList<Track> listTrack = new ArrayList<>();
            while (rs.next()){
                Track tr = new Track();
                tr.setAudio(rs.getString("Audio"));
                tr.setTranscript(rs.getString("Transcript"));
                tr.setTime(rs.getInt("Time"));
                listTrack.add(tr);
                }
                ex.setLevel(lv);
                ex.setTitle(title);
                ex.setListTrack(listTrack);
            }
            
        catch (SQLException e){
            e.printStackTrace();
            }
        return ex;   
        }
}