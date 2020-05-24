package model;

import java.util.ArrayList;


public class HistoryModel {
    private ArrayList<History> historyList;
    
    public HistoryModel(){
       
        historyList = getAllHistory();
    }
    public ArrayList<History> getAllHistory(){
        return new ArrayList<>();
    }
    }
