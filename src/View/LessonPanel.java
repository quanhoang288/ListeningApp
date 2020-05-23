package  View;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import java.awt.BasicStroke;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class LessonPanel extends JPanel{
    private JTextField text;
    private JTextPane ans;
    private JProgressBar progressBar;
    private JLabel currentTime;
    private JLabel trackLen;
    private JPanel nextPanel;
    private XYDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private JButton bNext;
    private JButton bListen;
    private JButton bPlay;
    public LessonPanel(){
        initComponents();
    }
    public void initComponents(){
        
        
        //JPanel textPanel = new JPanel();
        
        // chart panel
        dataset = createDataset(0, null);
        chart = createChart(dataset, 1);
        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
        chartPanel.setBackground(Color.white);
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);
        
        // panel for user typing
        JPanel textPanel = new JPanel();
        text = new JTextField();
        text.setPreferredSize(new Dimension(750, 30));
        text.setTransferHandler(null);
        //text.addKeyListener(lessonController);
        textPanel.add(text);


        // panel containing answerPanel and nextPanel 
        JPanel audioPanel = new JPanel();
        audioPanel.setPreferredSize(new Dimension(600, 300));
        audioPanel.setLayout(new BoxLayout(audioPanel, BoxLayout.Y_AXIS));
        ans = new JTextPane();
        ans.setEditable(false);        
        ans.setPreferredSize(new Dimension(600, 30));

        ans.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
 
        // Containing next and listen button
        nextPanel = new JPanel();
        bNext = new JButton("Next");
        bListen = new JButton("Listen");
        //bNext.addActionListener(lessonController);
        //bListen.addActionListener(lessonController);
        nextPanel.add(bNext);
        nextPanel.add(bListen);
        nextPanel.setVisible(false);
        // Containing text area to show transcript when user types correctly
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        answerPanel.add(ans);
        answerPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));
        answerPanel.add(nextPanel);
        audioPanel.add(answerPanel);

        // Panel containing media playing bar and time tracking
        JPanel musicPanel = new JPanel();
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setPreferredSize(new Dimension(650, 5));
        currentTime = new JLabel("00:00");
        trackLen = new JLabel();
        //progressBar.addChangeListener(lessonController);
        
        bPlay = new JButton();
        ImageIcon playIcon = new ImageIcon("Image/play1"
                + ".png", "play button");
        bPlay.setIcon(playIcon);
        bPlay.setBackground(Color.white);
        bPlay.setPreferredSize(new Dimension(28, 30));
        //bPlay.addActionListener(lessonController);
        musicPanel.add(bPlay, LEFT_ALIGNMENT);
        musicPanel.add(currentTime);
        musicPanel.add(progressBar);
        musicPanel.add(trackLen);
        
        
        
        
        // main panel
        //JPanel mainPanel = new JPanel();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(chartPanel);
        this.add(musicPanel);
        this.add(audioPanel);
        this.add(textPanel);
//        this.getContentPane().add(mainPanel);
//        this.setSize(800,600);
//        this.setVisible(true);
//        this.setLocationRelativeTo(null);
    }

    public JTextField getText() {
        return text;
    }

    public void setText(JTextField text) {
        this.text = text;
    }

    public JTextPane getAns() {
        return ans;
    }

    public void setAns(JTextPane ans) {
        this.ans = ans;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public JLabel getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(JLabel currentTime) {
        this.currentTime = currentTime;
    }

    public JLabel getTrackLen() {
        return trackLen;
    }

    public void setTrackLen(JLabel trackLen) {
        this.trackLen = trackLen;
    }

    public XYDataset getDataset() {
        return dataset;
    }

    public void setDataset(XYDataset dataset) {
        this.dataset = dataset;
    }

    public JFreeChart getChart() {
        return chart;
    }

    public void setChart(JFreeChart chart) {
        this.chart = chart;
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    public void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }

    public JButton getbNext() {
        return bNext;
    }

    public void setbNext(JButton bNext) {
        this.bNext = bNext;
    }

    public JButton getbListen() {
        return bListen;
    }

    public void setbListen(JButton bListen) {
        this.bListen = bListen;
    }

    public JButton getbPlay() {
        return bPlay;
    }

    public void setbPlay(JButton bPlay) {
        this.bPlay = bPlay;
    }

    public JPanel getNextPanel() {
        return nextPanel;
    }

    public void setNextPanel(JPanel nextPanel) {
        this.nextPanel = nextPanel;
    }
    public void setMediaBar(int time){
        String trackTime;
        if (time > 9)
           trackTime = "00:" + Integer.toString(time);
        else 
           trackTime = "00:0" + Integer.toString(time);
        trackLen = new JLabel(trackTime);
        currentTime = new JLabel("00:00");
        
    }
    

    public XYDataset createDataset(int currentAttempt, int[] points) {
        
        XYSeries series = new XYSeries("Current Attempt");
        for (int i = 0; i < currentAttempt; ++i){
            series.add(i + 1, points[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }
    public JFreeChart createChart(XYDataset dataset, int currentTrack) {
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
    
    
    
}
