package View;

import javax.swing.*;
import javax.swing.border.Border;

import Model.LevelModel;

import java.awt.*;

public class TopicPanel extends View{
    private JButton backButton;
    private JPanel lessonPanel;
    private LevelModel lm ;
    private JPanel[] panel;
    private JLabel[] lessonLabel;
    private JLabel[] information;
    private JLabel[] transcript;
    private JSeparator[] separators = new JSeparator[2];


    public TopicPanel(int level){
        lm = new LevelModel(level);
        int size = lm.getAllExerciseByLevel(level).size();
        backButton = new JButton("Back");
        lessonPanel = new JPanel();
        panel = new JPanel[size];
        lessonLabel = new JLabel[size];
        information = new JLabel[size];
        transcript = new JLabel[size];

        backButton.setFont(new Font("Arial", 0, 14));

        lessonPanel.setMaximumSize(new Dimension(640, 480));
        lessonPanel.setMinimumSize(new Dimension(640, 480));
        lessonPanel.setPreferredSize(new Dimension(640, 480));
        lessonPanel.setLayout(new BoxLayout(lessonPanel, BoxLayout.PAGE_AXIS));
        lessonPanel.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));

        for( int i=0 ; i<size ;i++){
            lessonLabel[i] = new JLabel();
            lessonLabel[i].setFont(new Font("Arial", 0, 20));
            lessonLabel[i].setText(lm.getAllExerciseByLevel(level).get(i).getTitle());

            information[i] =new JLabel();
            information[i].setFont(new Font("Arial", 0, 14));
            information[i].setText("Level: "+lm.getLevel()+  "   Time: "+ lm.getAllExerciseByLevel(level).get(i).getTime() +" seconds    HighScore: " + lm.getAllExerciseByLevel(level).get(i).getHighScore()+  "/100");
            information[i].setMaximumSize(new Dimension(640, 50));
            information[i].setMinimumSize(new Dimension(640, 50));
            information[i].setPreferredSize(new Dimension(640, 50));

            transcript[i] = new JLabel();
            transcript[i].setFont(new Font("Arial", 0, 14));
            transcript[i].setText("Transcript: ");
            transcript[i].setMaximumSize(new Dimension(640, 50));
            transcript[i].setMinimumSize(new Dimension(640, 50));
            transcript[i].setPreferredSize(new Dimension(640, 50));
        }

        for(int i=0 ; i<size ; i++){
            panel[i] = new JPanel();
            panel[i].setMaximumSize(new Dimension(640, 160));
            panel[i].setMinimumSize(new Dimension(640, 160));
            panel[i].setPreferredSize(new Dimension(640, 160));
            panel[i].setLayout(new BoxLayout(panel[i], BoxLayout.PAGE_AXIS));
            panel[i].add(lessonLabel[i]);
            panel[i].add(information[i]);
            panel[i].add(transcript[i]);

        }

        for(int i=0 ; i<size ; i++){
            lessonPanel.add(panel[i]);
            if(i!=2){
                separators[i] = new JSeparator();
                lessonPanel.add(separators[i]);
            }
        }

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lessonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lessonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JLabel[] getLessonLabel() {
        return lessonLabel;
    }

    public void setLessonLabel(JLabel[] lessonLabel) {
        this.lessonLabel = lessonLabel;
    }

}