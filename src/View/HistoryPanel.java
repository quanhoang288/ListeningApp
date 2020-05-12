package View;

import javax.swing.*;
import java.util.Date;

public class HistoryPanel extends View{
    private JButton backButton;
    private JPanel historyPage1;
    private JPanel historyPage2;
    private JTabbedPane historyTabbedPane;
    private JTable historyTable1;
    private JTable historyTable2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;

    public HistoryPanel() {

        historyTabbedPane = new JTabbedPane();
        historyPage1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        historyTable1 = new JTable();
        historyPage2 = new JPanel();
        jScrollPane2 = new JScrollPane();
        historyTable2 = new JTable();
        backButton = new JButton();

        historyTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

        jScrollPane1.setFont(new java.awt.Font("Arial", 0, 10)); 

        historyTable1.setFont(new java.awt.Font("Arial", 0, 10)); 
        historyTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Level", "Topic", "Score", "Date"
            }
        ) {
            Class[] types = new Class [] {
                Integer.class, Integer.class, String.class, Integer.class, Date.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(historyTable1);

        GroupLayout historyPage1Layout = new GroupLayout(historyPage1);
        historyPage1.setLayout(historyPage1Layout);
        historyPage1Layout.setHorizontalGroup(
            historyPage1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
        );
        historyPage1Layout.setVerticalGroup(
            historyPage1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );

        historyTabbedPane.addTab("Page 1", historyPage1);

        jScrollPane2.setFont(new java.awt.Font("Arial", 0, 10)); 

        historyTable2.setFont(new java.awt.Font("Arial", 0, 10)); 
        historyTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Level", "Topic", "Score", "Date"
            }
        ) {
            Class[] types = new Class [] {
                Integer.class, Integer.class, String.class, Integer.class, Date.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(historyTable2);

        GroupLayout historyPage2Layout = new GroupLayout(historyPage2);
        historyPage2.setLayout(historyPage2Layout);
        historyPage2Layout.setHorizontalGroup(
            historyPage2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
        );
        historyPage2Layout.setVerticalGroup(
            historyPage2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );

        historyTabbedPane.addTab("Page 2", historyPage2);

        backButton.setFont(new java.awt.Font("Arial", 0, 14)); 
        backButton.setText("Back");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                    .addComponent(historyTabbedPane, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(historyTabbedPane, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }

    
}