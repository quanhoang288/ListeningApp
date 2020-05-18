/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.SQLException;

import DAO.ExerciseDAO;

/**
 *
 * @author DELL 7577
 */
public class ExerciseModel {
    private Exercise currentExercise;
    private int currentTrackScore = 0;

    public ExerciseModel(Exercise ex) {
        this.currentExercise = ex;
    }

    public Exercise getCurrentExercise() {
        return currentExercise;
    }

    public int getCurrentTrackScore() {
        return currentTrackScore;
    }

    public void setCurrentTrackScore(int currentTrackScore) {
        this.currentTrackScore = currentTrackScore;
    }

    public Exercise getExcerciseByTitle(String title) {
        try {
            return ExerciseDAO.getExerciseByTitle(title, 1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    
    
    
    
}
