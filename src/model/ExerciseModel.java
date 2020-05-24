
package Model;
import DAO.ExerciseDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;

public class ExerciseModel {
    private Exercise currentExercise;
    private int currentTrackScore;
    private int currentAttempt;
    private int currentTrack;

    private static final int maxNumOfAttempts = 20;
    private int[] points;
    private int totalPoint;
    private int currentPoint;
    private int pointPerWord;
    private String transcript;
    private String[] words, standardizedWords;
    private boolean[] isInserted;
    private static final String dictPath = "words-1.txt";
    private static Set<String> dict = new HashSet<>();

    private int currentWordPos;
    private int currentCharPos;
    private boolean isPlaying;
    private AudioInputStream audioInputStream;
    private Clip clip; 
    private Long currentFrame;
    AbstractDocument textDocument;
    private Date startTime;


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
        isInserted = new boolean[words.length];
    }
    public static void loadDict() throws FileNotFoundException, IOException{
        BufferedReader bf = new BufferedReader(new FileReader(new File(dictPath)));
        String key;
        while ((key = bf.readLine()) != null){
            dict.add(key);
        }
    }
    public void loadFile() throws FileNotFoundException, UnsupportedAudioFileException, IOException, LineUnavailableException{
        currentTrack++;
        System.out.println("Current track: " + Integer.toString(currentTrack));
        String audioPath = this.currentExercise.getTrackList().get(currentTrack).getAudio();
        String transcriptPath = this.currentExercise.getTrackList().get(currentTrack).getTranscript();


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
        standardizedWords = new String[words.length];
        for (int i = 0; i < words.length; ++i){
            int beginPos = 0;
            int endPos = words[i].length();
            while (!Character.isAlphabetic(words[i].charAt(beginPos))) beginPos++;
            while (!Character.isAlphabetic(words[i].charAt(endPos - 1))) endPos--;
            standardizedWords[i] = words[i].substring(beginPos, endPos);
        }
        pointPerWord = 100/words.length;
        for (String word: words)
            System.out.print(word + " ");
        
        time = (int)clip.getMicrosecondLength()/1000000;
        percentPerSec = 100/time;
        
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public boolean[] getIsInserted() {
        return isInserted;
    }

    public void setIsInserted(boolean[] isInserted) {
        this.isInserted = isInserted;
    }
    
    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }
    
    public void loadAudio(){
        
        clip.setMicrosecondPosition(0);
        clip.start();
        isPlaying = true;
        
    }

    public String[] getStandardizedWords() {
        return standardizedWords;
    }

    public void setStandardizedWords(String[] standardizedWords) {
        this.standardizedWords = standardizedWords;
    }
    

    public static Set<String> getDict() {
        return dict;
    }

    public static void setDict(Set<String> dict) {
        ExerciseModel.dict = dict;
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
    


    public Exercise getCurrentExercise() {
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


    public static int getMaxNumOfAttempts() {
        return maxNumOfAttempts;
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

    public Exercise getExcerciseByTitle(String title, int level) {
        try {
            return ExerciseDAO.getExerciseByTitle(title, level);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void setCurrentExercise(Exercise currentExercise) {
        this.currentExercise = currentExercise;
    }
    
//    public Exercise getExcerciseByTitle(String title){
//        return ExerciseDAO.getExerciseByTitle(title, getCurrentExercise().getLevel());
//    }
//    
    
    
    
}
