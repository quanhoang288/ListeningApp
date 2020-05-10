/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author DELL 7577
 */
public class LessonModel {
    private Exercise currentExercise;
    private int currentTrackScore = 0;
    private String hint;
    public LessonModel(Exercise ex){
        this.currentExercise = ex;
    }
    public Exercise getCurrentExercise(){
        return currentExercise;
    }
    public String getCurrentTrackPath(){
        return currentExercise.getCurrentTrackPath();
    }
    public String getCurrentTrackTranscript(){
        return currentExercise.getCurrentTrackTranscript();
    }

    public int getCurrentTrackScore() {
        return currentTrackScore;
    }

    public void setCurrentTrackScore(int currentTrackScore) {
        this.currentTrackScore = currentTrackScore;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
    
    
    
    
}
