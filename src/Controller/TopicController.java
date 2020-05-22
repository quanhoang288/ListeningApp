package Controller;

import Model.LevelModel;
import View.TopicPanel;

public class TopicController {
    private LevelModel levelModel;
    private TopicPanel topicPanel;

    public LevelModel getLevelModel() {
        return levelModel;
    }

    public TopicPanel getTopicPanel() {
        return topicPanel;
    }
    public TopicController(LevelModel levelModel, TopicPanel topicPanel) {
        this.levelModel = levelModel;
        this.topicPanel = topicPanel;
    }

    
}