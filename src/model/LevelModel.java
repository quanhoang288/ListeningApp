
package Model;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.ExerciseDAO;

public class LevelModel {
    private int level;
    public LevelModel(int level) {
        this.level = level;
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
