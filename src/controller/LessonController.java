package controller;

import View.LessonPanel;
import java.awt.Color;
import java.awt.EventQueue;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.Dictionary;
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
import model.Exercise;
import model.ExerciseModel;
import model.Track;
import org.jfree.data.xy.XYDataset;


public class LessonController extends DocumentFilter implements ActionListener, LineListener, KeyListener, DocumentListener{

    private ExerciseModel exerciseModel;
    private LessonPanel lessonPanel;
    public LessonController(ExerciseModel exerciseModel, LessonPanel lessonPanel) throws IOException{
        this.exerciseModel = exerciseModel;
        this.lessonPanel = lessonPanel;
        lessonPanel.getText().addKeyListener(this);
        exerciseModel.setTimer(new Timer(1000, this));
        exerciseModel.getTimer().setInitialDelay(0);
        exerciseModel.getClip().addLineListener(this);
        lessonPanel.getbPlay().addActionListener(this);
        lessonPanel.getbNext().addActionListener(this);
        lessonPanel.getbListen().addActionListener(this);
        exerciseModel.setTextDocument((AbstractDocument)this.lessonPanel.getText().getDocument());
        exerciseModel.getTextDocument().setDocumentFilter(this);
        lessonPanel.getAns().getStyledDocument().addDocumentListener(this);
        ExerciseModel.loadDict();
    }
        
    
    public void fillProgressBar(){
        int currentProgress = exerciseModel.getCurrentProgress();
        exerciseModel.setCurrentProgress(currentProgress + exerciseModel.getPercentPerSec()); 
        lessonPanel.getProgressBar().setValue(exerciseModel.getCurrentProgress());
    }
    private void generateChart(int currentAttempt, int[] points){
        XYDataset dataset = lessonPanel.createDataset(currentAttempt, points);
        lessonPanel.getChart().getXYPlot().setDataset(dataset);
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
                        if (exerciseModel.getCurrentTrack() != exerciseModel.getCurrentExercise().getTrackList().size() - 1)
                            lessonPanel.getNextPanel().setVisible(true);
                        else{
                            String message = "Score: " + Integer.toString(exerciseModel.getTotalPoint()/(exerciseModel.getCurrentTrack() + 1)) + "/100" + "\n"
                                            + "Title: " + exerciseModel.getCurrentExercise().getTitle() + "\n"
                                            + "Level: " + exerciseModel.getCurrentExercise().getLevel();
                            JOptionPane.showMessageDialog(lessonPanel, message);
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
                        //lessonPanel.getAns().getStyledDocument().insertString(lessonPanel.getAns().getDocument().getLength(), words[exerciseModel.getCurrentWordPos()] + " ", keyWord);
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
                        if (exerciseModel.getCurrentTrack() != exerciseModel.getCurrentExercise().getTrackList().size() - 1)
                            lessonPanel.getNextPanel().setVisible(true);
                        else{
                            String message = "Score: " + Integer.toString(exerciseModel.getTotalPoint()/(exerciseModel.getCurrentTrack() + 1)) + "/100" + "\n"
                                            + "Title: " + exerciseModel.getCurrentExercise().getTitle() + "\n"
                                            + "Level: " + exerciseModel.getCurrentExercise().getLevel();
                            JOptionPane.showMessageDialog(lessonPanel, message);
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
                System.out.println("firing!");
                int currentSec = exerciseModel.getCurrentSec();
                
                fillProgressBar();
                if (exerciseModel.getCurrentSec() > 9)
                    lessonPanel.getCurrentTime().setText("00:" + Integer.toString(exerciseModel.getCurrentSec()));  
                else
                    lessonPanel.getCurrentTime().setText("00:0" + Integer.toString(exerciseModel.getCurrentSec()));
                exerciseModel.setCurrentSec(exerciseModel.getCurrentSec() + 1);
        }
        else if (e.getSource() == lessonPanel.getbPlay()){
            SwingUtilities.invokeLater(new Runnable(){ 
                public void run(){
                    lessonPanel.getText().requestFocus(true);
                    if (!exerciseModel.checkIsPlaying()){
                        if (exerciseModel.getCurrentAttempt() < ExerciseModel.getMaxNumOfAttempts()){
                            lessonPanel.getbPlay().setIcon(new ImageIcon("Image/pause" + ".png", "pause button"));
                            exerciseModel.loadAudio();
                            exerciseModel.getTimer().start();
                            if (exerciseModel.getCurrentWordPos() != exerciseModel.getWords().length){
                                exerciseModel.setCurrentAttempt(exerciseModel.getCurrentAttempt() + 1);
                                    
                            }
                        }
                        else{
                            lessonPanel.getText().setEditable(false);
                            lessonPanel.getText().requestFocus(false);
                            exerciseModel.setCurrentPoint(0);
                        // check if last track
                            if (exerciseModel.getCurrentTrack() != exerciseModel.getCurrentExercise().getTrackList().size() - 1)
                                lessonPanel.getNextPanel().setVisible(true);
                        }
                        exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = exerciseModel.getCurrentPoint();
                        generateChart(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
                            
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
            generateChart(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
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
            exerciseModel.getTimer().start();
        }
    }


    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP){
            System.out.println("line stopped!");
            lessonPanel.getbPlay().setIcon(new ImageIcon("Image/play1" + ".png", "play button"));       
            exerciseModel.setPlaying(false);
            lessonPanel.getProgressBar().setValue(0);
            exerciseModel.setCurrentProgress(0); 
            exerciseModel.setCurrentSec(0);
            lessonPanel.getCurrentTime().setText("00:00");
            exerciseModel.getTimer().stop();
            
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
                        //exerciseModel.getTimer().setInitialDelay(0);
                        exerciseModel.loadAudio();
                        exerciseModel.getTimer().start();
                        
                        exerciseModel.setCurrentAttempt(exerciseModel.getCurrentAttempt() + 1);

                    }
                    else{
                        lessonPanel.getText().setEditable(false);
                        lessonPanel.getText().requestFocus(false);
                        exerciseModel.setCurrentPoint(0);
                            // check if last track
                        if (exerciseModel.getCurrentTrack() != exerciseModel.getCurrentExercise().getTrackList().size() - 1)
                            lessonPanel.getNextPanel().setVisible(true);
                    }
                    exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = exerciseModel.getCurrentPoint();
                    generateChart(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());

                }
            }
            else 
                lessonPanel.getbPlay().setEnabled(false);
        }    
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                LessonPanel lessonPanel = new LessonPanel();
                Track track1 = new Track("190121-fortnite_1.wav", "transcript.txt", 8);
                Track track2 = new Track("191004-living-by-the-sea_4.wav", "transcript4.txt", 14);
                Exercise currentExercise = new Exercise();
                currentExercise.getTrackList().add(track1);
                currentExercise.getTrackList().add(track2);
                currentExercise.setLevel(1);
                currentExercise.setTitle("Some random title!");
                ExerciseModel exerciseModel = new ExerciseModel(currentExercise);
                try {
                    LessonController lessonController = new LessonController(exerciseModel, lessonPanel);
                } catch (IOException ex) {
                    Logger.getLogger(LessonController.class.getName()).log(Level.SEVERE, null, ex);
                }
                lessonPanel.getTrackLen().setText("00:0" + Integer.toString(exerciseModel.getCurrentExercise().getTrackList().get(exerciseModel.getCurrentTrack()).getTime()));
                JFrame frame = new JFrame("Demo");
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.getContentPane().add(lessonPanel);
                frame.setVisible(true);
            }
        });
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        exerciseModel.setCurrentPoint(exerciseModel.getCurrentPoint() + exerciseModel.getPointPerWord()); 
        exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = exerciseModel.getCurrentPoint();
        generateChart(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
        if (exerciseModel.getCurrentWordPos() == exerciseModel.getStandardizedWords().length - 1){
             exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = 100;
                        generateChart(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
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