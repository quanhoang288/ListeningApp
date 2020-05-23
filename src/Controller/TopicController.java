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
        Track track1 = new Track();
        track1.setAudio("C:\\Users\\Admin\\Documents\\GitHub\\ListeningApp\\fortnite1.mp3");
        track1.setTranscript("C:\\Users\\Admin\\Documents\\GitHub\\ListeningApp\\transcript.txt");
        //ExerciseModel em = new ExerciseModel(new Exercise());
//        em.setCurrentExercise(em.getExcerciseByTitle(title, levelModel.getLevel()));
        //em.getCurrentExercise().getListTrack().add(track1);
        Exercise ex = new Exercise();
        ex.getListTrack().add(track1);
        ExerciseModel em = new ExerciseModel(ex);
        //System.out.println(em.getCurrentExercise().getListTrack().size());
        LessonPanel lp = new LessonPanel();
        LessonController lc = new LessonController(em, lp);
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