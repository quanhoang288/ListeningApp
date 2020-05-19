package controller;

import model.HistoryModel;
import view.HistoryView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DAO.HistoryDAO;

public class HistoryController implements ActionListener{

    private HistoryModel model;
    private HistoryView view;
   
    public HistoryController( HistoryModel hm,HistoryView hv) {
       
        this.model = hm;
        this.view = hv;
        view.getBack().addActionListener(this);
    }

    public HistoryModel getModel() {
        return model;
    }

    public HistoryView getView() {
        return view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(view.getBack())){
            MainWindow.refresh(session.getView());
        }
    }
    getAllHistory();
}