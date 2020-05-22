package View;

import javax.swing.*;

import Controller.MenuController;
import Controller.LevelController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.LevelModel;
public class TopicPanel extends View implements ActionListener {
    private JButton backButton;
    private LevelModel lm;
    private JPanel lessonPanel;
    private JPanel[] panelList = new JPanel[10];
    private JScrollPane scrollPane;
    private JButton[] lessonListButton = new JButton[10];
    private JLabel[] topicList = new JLabel[10];
    private JLabel[] transcriptListLabel = new JLabel[10];
    private JSeparator[] separators = new JSeparator[9];
    public TopicPanel(int level) {
        lm = new LevelModel(level);
        backButton = new JButton();
        scrollPane = new JScrollPane();
        lessonPanel = new JPanel();
        backButton.addActionListener(this);
        for (int i = 0; i < 10; i++) {
            panelList[i] = new JPanel();
            lessonListButton[i] = new JButton();
            topicList[i] = new JLabel();
            transcriptListLabel[i] = new JLabel();
            if (i != 9) {
                separators[i] = new JSeparator();
            }
        }

        backButton.setFont(new Font("Arial", 0, 14));
        backButton.setText("Back");

        lessonPanel.setLayout(new BoxLayout(lessonPanel, BoxLayout.PAGE_AXIS));

        for (int i = 0; i < 10; i++) {
            lessonListButton[i].setFont(new Font("Arial", 0, 14));
            lessonListButton[i].setText("Lesson " + (i + 1));
            lessonListButton[i].setMaximumSize(new Dimension(100, 30));
            lessonListButton[i].setMinimumSize(new Dimension(100, 30));
            lessonListButton[i].setPreferredSize(new Dimension(100, 30));

            topicList[i].setFont(new Font("Arial", 0, 14));
            topicList[i].setText("Level " + lm.getLevel() + "       Time: " + lm.getAllExerciseByLevel(level).get(i).getTime() +"       High Score: ");
            topicList[i].setMaximumSize(new Dimension(400, 30));
            topicList[i].setMinimumSize(new Dimension(400, 30));
            topicList[i].setPreferredSize(new Dimension(400, 30));

            transcriptListLabel[i].setFont(new Font("Arial", 0, 14));
            transcriptListLabel[i].setText("Transcript: ");
            transcriptListLabel[i].setMaximumSize(new Dimension(400, 30));
            transcriptListLabel[i].setMinimumSize(new Dimension(400, 30));
            transcriptListLabel[i].setPreferredSize(new Dimension(400, 30));
        }

        for (int i = 0; i < 10; i++) {
            panelList[i].setMaximumSize(new Dimension(638, 100));
            panelList[i].setMinimumSize(new Dimension(638, 100));
            panelList[i].setLayout(new BoxLayout(panelList[i], BoxLayout.PAGE_AXIS));
            panelList[i].add(lessonListButton[i]);
            panelList[i].add(topicList[i]);
            panelList[i].add(transcriptListLabel[i]);
        }

        for (int i = 0; i < 10; i++) {
            lessonPanel.add(panelList[i]);
            if (i != 9)
                lessonPanel.add(separators[i]);
        }

        scrollPane.setViewportView(lessonPanel);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE)
                                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(80, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(10, 10, 10)
                        .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(60, Short.MAX_VALUE)));

    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        this.setVisible(false);
        LevelController lc = new LevelController(new LevelPanel());
        MainFrame.refresh(lc.getLevelPanel());

    }

    
}