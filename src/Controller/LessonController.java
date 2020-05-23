package Controller;

import View.LessonPanel;
import java.awt.EventQueue;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.FileNotFoundException;

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

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import Model.Exercise;
import Model.ExerciseModel;
import Model.Track;
import org.jfree.data.xy.XYDataset;


public class LessonController extends DocumentFilter implements ActionListener, LineListener, KeyListener{

    private ExerciseModel exerciseModel;
    private LessonPanel lessonPanel;
    public LessonController(ExerciseModel exerciseModel, LessonPanel lessonPanel){
        this.exerciseModel = exerciseModel;
        this.lessonPanel = lessonPanel;
        this.lessonPanel.getText().addKeyListener(this);
        this.exerciseModel.setTimer(new Timer(1000, this));
        this.exerciseModel.getClip().addLineListener(this);
        this.lessonPanel.getbPlay().addActionListener(this);
        this.lessonPanel.getbNext().addActionListener(this);
        this.lessonPanel.getbListen().addActionListener(this);
        this.exerciseModel.setTextDocument((AbstractDocument)this.lessonPanel.getText().getDocument());
        this.exerciseModel.getTextDocument().setDocumentFilter(this);
    }
    
    public void loadFile() throws FileNotFoundException, UnsupportedAudioFileException, IOException, LineUnavailableException{
        exerciseModel.loadFile();
    }
    
    
    public void fillProgressBar(){
        int currentProgress = exerciseModel.getCurrentProgress();
        exerciseModel.setCurrentProgress(currentProgress + exerciseModel.getPercentPerSec()); 
        lessonPanel.getProgressBar().setValue(exerciseModel.getCurrentProgress());
    }
    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException{
        //int currentWordPos = exerciseModel.getCurrentWordPos();
        //int currentCharPos = exerciseModel.getCurrentCharPos();
//        System.out.println("Current word pos: " + Integer.toString(currentWordPos));
//        System.out.println("Current char pos: " + Integer.toString(currentCharPos));
        String[] words = exerciseModel.getWords();
        if ((fb.getDocument().getText(0, fb.getDocument().getLength()) + str).equalsIgnoreCase(words[exerciseModel.getCurrentWordPos()].substring(0, exerciseModel.getCurrentCharPos() + 1))){    
            super.insertString(fb, offs, str, a);
            exerciseModel.setCurrentCharPos(exerciseModel.getCurrentCharPos() + 1);
            //System.out.println("Current char pos after insertion: " + Integer.toString(currentCharPos));
            if (exerciseModel.getCurrentCharPos() == words[exerciseModel.getCurrentWordPos()].length()){
                if (exerciseModel.getCurrentWordPos() != words.length){
                    String key = fb.getDocument().getText(0, fb.getDocument().getLength());
                    remove(fb, 0, fb.getDocument().getLength());
                    exerciseModel.setCurrentPoint(exerciseModel.getCurrentPoint() + exerciseModel.getPointPerWord()); 
                    exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = exerciseModel.getCurrentPoint();
                    XYDataset dataset = lessonPanel.createDataset(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
                    lessonPanel.setDataset(dataset); 
                    lessonPanel.getChart().getXYPlot().setDataset(dataset);
                    exerciseModel.setCurrentWordPos(exerciseModel.getCurrentWordPos() + 1);
                    exerciseModel.setCurrentCharPos(0);
                    lessonPanel.getAns().append(key + " ");
                    if (exerciseModel.getCurrentWordPos() == words.length){
                        exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = 100;
                        dataset = lessonPanel.createDataset(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
                        lessonPanel.setDataset(dataset); 
                        lessonPanel.getChart().getXYPlot().setDataset(dataset);
                        if (exerciseModel.getCurrentTrack() != exerciseModel.getCurrentExercise().getListTrack().size() - 1){
                            lessonPanel.getNextPanel().setVisible(true);
                            lessonPanel.getText().setEditable(false);
                            lessonPanel.getText().requestFocus(false);
                        }
                        else{
                            
                        }
                        exerciseModel.stopAudio();
                        //System.out.println(fb.getDocument().getText(0, fb.getDocument().getLength()));
                    }
                                
                }                     
            }
        }  
            else 
                Toolkit.getDefaultToolkit().beep();
    }
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException{
        String[] words = exerciseModel.getWords();
        if ((fb.getDocument().getText(0, fb.getDocument().getLength()) + str).equalsIgnoreCase(words[exerciseModel.getCurrentWordPos()].substring(0, exerciseModel.getCurrentCharPos() + 1))){    
            super.replace(fb, offs, length, str, a);
            exerciseModel.setCurrentCharPos(exerciseModel.getCurrentCharPos() + 1);
            //System.out.println("Current char pos after insertion: " + Integer.toString(currentCharPos));
            if (exerciseModel.getCurrentCharPos() == words[exerciseModel.getCurrentWordPos()].length()){
                if (exerciseModel.getCurrentWordPos() != words.length){
                    String key = fb.getDocument().getText(0, fb.getDocument().getLength());
                    remove(fb, 0, fb.getDocument().getLength());
                    exerciseModel.setCurrentPoint(exerciseModel.getCurrentPoint() + exerciseModel.getPointPerWord()); 
                    exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = exerciseModel.getCurrentPoint();
                    XYDataset dataset = lessonPanel.createDataset(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
                    lessonPanel.setDataset(dataset); 
                    lessonPanel.getChart().getXYPlot().setDataset(dataset);
                    exerciseModel.setCurrentWordPos(exerciseModel.getCurrentWordPos() + 1);
                    exerciseModel.setCurrentCharPos(0);
                    lessonPanel.getAns().append(key + " ");
                    if (exerciseModel.getCurrentWordPos() == words.length){
                        exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = 100;
                        dataset = lessonPanel.createDataset(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
                        lessonPanel.setDataset(dataset); 
                        lessonPanel.getChart().getXYPlot().setDataset(dataset);
                        lessonPanel.getNextPanel().setVisible(true);
                        lessonPanel.getText().setEditable(false);
                        lessonPanel.getText().requestFocus(false);
                        exerciseModel.stopAudio();
                        //System.out.println(fb.getDocument().getText(0, fb.getDocument().getLength()));
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
                exerciseModel.getTimer().setInitialDelay(0);
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
                        lessonPanel.getbPlay().setIcon(new ImageIcon("Image/pause" + ".png", "pause button"));
                        
                        //timer.setInitialDelay(1000);
                        exerciseModel.loadAudio();
                        exerciseModel.getTimer().start();
                        exerciseModel.setCurrentAttempt(exerciseModel.getCurrentAttempt() + 1);
                        exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = exerciseModel.getCurrentPoint();
                        XYDataset dataset = lessonPanel.createDataset(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
                        lessonPanel.getChart().getXYPlot().setDataset(dataset);
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
                loadFile();
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(LessonController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LessonController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(LessonController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            exerciseModel.setCurrentAttempt(0);
            exerciseModel.setCurrentWordPos(0);
            exerciseModel.setCurrentCharPos(0); 
            exerciseModel.getPoints()[exerciseModel.getCurrentAttempt()] = 0;
            exerciseModel.setCurrentPoint(0);
            XYDataset dataset = lessonPanel.createDataset(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
            lessonPanel.getChart().getXYPlot().setDataset(dataset);
            lessonPanel.getChart().setTitle("Track " + Integer.toString(exerciseModel.getCurrentTrack()));
            lessonPanel.getText().setEditable(true);
            lessonPanel.getAns().setText("");
            lessonPanel.getbPlay().setEnabled(true);
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
            lessonPanel.getbPlay().setIcon(new ImageIcon("Image/play1" + ".png", "play button"));       
            exerciseModel.setPlaying(false);
            lessonPanel.getProgressBar().setValue(0);
            exerciseModel.setCurrentProgress(0); 
            exerciseModel.setCurrentSec(0);
            lessonPanel.getCurrentTime().setText("00:00");
            exerciseModel.getTimer().stop();
            //exerciseModel.getTimer().setInitialDelay(0);
            
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            if (exerciseModel.checkIsPlaying()){
                exerciseModel.stopAudio();
                exerciseModel.getTimer().stop();
                lessonPanel.getbPlay().setIcon(new ImageIcon("Image/play1" + ".png", "play button"));
                
            }
            else{
                lessonPanel.getbPlay().setIcon(new ImageIcon("Image/pause" + ".png", "pause button"));
                exerciseModel.getTimer().start();
                exerciseModel.loadAudio();
                exerciseModel.setCurrentAttempt(exerciseModel.getCurrentAttempt() + 1);
                exerciseModel.getPoints()[exerciseModel.getCurrentAttempt() - 1] = exerciseModel.getCurrentPoint();
                XYDataset dataset = lessonPanel.createDataset(exerciseModel.getCurrentAttempt(), exerciseModel.getPoints());
                lessonPanel.getChart().getXYPlot().setDataset(dataset);
               
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    // public static void main(String[] args){
    //     EventQueue.invokeLater(new Runnable(){
    //         public void run(){
    //             LessonPanel lessonPanel = new LessonPanel();
    //             Track track1 = new Track("190121-fortnite_1.wav", "transcript.txt", 8);
    //             Track track2 = new Track("191004-living-by-the-sea_4.wav", "transcript4.txt", 14);
    //             Exercise currentExercise = new Exercise();
    //             currentExercise.getListTrack().add(track1);
    //             currentExercise.getListTrack().add(track2);
    //             ExerciseModel exerciseModel = new ExerciseModel(currentExercise);
    //             LessonController lessonController = new LessonController(exerciseModel, lessonPanel);
    //             lessonPanel.getTrackLen().setText("00:0" + Integer.toString(exerciseModel.getCurrentExercise().getListTrack().get(exerciseModel.getCurrentTrack()).getTime()));
    //             JFrame frame = new JFrame("Demo");
    //             frame.setLocationRelativeTo(null);
    //             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //             frame.setSize(800, 600);
    //             frame.getContentPane().add(lessonPanel);
    //             frame.setVisible(true);
    //         }
    //     });
    // }
    
    
}