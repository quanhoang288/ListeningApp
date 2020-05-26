
package Model;

import java.util.ArrayList;
import java.util.List;

public class Exercise {

    private String title;
    private int level;
    private List<Track> listTrack;
    private int time, highScore;
    public Exercise(){
        listTrack = new ArrayList<Track>();
    }

    public Exercise(int time, int highScore, String title, int level, ArrayList<Track> listTrack) {
        this.time = time;
        this.highScore = highScore;
        this.title = title;
        this.level = level;
        this.listTrack = listTrack;
    }

    public List<Track> getListTrack() {
        return listTrack;
    }

    public void setListTrack(List<Track> listTrack) {
        this.listTrack = listTrack;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highscore) {
        this.highScore = highscore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
