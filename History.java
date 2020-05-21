package model;

import java.util.ArrayList;
import java.util.Date;


public class History {
    private String topic;
    private Date time;
    private int score;
    private int level;

    }
    public History(String topuc, Date time, int score, int level) {
        this.topic = topic;
        this.time = time;
        this.score = 0;
        this.level = level;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
     public int getscore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    
    
}
