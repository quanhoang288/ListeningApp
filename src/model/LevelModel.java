/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import DAO.ExerciseDAO;
/**
 *
 * @author DELL 7577
 */
public class LevelModel {
    private List<Exercise> exerciseList;
    private final int level;
    public LevelModel(int level){
        this.level = level;

        exerciseList = getAllExerciseByLevel(level);
    }
    public List<Exercise> getAllExerciseByLevel(int level){
        return ExerciseDAO.getAllExerciseByLevel(level); 
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
        //exerciseList = getExerciseByLevel(level);
    }
    /*
    public ArrayList<Exercise> getExerciseByLevel(int level){
        return new ArrayList<>();
    }
    */
    
    public Exercise getExerciseByTitle(String title){
        Exercise res = null;
        for (int i = 0; i < exerciseList.size(); ++i){
            Exercise tmp = exerciseList.get(i);
            if (tmp.getTitle().equalsIgnoreCase(title)){
                res = tmp;
                break;
            }
        }
        return res;
    }
    

    public int getLevel() {
        return level;
    }

    
}
