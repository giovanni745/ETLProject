package com.etl;
 
public interface TopicObserver extends Observer<Topic> {
    @Override
    void onChanged(Topic topic);
}
