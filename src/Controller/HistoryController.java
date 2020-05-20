package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.HistoryModel;
import View.HistoryPanel;
import Model.History;
public class HistoryController {
    HistoryModel historyModel;
    HistoryPanel historyPanel; 
    public HistoryController(HistoryModel historyModel, HistoryPanel historyPanel) {
        this.historyModel = historyModel;
        this.historyPanel = historyPanel;

    }
    public HistoryPanel getHistoryPanel() {
        return historyPanel;
    }

    public HistoryModel getHistoryModel() {
        return historyModel;
    }

    
}