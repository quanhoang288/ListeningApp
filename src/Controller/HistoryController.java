package Controller;

import Model.HistoryModel;
import View.HistoryPanel;
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