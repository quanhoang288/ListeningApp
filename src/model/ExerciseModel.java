/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
//import DAO.ExerciseDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;
/**
 *
 * @author DELL 7577
 */
public class ExerciseModel {
    private Exercise currentExercise;
    private int currentTrackScore;
    private int currentAttempt;
    private int currentTrack;
    private int maxNumOfAttempts = 20;
    private int[] points;
    private int currentPoint;
    private int pointPerWord;
    private String transcript;
    private String[] words;
    private int currentWordPos;
    private int currentCharPos;
    private boolean isPlaying;
    private AudioInputStream audioInputStream;
    private Clip clip; 
    private Long currentFrame;
    AbstractDocument textDocument;

    private int time, currentSec;
    private int percentPerSec;
    private int currentProgress;
    private Timer timer;
    
    public ExerciseModel(Exercise ex){
        currentTrack = -1;
        this.currentExercise = ex;
        this.points = new int[maxNumOfAttempts];
        try {
            loadFile();
        } catch (UnsupportedAudioFileException ex1) {
            Logger.getLogger(ExerciseModel.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (IOException ex1) {
            Logger.getLogger(ExerciseModel.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (LineUnavailableException ex1) {
            Logger.getLogger(ExerciseModel.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
    public void loadFile() throws FileNotFoundException, UnsupportedAudioFileException, IOException, LineUnavailableException{
        currentTrack++;
        String audioPath = this.currentExercise.getListTrack().get(currentTrack).getAudio();
        String transcriptPath = this.currentExercise.getListTrack().get(currentTrack).getTranscript();
        BufferedReader bf = new BufferedReader(new FileReader(new File(transcriptPath)));
        audioInputStream = AudioSystem.getAudioInputStream(new File(audioPath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        transcript = "";
        String tmp;
        while ((tmp = bf.readLine()) != null){
            transcript += tmp;
        }
        words = transcript.split(" ");
        pointPerWord = 100/words.length;
        for (String word: words)
            System.out.print(word + " ");
        
        time = (int)clip.getMicrosecondLength()/1000000;
        percentPerSec = 100/time;
        
    }
    public void loadAudio(){
        
        clip.setMicrosecondPosition(0);
        clip.start();
        isPlaying = true;
        
    }

    public AbstractDocument getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(AbstractDocument textDocument) {
        this.textDocument = textDocument;
    }
    
    public void stopAudio(){
        clip.stop();
        isPlaying = false;
    }
    
    
    public Exercise getCurrentExercise(){
        return currentExercise;
    }

    public int getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(int currentTrack) {
        this.currentTrack = currentTrack;
    }

    public int getCurrentTrackScore() {
        return currentTrackScore;
    }

    public void setCurrentTrackScore(int currentTrackScore) {
        this.currentTrackScore = currentTrackScore;
    }

    public int getCurrentAttempt() {
        return currentAttempt;
    }

    public void setCurrentAttempt(int currentAttempt) {
        this.currentAttempt = currentAttempt;
    }

    public int getMaxNumOfAttempts() {
        return maxNumOfAttempts;
    }

    public void setMaxNumOfAttempts(int maxNumOfAttempts) {
        this.maxNumOfAttempts = maxNumOfAttempts;
    }

    public int[] getPoints() {
        return points;
    }

    public void setPoints(int[] points) {
        this.points = points;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }


    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

    public int getCurrentWordPos() {
        return currentWordPos;
    }

    public void setCurrentWordPos(int currentWordPos) {
        this.currentWordPos = currentWordPos;
    }

    public int getCurrentCharPos() {
        return currentCharPos;
    }

    public void setCurrentCharPos(int currentCharPos) {
        this.currentCharPos = currentCharPos;
    }

    public boolean checkIsPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }



    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }

    public void setAudioInputStream(AudioInputStream audioInputStream) {
        this.audioInputStream = audioInputStream;
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public Long getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(Long currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(int currentPoint) {
        this.currentPoint = currentPoint;
    }

    public int getPointPerWord() {
        return pointPerWord;
    }

    public void setPointPerWord(int pointPerWord) {
        this.pointPerWord = pointPerWord;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCurrentSec() {
        return currentSec;
    }

    public void setCurrentSec(int currentSec) {
        this.currentSec = currentSec;
    }

    public int getPercentPerSec() {
        return percentPerSec;
    }

    public void setPercentPerSec(int percentPerSec) {
        this.percentPerSec = percentPerSec;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
    
//    public Exercise getExcerciseByTitle(String title){
//        return ExerciseDAO.getExerciseByTitle(title, getCurrentExercise().getLevel());
//    }
//    
    
    
    
}
