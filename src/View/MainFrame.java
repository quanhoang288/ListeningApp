package View;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame{
    private static MainFrame mainFrame = new MainFrame();

    private MainFrame(){
        setTitle("Learn English");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static Container getMainFrame(){
        return mainFrame.getContentPane();
    }
    
    public static void refresh(javax.swing.JPanel view){
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(view);
        mainFrame.repaint();
        mainFrame.revalidate();
        mainFrame.setVisible(true);
    }
}