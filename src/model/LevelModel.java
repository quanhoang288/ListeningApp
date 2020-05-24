
package Model;

import java.sql.SQLException;
import java.util.ArrayList;
import DAO.ExerciseDAO;

public class LevelModel {
    private int level;
    private List<Exercise> exerciseList;
    public LevelModel(int level) {
        this.level = level;
        exerciseList = getAllExerciseByLevel(level);
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    

    public ArrayList<Exercise> getAllExerciseByLevel(int level) {
        try {
            return ExerciseDAO.getAllExerciseByLv(level);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public int getLevel() {
        return level;
    }

    
}
