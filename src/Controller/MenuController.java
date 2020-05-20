package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.HistoryModel;
import View.*;

public class MenuController implements ActionListener {
    private MenuController() {
    }

    private MenuPanel mp;
    private static MenuPanel view = new MenuPanel();
    private static MenuController instance = new MenuController(view);

    private MenuController(MenuPanel v) {
        this.mp = v;
        v.getStartButton().addActionListener(this);
        v.getHistoryButton().addActionListener(this);
        v.getHelpButton().addActionListener(this);
        v.getExitButton().addActionListener(this);
    }
    public static MenuController getInstance(){
        return instance;
    }
    public MenuPanel getView() {
        return this.mp;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        int action = Integer.parseInt(e.getActionCommand());
        switch (action){
            case 0:
                break;
            case 1:
                HistoryController hc = new HistoryController(new HistoryModel(),new HistoryPanel());
                MainFrame.refresh(hc.getHistoryPanel());
                break;
            case 2:
                MainFrame.refresh(new HelpPanel());
                break;
            case 3:
                System.exit(0);
                break;
        }
    }
}