/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL 7577
 */
public class Exercise {
    private String title;
    private int level;
    private List<Track> trackList;
    
    public Exercise(){
        trackList = new ArrayList<>();
    }

    public Exercise(String title, int level, int currentTrack, List<Track> trackList) {
        this.title = title;
        this.level = level;
        this.trackList = trackList;
    }
    
    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
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
    
    
    
}
