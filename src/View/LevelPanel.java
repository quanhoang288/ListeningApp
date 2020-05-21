package View;
import Controller.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LevelPanel extends View implements ActionListener {
    private JLabel chooseLevel;
    private JButton backButton;
    private JButton level_1Button;
    private JButton level_2Button;
    private JButton level_3Button;

    public LevelPanel() {

        backButton = new JButton();
        level_1Button = new JButton();
        level_2Button = new JButton();
        level_3Button = new JButton();
        chooseLevel = new JLabel();
        level_1Button.setActionCommand("1");
        level_2Button.setActionCommand("2");
        level_3Button.setActionCommand("3");
        backButton.setFont(new java.awt.Font("Arial", 0, 14));
        backButton.setText("Back");
        backButton.addActionListener(this);
        level_1Button.setFont(new java.awt.Font("Arial", 0, 14));
        level_1Button.setText("Level 1");

        level_2Button.setFont(new java.awt.Font("Arial", 0, 14));
        level_2Button.setText("Level 2");

        level_3Button.setFont(new java.awt.Font("Arial", 0, 14));
        level_3Button.setText("Level 3");

        chooseLevel.setFont(new java.awt.Font("Arial", 0, 36));
        chooseLevel.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLevel.setText("Choose Level");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup().addGap(250, 250, 250)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(chooseLevel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addComponent(level_1Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(level_2Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(level_3Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup().addGap(80, 80, 80).addComponent(backButton,
                                GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(250, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(10, 10, 10)
                        .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(chooseLevel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(level_1Button, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(level_2Button, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(level_3Button, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)));
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getLevel_1Button() {
        return level_1Button;
    }

    public JButton getLevel_2Button() {
        return level_2Button;
    }

    public JButton getLevel_3Button() {
        return level_3Button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        this.setVisible(false);
        MainFrame.getInstance().add(MenuController.getInstance().getView());
    }

}