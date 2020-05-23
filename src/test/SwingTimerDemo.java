package com.jcg;
 
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
 
/**
 * The Class SwingTimerDemo.
 *
 * @author ashraf
 */
@SuppressWarnings("serial")
public class SwingTimerDemo extends JPanel {
     
    private final static int ONE_SECOND = 1000;
    private final static String NEW_LINE_DLIM = "\n";
 
    private JProgressBar progressBar;
    private Timer timer;
    private JToggleButton jtButton;
    private DemoTask task;
    private JTextArea taskOutput;
 
    public SwingTimerDemo() {
        super(new BorderLayout());
        task = new DemoTask();
 
        //Create the demo's UI.
        jtButton = new JToggleButton("Start"); 
        jtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jtButton.isSelected()) {
                    jtButton.setText("Stop");
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    task.go();
                    timer.start();
                }else {
                    jtButton.setText("Start");
                    setCursor(null); //turn off the wait cursor
                    task.pause();
                    timer.stop();
                }  
            }
        });
 
        progressBar = new JProgressBar(0, task.getLengthOfTask());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
 
        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);
        taskOutput.setCursor(null); 
 
        JPanel panel = new JPanel();
        panel.add(jtButton);
        panel.add(progressBar);
 
        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
 
        //Create a timer.
        timer = new Timer(ONE_SECOND, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                progressBar.setValue(task.getCurrent());
                String s = task.getMessage();
                if (s != null) {
                    taskOutput.append(s + NEW_LINE_DLIM);
                    taskOutput.setCaretPosition(
                            taskOutput.getDocument().getLength());
                }
                 
                if (task.isDone()) {
                    jtButton.setSelected(false);
                    jtButton.setText("Start");
                    timer.stop();
                    setCursor(null); //turn off the wait cursor
                    progressBar.setValue(progressBar.getMinimum());
                }
            }
        });
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
 
        //Create and set up the window.
        JFrame frame = new JFrame("SwingTimerDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        JComponent newContentPane = new SwingTimerDemo();
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}