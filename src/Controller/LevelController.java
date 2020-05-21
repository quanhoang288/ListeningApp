package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.LevelPanel;

public class LevelController implements ActionListener {
    private LevelPanel levelPanel;

    public LevelPanel getLevelPanel() {
        return levelPanel;
    }

    public void setLevelPanel(LevelPanel levelPanel) {
        this.levelPanel = levelPanel;
    }

    public LevelController(LevelPanel lp) {
        lp.getLevel_1Button().addActionListener(this);
        lp.getLevel_2Button().addActionListener(this);
        lp.getLevel_3Button().addActionListener(this);
        this.levelPanel = lp;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        int action = Integer.parseInt(e.getActionCommand());
        switch (action){
            case 1:
                
                break;
            case 2:  
                break;
            case 3:
                break;
        }   
    }
}