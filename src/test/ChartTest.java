/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.jfree.chart.axis.ValueAxis;

/**
 *
 * @author DELL 7577
 */
public class ChartTest extends  JFrame implements ActionListener, DocumentListener, ChangeListener, LineListener, KeyListener{
    JTextField text;
    JTextPane ans;
    JProgressBar progressBar;
    JLabel currentTime;
    JLabel trackLen;
    JPanel nextPanel;
    XYDataset dataset;
    JFreeChart chart;
    ChartPanel chartPanel;
    //int numOfAttempts = 0;
    int maxNumOfAttempts = 20;
    int[] points;
    String transcript;
    String[] words;
    boolean[] isInserted;
    int currentWordPos;
    int currentCharPos;
    JButton bPlay;
    boolean isPlaying;
    AudioInputStream audioInputStream;
    Clip clip;
    String filePath;
    String transcriptPath;
    Long currentFrame;
    //Map<Integer, Integer> pointsPerAttempt;
    int currentAttempt;
    int currentPoint;
    int pointPerWord;
    int time, currentSec;
    int percentPerSec;
    int currentProgress;
    Timer timer;
    int currentTrack;
    private static final String dictPath = "words-1.txt";
    private static Set<String> dict = new HashSet<>();
    public ChartTest() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.setInitialDelay(0);
                fillProgressBar();
                if (currentSec > 9)
                    currentTime.setText("00:" + Integer.toString(currentSec++));
                else 
                    currentTime.setText("00:0" + Integer.toString(currentSec++));
//                if (progressBar.getValue() >= 100){
//                    progressBar.setValue(0);
//                    currentProgress = 0;
//                }
            
            }
        });
        //timer.setInitialDelay(0);
        //timer.setInitialDelay(1000);
        filePath = "190121-fortnite_1.wav";
        transcriptPath = "transcript.txt";
