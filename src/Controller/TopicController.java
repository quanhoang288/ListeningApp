package Controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;

import javax.swing.JLabel;

import View.*;
import Model.Exercise;
import Model.ExerciseModel;
import Model.LevelModel;
import Model.Track;
import controller.LessonController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        Track track1 = new Track("190121-fortnite_1.wav", "transcript.txt", 8);
        Track track2 = new Track("191004-living-by-the-sea_4.wav", "transcript4.txt", 14);
        Exercise currentExercise = new Exercise();
        currentExercise.getListTrack().add(track1);
        currentExercise.getListTrack().add(track2);
        currentExercise.setLevel(1);
        currentExercise.setTitle("Some random title!");
        ExerciseModel exerciseModel = new ExerciseModel(currentExercise);
        ExerciseModel em = new ExerciseModel(currentExercise);
        //System.out.println(em.getCurrentExercise().getListTrack().size());
        //System.out.println(ex.getListTrack().size());
        LessonPanel lp = new LessonPanel();
        //lp.setMediaBar(em.getCurrentExercise().getListTrack().get(exerciseModel.getCurrentTrack()).getTime());
        int trackLen = em.getCurrentExercise().getListTrack().get(exerciseModel.getCurrentTrack()).getTime();
        if (trackLen > 9)
            lp.getTrackLen().setText("00:" + Integer.toString(trackLen));
        else 
            lp.getTrackLen().setText("00:0" + Integer.toString(trackLen));
        //System.out.println(Integer.toString(em.getCurrentExercise().getListTrack().get(exerciseModel.getCurrentTrack()).getTime()));
        try {
            LessonController lc = new LessonController(em, lp, this);
        } catch (IOException ex) {
            Logger.getLogger(TopicController.class.getName()).log(Level.SEVERE, null, ex);
        }
        MainFrame.refresh(lp);
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

    
}