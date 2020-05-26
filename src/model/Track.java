/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author DELL 7577
 */
public class Track {
    private String Audio, TranscriptPath, Transcript;
    private int Time;

    public Track() {
    }

    public String getAudio() {
        return Audio;
    }

    public void setAudio(String audio) {
        Audio = audio;
    }

    public String getTranscriptPath() {
        return TranscriptPath;
    }

    public void setTranscriptPath(String TranscriptPath) {
        this.TranscriptPath = TranscriptPath;
    }
    

    public String getTranscript() {
        return Transcript;
    }

    public void setTranscript(String transcript) {
        Transcript = transcript;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public Track(String audio, String transcriptPath, int time) {
        Audio = audio;
        TranscriptPath = transcriptPath;
        Time = time;
    }


    
}