//        BufferedReader bf = new BufferedReader(new FileReader(new File(transcriptPath)));
//        
//        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
//        clip = AudioSystem.getClip();
//        clip.open(audioInputStream);
//        clip.addLineListener(this);
//        transcript = "";
//        String tmp;
//        while ((tmp = bf.readLine()) != null){
//            transcript += tmp;
//        }
//        words = transcript.split(" ");
//        pointPerWord = 100/words.length;
//        time = (int)clip.getMicrosecondLength()/1000000;
//        percentPerSec = 100/time;
        loadFile(filePath, transcriptPath);
        loadDict();
        isInserted = new boolean[words.length];
        String trackTime = "00:0" + Integer.toString(time);
        trackLen = new JLabel(trackTime);
        currentTime = new JLabel("00:00");
        
        this.points = new int[maxNumOfAttempts];
        //JPanel textPanel = new JPanel();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        text = new JTextField();
        text.setPreferredSize(new Dimension(750, 30));
        text.addKeyListener(this);
        //text.requestFocus(true);
        //text.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        //text.addActionListener(this);
        //text.addKeyListener(this);
        //text.getDocument().addDocumentListener(this);
        //text.addKeyListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        //textPanel.add(text);
        //textPanel.add(button);
        //text.set
        dataset = createDataset();
        chart = createChart(dataset, currentTrack);

        chartPanel = new ChartPanel(chart);
        //chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
        chartPanel.setBackground(Color.white);
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);
        JPanel textPanel = new JPanel();
        //this.getContentPane().add(chartPanel);
        mainPanel.add(chartPanel);
        
        JPanel audioPanel = new JPanel();
        
        //this.getContentPane().add(BorderLayout.SOUTH, text);
        //button.setPreferredSize(new Dimension(10, 5));
        //audioPanel.setPreferredSize(new Dimension(600, 100));
        audioPanel.setPreferredSize(new Dimension(600, 300));
        audioPanel.setLayout(new BoxLayout(audioPanel, BoxLayout.Y_AXIS));
        ans = new JTextPane(new DefaultStyledDocument());
        ans.setEditable(false);
        //ans.setLineWrap(true);
        //ans.setWrapStyleWord(true);
        ans.setPreferredSize(new Dimension(600, 30));
        //ans.setRows(2);
        ans.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        StyledDocument ansDoc = (StyledDocument) ans.getDocument();
        ansDoc.addDocumentListener(this);
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        answerPanel.add(ans);
        //JButton bNext = new JButton("Next");
        //bNext.setVisible(false);
        //answerPanel.add(bNext, CENTER_ALIGNMENT);
        answerPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));

        
        //audioPanel.add(buttonPanel);
        
        audioPanel.add(answerPanel);
        //audioPanel.add(ans);
        //audioPanel.setBackground(Color.red.brighter());
        //audioPanel.add(new JLabel("sadfsa"));
        
        JPanel musicPanel = new JPanel();
        bPlay = new JButton();
        ImageIcon playIcon = new ImageIcon("Image/play1"
                + ".png", "play button");
        //bPlay.setIcon(defaultIcon);
        bPlay.setIcon(playIcon);
        bPlay.setBackground(Color.white);
        bPlay.setPreferredSize(new Dimension(28, 30));
        bPlay.addActionListener(this);
        
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        //progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(650, 5));
        progressBar.addChangeListener(this);
        musicPanel.add(bPlay, LEFT_ALIGNMENT);
        musicPanel.add(currentTime);
        musicPanel.add(progressBar);
        musicPanel.add(trackLen);
        mainPanel.add(musicPanel);
        
        nextPanel = new JPanel();
        JButton bNext = new JButton("Next");
        JButton bListen = new JButton("Listen");
        bNext.addActionListener(this);
        bListen.addActionListener(this);
        nextPanel.add(bNext);
        nextPanel.add(bListen);
        answerPanel.add(nextPanel);
        nextPanel.setVisible(false);
        mainPanel.add(audioPanel);
        
        textPanel.add(text);
        mainPanel.add(textPanel);
        this.getContentPane().add(mainPanel);
        this.setSize(800,600);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        AbstractDocument document = (AbstractDocument) text.getDocument();
        //document.addDocumentListener(this);
        document.setDocumentFilter(new DocumentFilter(){
            //Random r = new Random();
            @Override
            public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException{
                   
                    
                    if ((fb.getDocument().getText(0, fb.getDocument().getLength()) + str).equalsIgnoreCase(words[currentWordPos].substring(0, currentCharPos + 1))){    
                        super.insertString(fb, offs, str, a);
                        currentCharPos++;
                        
                        if (Character.isUpperCase(words[currentWordPos].charAt(0))){
                            SimpleAttributeSet keyWord = new SimpleAttributeSet();
                            StyleConstants.setForeground(keyWord, Color.gray.brighter());
                            if (!isInserted[currentWordPos]){
                                isInserted[currentWordPos] = true;
                                ans.getStyledDocument().insertString(ansDoc.getLength(), words[currentWordPos] + " ", keyWord);
                            }
                        }
                        if (currentCharPos == words[currentWordPos].length()){
                            if (currentWordPos != words.length){
                                
                                String key = fb.getDocument().getText(0, fb.getDocument().getLength());
                                remove(fb, 0, fb.getDocument().getLength());
                                currentPoint += pointPerWord;
                                points[currentAttempt - 1] = currentPoint;
                                dataset = createDataset();
                                chart.getXYPlot().setDataset(dataset);
                                
                                ansDoc.setCharacterAttributes(0, ansDoc.getLength(), new SimpleAttributeSet(), true);
                                if (!Character.isUpperCase(words[currentWordPos].charAt(0)))
                                    ansDoc.insertString(ansDoc.getLength(), words[currentWordPos] + " ", new SimpleAttributeSet());
                                currentWordPos++;
                                currentCharPos = 0;
                                
                                
                            if (currentWordPos == words.length){
                                points[currentAttempt - 1] = 100;
                                dataset = createDataset();
                                chart.getXYPlot().setDataset(dataset);
                                nextPanel.setVisible(true);
                                text.setEditable(false);
                                text.requestFocus(false);
                                stopAudio();
                               
                                //System.out.println(fb.getDocument().getText(0, fb.getDocument().getLength()));
                            }
                                
                            }
                            
                                
                                
                        }
                         
                    }
                    else 
                        //remove(fb, fb.getDocument().getLength() - 1, 1);
                        Toolkit.getDefaultToolkit().beep();
                
                    
                }
            public void replace(FilterBypass fb, int offs, int length,String str, AttributeSet a) throws BadLocationException{
               
               
                if ((fb.getDocument().getText(0, fb.getDocument().getLength()) + str).equalsIgnoreCase(words[currentWordPos].substring(0, currentCharPos + 1))){
                    super.replace(fb, offs, length, str, a);
                    currentCharPos++;    
                    if (Character.isUpperCase(words[currentWordPos].charAt(0))){
                            SimpleAttributeSet keyWord = new SimpleAttributeSet();
                            StyleConstants.setForeground(keyWord, Color.gray.brighter());
                            if (!isInserted[currentWordPos]){
                                isInserted[currentWordPos] = true;
                                ans.getStyledDocument().insertString(ansDoc.getLength(), words[currentWordPos] + " ", keyWord);
                            }
                    }
                    if (currentCharPos == words[currentWordPos].length()){
                        String key = fb.getDocument().getText(0, fb.getDocument().getLength());
                        remove(fb, 0, fb.getDocument().getLength());
                        currentPoint += pointPerWord;
                        points[currentAttempt - 1] = currentPoint;
                        dataset = createDataset();
                        chart.getXYPlot().setDataset(dataset);
                        ansDoc.setCharacterAttributes(0, ansDoc.getLength(), new SimpleAttributeSet(), true);
                        if (!Character.isUpperCase(words[currentWordPos].charAt(0)))
                            ansDoc.insertString(ansDoc.getLength(), words[currentWordPos] + " ", new SimpleAttributeSet());
                        currentWordPos++;
                        currentCharPos = 0;
                        
                        
                        if (currentWordPos == words.length){
                            points[currentAttempt - 1] = 100;
                            dataset = createDataset();
                            chart.getXYPlot().setDataset(dataset);
                            nextPanel.setVisible(true);
                            text.setEditable(false);
                            text.requestFocus(false);
                            stopAudio();
                            
                           
                        }
                        
                            
                        
                    }
                }
                else 
                    Toolkit.getDefaultToolkit().beep();
                
            }
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException{
                currentCharPos--;
                super.remove(fb, offset, length);
            }
        
           
        });
        
    }

    public JTextField getText() {
        return text;
    }




    public JButton getbPlay() {
        return bPlay;
    }
    

    public static void loadDict() throws FileNotFoundException, IOException{
        BufferedReader bf = new BufferedReader(new FileReader(new File(dictPath)));
        String key;
        while ((key = bf.readLine()) != null){
            dict.add(key);
        }
    }
    
    public void actionPerformed(ActionEvent e){
       
       
        if (e.getSource() == getbPlay()){
            SwingUtilities.invokeLater(new Runnable(){ 
                public void run(){
                    text.requestFocus(true);
                    if (!isPlaying){
                        getbPlay().setIcon(new ImageIcon("Image/pause" + ".png", "pause button"));
                  
                        
                        loadAudio();
                     
                        timer.start();
                        
                        
                        
                        if (currentWordPos != words.length){
                            currentAttempt++;
                            points[currentAttempt - 1] = currentPoint;
                            dataset = createDataset();
                            chart.getXYPlot().setDataset(dataset);
                        }
                    }
                    else {
                        getbPlay().setIcon(new ImageIcon("Image/play1" + ".png", "play button"));
                        stopAudio();
                        
                    }
                }
            });
        }
        else if (e.getActionCommand().equals("Next")){
            try {
                loadFile("191004-living-by-the-sea_4.wav", "transcript4.txt");
                currentAttempt = 0;
                currentWordPos =  currentCharPos = 0;
                points[currentAttempt] = 0;
                currentPoint = 0;
                dataset = createDataset();
                chart.getXYPlot().setDataset(dataset);
                chart.setTitle("Track " + Integer.toString(currentTrack));
                text.setEditable(true);
                ans.setText("");
                bPlay.setEnabled(true);
                if (time > 9)
                    trackLen.setText("00:" + Integer.toString(time));
                else
                    trackLen.setText("00:0" + Integer.toString(time));
                
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(ChartTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ChartTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(ChartTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (e.getActionCommand().equals("Listen")){
            loadAudio(); 
            System.out.println("listening");
            //bPlay.setEnabled(false);
            timer.start();
        }
    }
    private void loadFile(String audioPath, String transcriptPath) throws FileNotFoundException, UnsupportedAudioFileException, IOException, LineUnavailableException{
        filePath = audioPath;
        this.transcriptPath = transcriptPath;
        currentTrack++;
        BufferedReader bf = new BufferedReader(new FileReader(new File(transcriptPath)));
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.addLineListener(this);
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
    private void fillProgressBar(){
        currentProgress += percentPerSec;
        progressBar.setValue(currentProgress);
    }
    private void loadAudio(){
        
        clip.setMicrosecondPosition(0);
        clip.start();
        isPlaying = true;
        //clip.loop(Clip.LOOP_CONTINUOUSLY);
        
    }
    private void stopAudio(){
        clip.stop();
        isPlaying = false;
    }
    private XYDataset createDataset() {
        
        XYSeries series = new XYSeries("Current Attempt");
        for (int i = 0; i < currentAttempt; ++i){
            series.add(i + 1, points[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }
    private JFreeChart createChart(XYDataset dataset, int currentTrack) {
        String trackNum = Integer.toString(currentTrack);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Track " + trackNum,
                "Attempts",
                "Points",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        ValueAxis domainAxis =  plot.getDomainAxis();
        domainAxis.setRange(0, 20);
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setRange(0, 100);
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        //plot.setRangeGridlinesVisible(true);
       // plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        chart.getLegend().visible = false;
        //chart.getLegend().setFrame(BlockBorder.NONE);
        
        //chart.setTitle(new TextTitle("Average Salary per Age",
        //                new Font("Serif", java.awt.Font.BOLD, 18)
        //        )
        //);

        return chart;
    }
    
    
    
    
    
    
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        EventQueue.invokeLater(new Runnable(){
           public void run(){
               try {
                   new ChartTest();
               } catch (UnsupportedAudioFileException ex) {
                   Logger.getLogger(ChartTest.class.getName()).log(Level.SEVERE, null, ex);
               } catch (IOException ex) {
                   Logger.getLogger(ChartTest.class.getName()).log(Level.SEVERE, null, ex);
               } catch (LineUnavailableException ex) {
                   Logger.getLogger(ChartTest.class.getName()).log(Level.SEVERE, null, ex);
               }
           } 
        });
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        if (e.getDocument().getLength() == transcript.length())
//            nextPanel.setVisible(true);
   
    }

    @Override
    public void update(LineEvent event) {
        //if (event.getType() == LineEvent.Type.STOP && event.getFramePosition() == clip.getFrameLength()){
        if (event.getType() == LineEvent.Type.STOP){
            getbPlay().setIcon(new ImageIcon("Image/play1" + ".png", "play button"));       
            isPlaying = false;
            progressBar.setValue(0);
            currentProgress = 0;
            currentSec = 0;
            
            currentTime.setText("00:00");
            timer.stop();
            //timer.setInitialDelay(0);
            
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            if (isPlaying){
                stopAudio();
                timer.stop();
                getbPlay().setIcon(new ImageIcon("Image/play1" + ".png", "play button"));
                
            }
            else{
                getbPlay().setIcon(new ImageIcon("Image/pause" + ".png", "pause button"));
                timer.start();
                loadAudio();
                if (currentWordPos != words.length){
                    currentAttempt++;
                    points[currentAttempt - 1] = currentPoint;
                    dataset = createDataset();
                    chart.getXYPlot().setDataset(dataset);
                }
             
            }
        }
            
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
