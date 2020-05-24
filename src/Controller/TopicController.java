package Controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.*;

import javax.swing.JLabel;

import View.*;
import Model.Exercise;
import Model.ExerciseModel;
import Model.LevelModel;
import Model.Track;
import DAO.ExerciseDAO;

public class TopicController implements ActionListener, MouseListener {
    private LevelModel levelModel;
    private TopicPanel topicPanel;

    public LevelModel getLevelModel() {
        return levelModel;
    }

    public TopicPanel getTopicPanel() {
        return topicPanel;
    }

    public TopicController(LevelModel levelModel, TopicPanel topicPanel) {
        topicPanel.getBackButton().addActionListener(this);
        this.levelModel = levelModel;
        this.topicPanel = topicPanel;
        for (int i = 0; i < topicPanel.getLessonLabel().length; i++) {
            topicPanel.getLessonLabel()[i].addMouseListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        topicPanel.setVisible(false);
        LevelController lc = new LevelController(new LevelPanel());
        MainFrame.refresh(lc.getLevelPanel());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        JLabel x = (JLabel) e.getSource();
        String title = x.getText();
        int level = levelModel.getLevel();
        Exercise ex;
        try {
            ex = ExerciseDAO.getExerciseByTitle(title, level);
            ExerciseModel em = new ExerciseModel(ex);
            LessonPanel lp = new LessonPanel();
            try {
                LessonController lc = new LessonController(em, lp);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            MainFrame.refresh(lp);
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }      
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        JLabel x = (JLabel) e.getSource();
        x.setForeground(Color.blue.brighter());
        topicPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        JLabel x = (JLabel) e.getSource();
        x.setForeground(Color.black);
        topicPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }  
    // public static void main(String[] args) {
    //     LevelModel lm = new LevelModel(1);
    //     for (Track x : lm.getAllExerciseByLevel(1).get(1).getTrackList()){
    //         System.out.println(x.toString());
    //     }
    //     System.out.println("True");
    //}
}