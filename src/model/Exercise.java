
package Model;

import java.util.ArrayList;

public class Exercise {

    private String title;
    private int level;
    private List<Track> listTrack;
    
    public Exercise(){
        listTrack = new ArrayList<Track>();
    }

    public Exercise(String title, int level, int currentTrack, List<Track> trackList) {
        this.title = title;
        this.level = level;
        this.trackList = trackList;
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
        Level = level;
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


    
    
    
    
    
}
