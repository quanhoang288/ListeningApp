/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventDispatcher;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
//import static test.SimpleAudioPlayer.filePath;

/**
 *
 * @author DELL 7577
 */
public class Mp3Player extends JFrame implements ActionListener{
    Long currentFrame;
    Clip clip;
    AudioInputStream audioInputStream;
    static  String filePath;
    //private JTextFile text;
    String status;
    private JButton bPlay, bStop, bResume;
    
    public Mp3Player() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException{
        bPlay = new JButton("Play");
        bStop = new JButton("Stop");
        bPlay.addActionListener(this);
        bStop.addActionListener(this);
        bResume = new JButton("Resume");
        bResume.addActionListener(this);
        JPanel panel = new JPanel();
        panel.add(bPlay);
        panel.add(bStop);
        panel.add(bResume);
        this.getContentPane().add(BorderLayout.SOUTH, panel);
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //clip.start();
        
        //Thread.sleep(clip.getMicrosecondLength() - 5000);
        //Thread.sleep(clip.getMicrosecondLength());
        //Thread.sleep();
        
        //clip.stop();
        //Thread.sleep(clip.getMicrosecondLength() - 5000);
        //clip.start();
        
    }

    public JButton getbPlay() {
        return bPlay;
    }

    public void setbPlay(JButton bPlay) {
        this.bPlay = bPlay;
    }

    public JButton getbStop() {
        return bStop;
    }

    public void setbStop(JButton bStop) {
        this.bStop = bStop;
    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == this.getbPlay()){
            if (status != "play"){
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
                    clip = AudioSystem.getClip(); 
                    clip.open(audioInputStream);
                    clip.start();
                    status = "play";
                    //clip.loop(Clip.LOOP_CONTINUOUSLY);
                    //sThread.sleep(clip.getMicrosecondLength());
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else 
                System.out.println("already playing");
        }
        
        else if(e.getSource() == this.getbStop()){
            if (clip != null && clip.isOpen()){
                clip.stop();
                currentFrame = clip.getMicrosecondPosition();
                status = "stop";
            }
        }
        else{
            try {
                audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.setMicrosecondPosition(currentFrame);
                clip.start();
                status = "play";
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
        
    }
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException{
        filePath = "190121-fortnite_1.wav"; 
        //Mp3Player.filePath = filePath;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Mp3Player();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
}
