/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.SQLException;
import java.util.ArrayList;
import DAO.ExerciseDAO;

/**
 *
 * @author DELL 7577
 */
public class LevelModel {
    private int level;

    public LevelModel(int level) {
        this.level = level;
    }

    public ArrayList<Exercise> getAllExerciseByLevel(int level) {
        try {
            return ExerciseDAO.getAllExerciseByLv(level);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public int getLevel() {
        return level;
    }

    
}
