/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DELL 7577
 */
public class Exercise {
    private String title;
    private Date time;
    private final ArrayList<String> audioPath;
    private final ArrayList<String> transcript;
    private int currentTrack;
    private int level;
    public Exercise(){ 
        audioPath = new ArrayList<>();
        transcript = new ArrayList<>();
    }
    public Exercise(String title, Date time, ArrayList<String> audioPath, ArrayList<String> transcript, int level) {
        this.title = title;
        this.time = time;
        this.audioPath = audioPath;
        this.transcript = transcript;
        this.currentTrack = -1;
        this.level = level;
    }
    public String getCurrentTrackPath(){
        return audioPath.get(++currentTrack);
    }
    public String getTrackPath(int trackNo){
        if (trackNo > -1 && trackNo < audioPath.size()){
            currentTrack = trackNo;
            return audioPath.get(currentTrack);
        }
        return null;
    }
    public String getCurrentTrackTranscript(){
        return transcript.get(currentTrack);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    
    
}
