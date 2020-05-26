package controller;

import View.LessonPanel;
import java.awt.Color;
import java.awt.EventQueue;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import Model.Exercise;
import Model.ExerciseModel;
import Model.Track;
import javax.swing.text.StyledDocument;
import org.jfree.data.xy.XYDataset;
import Controller.TopicController;
import Model.LevelModel;
import View.MainFrame;
import View.TopicPanel;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.MutableComboBoxModel;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class LessonController extends DocumentFilter implements ActionListener, LineListener, KeyListener, DocumentListener{
    private TopicController topicController;
    private ExerciseModel exerciseModel;
    private LessonPanel lessonPanel;
    public LessonController(ExerciseModel exerciseModel, LessonPanel lessonPanel, TopicController topicController) throws IOException{
        this.exerciseModel = exerciseModel;
        this.lessonPanel = lessonPanel;
        this.topicController = topicController;
        lessonPanel.getText().addKeyListener(this);
        exerciseModel.setTimer(new Timer(1000, this));
        exerciseModel.getTimer().setInitialDelay(0);
        exerciseModel.getClip().addLineListener(this);
        lessonPanel.getbPlay().addActionListener(this);
        lessonPanel.getbPlay().requestFocus();
        lessonPanel.getbNext().addActionListener(this);
        lessonPanel.getbListen().addActionListener(this);
        lessonPanel.getbBack().addActionListener(this);
        initTrackBox();
        lessonPanel.getTrackBox().addActionListener(this);
        
        exerciseModel.getTrackDatasets().add(lessonPanel.getDataset());
        exerciseModel.setTextDocument((AbstractDocument)this.lessonPanel.getText().getDocument());
        exerciseModel.getTextDocument().setDocumentFilter(this);
        lessonPanel.getAns().getStyledDocument().addDocumentListener(this);
        ExerciseModel.loadDict();
    }
        
    private void initTrackBox(){
        int numOfTracks = exerciseModel.getCurrentExercise().getListTrack().size();
        String[] trackList = new String[numOfTracks];
        for (int i = 0; i < numOfTracks; ++i)
            trackList[i] = "Track " + Integer.toString(i + 1);
        for (String word : trackList)
            System.out.println(word);
        lessonPanel.setTrackBox(new JComboBox(trackList));
        
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        lessonPanel.getTrackBox().setRenderer(renderer);
        lessonPanel.getBoxContainer().add(Box.createHorizontalStrut(250));
        lessonPanel.getBoxContainer().add(lessonPanel.getTrackBox());
        lessonPanel.getBoxContainer().add(Box.createHorizontalStrut(350));
        lessonPanel.getTrackBox().setVisible(false);

    }
    public void fillProgressBar(){
        int currentProgress = exerciseModel.getCurrentProgress();
        exerciseModel.setCurrentProgress(currentProgress + exerciseModel.getPercentPerSec()); 
        lessonPanel.getProgressBar().setValue(exerciseModel.getCurrentProgress());
    }
    private void addTrackDataset(int currentAttempt, int[] points){
        XYSeriesCollection dataset = lessonPanel.createDataset(currentAttempt, points);
        exerciseModel.getTrackDatasets().add(dataset);
    }
    private void generateChart(int currentTrack, int currentAttempt, int[] points){
        //XYSeriesCollection dataset = lessonPanel.createDataset(currentAttempt, points);
        exerciseModel.getTrackDatasets().get(currentTrack).removeSeries(0);
        XYSeries series = new XYSeries("Current Attempt");
        for (int i = 0; i < currentAttempt; ++i){
            series.add(i + 1, points[i]);
        }
        exerciseModel.getTrackDatasets().get(currentTrack).addSeries(series);
        lessonPanel.getChart().getXYPlot().setDataset(exerciseModel.getTrackDatasets().get(currentTrack));
    }
    private void showTrackChart(int currentTrack){
        lessonPanel.getChart().getXYPlot().setDataset(exerciseModel.getTrackDatasets().get(currentTrack));
        lessonPanel.getChart().setTitle("Track " + Integer.toString(currentTrack + 1));
    }
    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException{
        int currentWordPos = exerciseModel.getCurrentWordPos();
        int currentCharPos = exerciseModel.getCurrentCharPos();
        String[] words = exerciseModel.getWords();
        String[] standardizeWords = exerciseModel.getStandardizedWords();
        // check if current char typed in match current char in transcript
        if ((fb.getDocument().getText(0, fb.getDocument().getLength()) + str).equalsIgnoreCase(standardizeWords[currentWordPos].substring(0, currentCharPos + 1))){
            super.insertString(fb, offs, str, a);
            exerciseModel.setCurrentCharPos(++currentCharPos);
            if (exerciseModel.getCurrentExercise().getLevel() == 1){
                if (!exerciseModel.getDict().contains(standardizeWords[currentWordPos].toLowerCase())){
                    SimpleAttributeSet keyWord = new SimpleAttributeSet();
                    StyleConstants.setForeground(keyWord, Color.gray.brighter());
                    if (!exerciseModel.getIsInserted()[currentWordPos]){
                        exerciseModel.getIsInserted()[currentWordPos] = true;
                        lessonPanel.getAns().getStyledDocument().insertString(lessonPanel.getAns().getDocument().getLength(), words[currentWordPos] + " ", keyWord);
                    }
                }
            }
            //hit the end of a word
            if (currentCharPos == standardizeWords[currentWordPos].length()){    
                // check if that is the last word in the transcript
                if (currentWordPos != standardizeWords.length){            
                    remove(fb, 0, fb.getDocument().getLength());
                    lessonPanel.getAns().getStyledDocument().setCharacterAttributes(0, lessonPanel.getAns().getDocument().getLength(), new SimpleAttributeSet(), true);
                    if (exerciseModel.getCurrentExercise().getLevel() == 1){
                        if (exerciseModel.getDict().contains(standardizeWords[currentWordPos].toLowerCase()))
                            lessonPanel.getAns().getStyledDocument().insertString(lessonPanel.getAns().getStyledDocument().getLength(), words[currentWordPos] + " ", new SimpleAttributeSet());
                    }
                    else                         
                        lessonPanel.getAns().getStyledDocument().insertString(lessonPanel.getAns().getStyledDocument().getLength(), words[currentWordPos] + " ", new SimpleAttributeSet());
                    exerciseModel.setCurrentWordPos(++currentWordPos);
                    exerciseModel.setCurrentCharPos(0);
                    if (currentWordPos == standardizeWords.length){    
                        exerciseModel.setTotalPoint(exerciseModel.getTotalPoint() + Integer.max(5*(21 - exerciseModel.getCurrentAttempt()), 0));
                        exerciseModel.stopAudio();
                        lessonPanel.getText().setEditable(false);
                        lessonPanel.getText().requestFocus(false);
                        // check if last track
                        if (exerciseModel.getCurrentTrack() != exerciseModel.getCurrentExercise().getListTrack().size() - 1)
                            lessonPanel.getNextPanel().setVisible(true);
                        else{
                            String message = "Score: " + Integer.toString(exerciseModel.getTotalPoint()/(exerciseModel.getCurrentTrack() + 1)) + "/100" + "\n"
                                            + "Title: " + exerciseModel.getCurrentExercise().getTitle() + "\n"
                                            + "Level: " + exerciseModel.getCurrentExercise().getLevel();
                            JOptionPane.showMessageDialog(lessonPanel, message, "Result", 1, null);
                        }

                    }
                                
                }                     
            }
        }  
            else 
                Toolkit.getDefaultToolkit().beep();
    }
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException{
        int currentWordPos = exerciseModel.getCurrentWordPos();
        int currentCharPos = exerciseModel.getCurrentCharPos();
        String[] words = exerciseModel.getWords();
        String[] standardizeWords = exerciseModel.getStandardizedWords();   
        if ((fb.getDocument().getText(0, fb.getDocument().getLength()) + str).equalsIgnoreCase(standardizeWords[currentWordPos].substring(0, currentCharPos + 1))){
            super.replace(fb, offs,length, str, a);
            exerciseModel.setCurrentCharPos(++currentCharPos);
            System.out.println("Current char: " + Integer.toString(exerciseModel.getCurrentCharPos()));
            if (exerciseModel.getCurrentExercise().getLevel() == 1){
                if (!exerciseModel.getDict().contains(standardizeWords[currentWordPos].toLowerCase())){
                    SimpleAttributeSet keyWord = new SimpleAttributeSet();
                    StyleConstants.setForeground(keyWord, Color.gray.brighter());
                    if (!exerciseModel.getIsInserted()[currentWordPos]){
                        exerciseModel.getIsInserted()[currentWordPos] = true;
                        lessonPanel.getAns().getStyledDocument().insertString(lessonPanel.getAns().getDocument().getLength(), words[currentWordPos] + " ", keyWord);
                    }
                }
            }
            //hit the end of a word
           
            if (currentCharPos == standardizeWords[currentWordPos].length()){    
                // check if that is the last word in the transcript
                if (currentWordPos != standardizeWords.length){              
                    remove(fb, 0, fb.getDocument().getLength());
                    lessonPanel.getAns().getStyledDocument().setCharacterAttributes(0, lessonPanel.getAns().getDocument().getLength(), new SimpleAttributeSet(), true);
                    if (exerciseModel.getCurrentExercise().getLevel() == 1){
                        if (exerciseModel.getDict().contains(standardizeWords[currentWordPos].toLowerCase()))
                            lessonPanel.getAns().getStyledDocument().insertString(lessonPanel.getAns().getStyledDocument().getLength(), words[currentWordPos] + " ", new SimpleAttributeSet());
                    }
                    else 
                        lessonPanel.getAns().getStyledDocument().insertString(lessonPanel.getAns().getStyledDocument().getLength(), words[currentWordPos] + " ", new SimpleAttributeSet());
                    exerciseModel.setCurrentWordPos(++currentWordPos);
                    exerciseModel.setCurrentCharPos(0);
                    if (currentWordPos == standardizeWords.length){    
                        exerciseModel.setTotalPoint(exerciseModel.getTotalPoint() + Integer.max(5*(21 - exerciseModel.getCurrentAttempt()), 0));
                        exerciseModel.stopAudio();
                        lessonPanel.getText().setEditable(false);
                        lessonPanel.getText().requestFocus(false);
                        // check if last track
                        if (exerciseModel.getCurrentTrack() != exerciseModel.getCurrentExercise().getListTrack().size() - 1)
                            lessonPanel.getNextPanel().setVisible(true);
                        else{
                            String message = "Score: " + Integer.toString(exerciseModel.getTotalPoint()/(exerciseModel.getCurrentTrack() + 1)) + "/100" + "\n"
                                            + "Title: " + exerciseModel.getCurrentExercise().getTitle() + "\n"
                                            + "Level: " + exerciseModel.getCurrentExercise().getLevel();
                            JOptionPane.showMessageDialog(lessonPanel, message, "Result", 1, null);
                            lessonPanel.getTrackBox().setVisible(true);
                        }

                    }
                                
                }                     
            }
        }  
            else 
                Toolkit.getDefaultToolkit().beep();

}
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException{
                exerciseModel.setCurrentCharPos(exerciseModel.getCurrentCharPos() - 1);
                super.remove(fb, offset, length);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exerciseModel.getTimer()){
                int currentSec = exerciseModel.getCurrentSec();
                fillProgressBar();
                if (currentSec > 9)
                    lessonPanel.getCurrentTime().setText("00:" + Integer.toString(currentSec));  
                else
                    lessonPanel.getCurrentTime().setText("00:0" + Integer.toString(currentSec));
                exerciseModel.setCurrentSec(currentSec + 1);
        }
        else if (e.getSource() == lessonPanel.getbPlay()){
            SwingUtilities.invokeLater(new Runnable(){ 
                public void run(){
                    lessonPanel.getText().requestFocus(true);
                    if (!exerciseModel.checkIsPlaying()){
                        if (exerciseModel.getCurrentAttempt() < ExerciseModel.getMaxNumOfAttempts()){
                            lessonPanel.getbPlay().setIcon(new ImageIcon("Image/pause" + ".png", "pause button"));
                            exerciseModel.loadAudio();
                            if (exerciseModel.getCurrentWordPos() != exerciseModel.getWords().length){
                                exerciseModel.setCurrentAttempt(exerciseModel.getCurrentAttempt() + 1);
                                    
                            }
                        }
                        else{
                            lessonPanel.getText().setEditable(false);
                            lessonPanel.getText().requestFocus(false);
                            exerciseModel.setCurrentPoint(0);
                            for (int i = exerciseModel.getCurrentWordPos(); i < exerciseModel.getWords().length; ++i){
                                try {
                                StyledDocument ansStyledDocument = lessonPanel.getAns().getStyledDocument();
                                ansStyledDocument.insertString(ansStyledDocument.getLength(), exerciseModel.getWords()[i] + " ", new SimpleAttributeSet());
                                } catch (BadLocationException ex) {
                                    Logger.getLogger(LessonController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        // check if last track
                            if (exerciseModel.getCurrentTrack() != exerciseModel.getCurrentExercise().getListTrack().size() - 1)
                                lessonPanel.getNextPanel().setVisible(true);
                            else{
                                String message = "Score: " + Integer.toString(exerciseModel.getTotalPoint()/(exerciseModel.getCurrentTrack() + 1)) + "/100" + "\n"
                                            + "Title: " + exerciseModel.getCurrentExercise().getTitle() + "\n"
                                            + "Level: " + exerciseModel.getCurrentExercise().getLevel();                   
                                JOptionPane.showMessageDialog(lessonPanel, message, "Result", 1, null);
                                lessonPanel.getTrackBox().setVisible(true);
                            } 
                                
                        }
                        exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = exerciseModel.getCurrentPoint();
                        generateChart(exerciseModel.getCurrentTrack(), exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
                            
                    }
                    else {
                        lessonPanel.getbPlay().setIcon(new ImageIcon("Image/play1" + ".png", "play button"));
                        exerciseModel.stopAudio();
                        
                    }
                }
            });
        }
        else if (e.getSource() == lessonPanel.getbNext()){
            try {
                exerciseModel.loadFile();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
            exerciseModel.getClip().addLineListener(this);
            exerciseModel.setCurrentAttempt(0);
            exerciseModel.setCurrentWordPos(0);
            exerciseModel.setCurrentCharPos(0); 
            exerciseModel.getPoints()[exerciseModel.getCurrentAttempt()] = 0;
            exerciseModel.setCurrentPoint(0);
            addTrackDataset(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
            generateChart(exerciseModel.getCurrentTrack(), exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
            lessonPanel.getChart().setTitle("Track " + Integer.toString(exerciseModel.getCurrentTrack() + 1));
            lessonPanel.getText().setEditable(true);
            lessonPanel.getAns().setText("");
            lessonPanel.getbPlay().setEnabled(true);
            lessonPanel.getNextPanel().setVisible(false);
            if (exerciseModel.getTime() > 9)
                lessonPanel.getTrackLen().setText("00:" + Integer.toString(exerciseModel.getTime()));
            else
                lessonPanel.getTrackLen().setText("00:0" + Integer.toString(exerciseModel.getTime()));
        }
        else if (e.getSource() == lessonPanel.getbListen()){
            exerciseModel.loadAudio();
            lessonPanel.getbPlay().setEnabled(false);
        }
        else if (e.getSource() == lessonPanel.getbBack()){
            MainFrame.refresh(topicController.getTopicPanel());
        }
        else{
            
            lessonPanel.getTrackBox().setVisible(true);
            JComboBox cb = (JComboBox)e.getSource();
            String trackName = (String)cb.getSelectedItem();
            int currentTrack = Integer.parseInt(trackName.substring(trackName.length() - 1, trackName.length()));
            System.out.println(Integer.toString(currentTrack));
            showTrackChart(currentTrack - 1);
            try {
                
                lessonPanel.getAns().getStyledDocument().remove(0, lessonPanel.getAns().getStyledDocument().getLength());
                lessonPanel.getAns().getStyledDocument().insertString(0, exerciseModel.getCurrentExercise().getListTrack().get(currentTrack - 1).getTranscript(), new SimpleAttributeSet());
            } catch (BadLocationException ex) {
                Logger.getLogger(LessonController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }


    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP){
            
            lessonPanel.getbPlay().setIcon(new ImageIcon("Image/play1" + ".png", "play button"));       
            exerciseModel.setPlaying(false);
            lessonPanel.getProgressBar().setValue(0);
            exerciseModel.setCurrentProgress(0); 
            exerciseModel.setCurrentSec(0);
            lessonPanel.getCurrentTime().setText("00:00");
            exerciseModel.getTimer().stop();
            
        }
        else if (event.getType() == LineEvent.Type.START){
            
            exerciseModel.getTimer().start();
        
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            if (exerciseModel.getCurrentWordPos() != exerciseModel.getWords().length){
                if (exerciseModel.checkIsPlaying()){
                    exerciseModel.stopAudio();
                    //exerciseModel.getTimer().stop();
                    lessonPanel.getbPlay().setIcon(new ImageIcon("Image/play1" + ".png", "play button"));

                }
                else{
                    if (exerciseModel.getCurrentAttempt() < ExerciseModel.getMaxNumOfAttempts()){
                        lessonPanel.getbPlay().setIcon(new ImageIcon("Image/pause" + ".png", "pause button"));
                        exerciseModel.loadAudio();                        
                        exerciseModel.setCurrentAttempt(exerciseModel.getCurrentAttempt() + 1);

                    }
                    else{
                        lessonPanel.getText().setEditable(false);
                        lessonPanel.getText().requestFocus(false);
                        exerciseModel.setCurrentPoint(0);
                        for (int i = exerciseModel.getCurrentWordPos(); i < exerciseModel.getWords().length; ++i){
                            try {
                                StyledDocument ansStyledDocument = lessonPanel.getAns().getStyledDocument();
                                ansStyledDocument.insertString(ansStyledDocument.getLength(), exerciseModel.getWords()[i] + " ", new SimpleAttributeSet());
                            } catch (BadLocationException ex) {
                                Logger.getLogger(LessonController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        // check if last track
                        if (exerciseModel.getCurrentTrack() != exerciseModel.getCurrentExercise().getListTrack().size() - 1)
                            lessonPanel.getNextPanel().setVisible(true);
                        else{
                            String message = "Score: " + Integer.toString(exerciseModel.getTotalPoint()/(exerciseModel.getCurrentTrack() + 1)) + "/100" + "\n"
                                            + "Title: " + exerciseModel.getCurrentExercise().getTitle() + "\n"
                                            + "Level: " + exerciseModel.getCurrentExercise().getLevel();                   
                            JOptionPane.showMessageDialog(lessonPanel, message, "Result", 1, null);
                            lessonPanel.getTrackBox().setVisible(true);
                        }
                    }
                    exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = exerciseModel.getCurrentPoint();
                    generateChart(exerciseModel.getCurrentTrack(), exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());

                }
            }
            else 
                lessonPanel.getbPlay().setEnabled(false);
        }    
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        int trackLen = exerciseModel.getCurrentExercise().getListTrack().size();
        if (exerciseModel.getCurrentTrack() != (trackLen - 1) || exerciseModel.getCurrentWordPos() != exerciseModel.getWords().length){
            if (exerciseModel.getCurrentAttempt() != ExerciseModel.getMaxNumOfAttempts()){
                exerciseModel.setCurrentPoint(exerciseModel.getCurrentPoint() + exerciseModel.getPointPerWord()); 
                exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = exerciseModel.getCurrentPoint();
                generateChart(exerciseModel.getCurrentTrack(), exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
                if (exerciseModel.getCurrentWordPos() == exerciseModel.getStandardizedWords().length - 1){
                    exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = 100;
                    generateChart(exerciseModel.getCurrentTrack(), exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
                    // check for last track
                    if (exerciseModel.getCurrentTrack() == exerciseModel.getCurrentExercise().getListTrack().size() - 1){
                        // code for creating History
                        
                    }
                }
            }
        } 
            
        
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}