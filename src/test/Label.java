/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author DELL 7577
 */
public class Label extends JFrame implements MouseListener{
    JLabel label;
    public Label(){
        setTitle("Demo");
        JPanel panel = new JPanel();
        label = new JLabel("Click here!");
        label.addMouseListener(this);
        JButton button = new JButton("nothing");
        button.setPreferredSize(new Dimension(20, 20));
        panel.add(button);
        panel.add(label);
        
        getContentPane().add(panel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        label.setForeground(Color.blue.brighter());
        setCursor(Cursor.HAND_CURSOR);
        e.getS
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        label.setForeground(Color.black);
        setCursor(Cursor.DEFAULT_CURSOR);
    }
    public static void main(String[] args){
        new Label();
    }
}
