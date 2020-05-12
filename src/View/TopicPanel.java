package View;
import javax.swing.*;
import java.awt.*;

public class TopicPanel extends View{
    private javax.swing.JButton backButton;
    private JPanel lessonPanel;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JPanel topicPanel;
    public JButton[] lessonListButton = new JButton[10];
    public JLabel[] topicList = new JLabel[10];

    public TopicPanel(){
        backButton = new JButton();
        scrollPane = new JScrollPane();
        panel = new JPanel();
        lessonPanel = new JPanel();
        topicPanel = new JPanel();

        backButton.setFont(new Font("Arial", 0, 14));
        backButton.setText("Back");

        lessonPanel.setAutoscrolls(true);
        lessonPanel.setMaximumSize(new Dimension(100, 100));
        lessonPanel.setPreferredSize(new Dimension(100, 100));
        lessonPanel.setLayout(new BoxLayout(lessonPanel, BoxLayout.PAGE_AXIS));
        for(int i=0; i<10; i++){
            lessonListButton[i].setFont(new Font("Arial", 0, 14));
            lessonListButton[i].setText("Lesson "+i);
            lessonListButton[i].setMaximumSize(new Dimension(100, 100));
            lessonListButton[i].setMinimumSize(new Dimension(100, 100));
            lessonListButton[i].setPreferredSize(new Dimension(100, 100));
            lessonPanel.add(lessonListButton[i]);
        }

        topicPanel.setPreferredSize(new Dimension(480, 1000));
        topicPanel.setLayout(new BoxLayout(topicPanel, javax.swing.BoxLayout.PAGE_AXIS));
        for(int i=0; i<10; i++){
            topicList[i].setFont(new Font("Arial", 0, 14));
            topicList[i].setText("jLabel1");
            topicList[i].setMaximumSize(new Dimension(480, 100));
            topicList[i].setMinimumSize(new Dimension(480, 100));
            topicList[i].setPreferredSize(new Dimension(480, 100));
            topicPanel.add(topicList[i]);
        }

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addComponent(lessonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(topicPanel, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(lessonPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(topicPanel, GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        scrollPane.setViewportView(panel);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

    }

    public javax.swing.JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(javax.swing.JButton backButton) {
        this.backButton = backButton;
    }

    
}