package View;
import Controller.MenuController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class HelpPanel extends View implements ActionListener {
    private JScrollPane helpScrollPane;
    private JTextArea helpTextArea;
    private JButton backButton;

    public HelpPanel() {
        helpScrollPane = new JScrollPane();
        helpTextArea = new JTextArea();
        backButton = new JButton();
        backButton.addActionListener(this);
        helpTextArea.setColumns(20);
        helpTextArea.setFont(new java.awt.Font("Arial", 0, 14));
        helpTextArea.setRows(5);
        helpTextArea.setFocusable(false);
        helpScrollPane.setViewportView(helpTextArea);

        backButton.setText("Back");
        backButton.setFont(new java.awt.Font("Arial", 0, 14));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(helpScrollPane, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE)
                        .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup().addContainerGap()
                                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(helpScrollPane,
                                        GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(60, Short.MAX_VALUE)));
    }

    public JButton getBackButton() {
        return backButton;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        this.setVisible(false);
        MainFrame.getInstance().add(MenuController.getInstance().getView());
    }

}