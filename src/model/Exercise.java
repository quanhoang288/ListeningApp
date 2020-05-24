
package Model;

import java.util.ArrayList;
import java.util.List;

public class Exercise {

    private String Title;
    private int Level;
    private List<Track> trackList;
    private int Time;
    private int HighScore;
    public Exercise(){
        trackList = new ArrayList<Track>();
    }

    
    public List<Track> getTrackList() {
        return trackList;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        level = level;
    }
/*

    private int Time;
    private int HighScore;
    private String Title;
    private int Level;
    private int currentTrack;
    private ArrayList<Track> listTrack;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(int currentTrack) {
        this.currentTrack = currentTrack;
    }

    public ArrayList<Track> getListTrack() {
        return listTrack;
    }
  

    

    public void setListTrack(ArrayList<Track> listTrack) {
        this.listTrack = listTrack;
    }

    public Exercise() {
        listTrack = new ArrayList<Track>();
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }




    public int getHighScore() {
        return HighScore;
    }

    public void setHighScore(int highScore) {
        HighScore = highScore;
    }

    public Exercise(int time, int highScore, String title, int level, int currentTrack, ArrayList<Track> listTrack) {
        Time = time;
        HighScore = highScore;
        Title = title;
        Level = level;
        this.currentTrack = currentTrack;
        this.listTrack = listTrack;
    }



*/

    public Exercise(String title, int level, List<Track> trackList, int time, int highScore) {
        this.Title = title;
        this.Level = level;
        this.trackList = trackList;
        Time = time;
        HighScore = highScore;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public int getHighScore() {
        return HighScore;
    }

    public void setHighScore(int highScore) {
        HighScore = highScore;
    }

    @Override
    public String toString() {
        return "Exercise [HighScore=" + HighScore + ", Level=" + Level + ", Time=" + Time + ", Title=" + Title
                + ", trackList=" + trackList + "]";
    }
  
    
}
