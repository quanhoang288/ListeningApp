package View;

import java.awt.*;
import javax.swing.*;
public class MainFrame extends JFrame{
    private static MainFrame instance = new MainFrame();
    public MainFrame(){
        setTitle("Learn English");
        setSize(800, 600);
        setResizable(true);
        setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static Container getInstance(){
        return instance.getContentPane();
    }
    public static void refresh(JPanel view){
        instance.getContentPane().removeAll();
        instance.getContentPane().add(view);
        instance.repaint();
        instance.revalidate();
        instance.setVisible(true);
    }
}