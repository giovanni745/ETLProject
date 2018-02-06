package com.etl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.etl.database.DatabaseManager;

public class Consumer implements TopicObserver {

    public static final int TIME_WINDOW = 10;

    private BlockingQueue<Topic> queue = new LinkedBlockingDeque();

    @Override
    public void onChanged(Topic topic) {
        try {
            checkDuplicateAndWriteDB(topic);
        } catch (InterruptedException e) {
            //TODO exception handling
        }
    }

    private void checkDuplicateAndWriteDB(Topic topic) throws InterruptedException {
        if (queue.isEmpty()) {
            queue.put(topic);
            return;
        }
        Topic frontTopic = queue.peek();
        Topic newTopic = topic;

        while (calculateTopicIntervalTime(frontTopic, newTopic) > TIME_WINDOW) {
            DatabaseManager.INSTANCE.writeData(frontTopic);
            queue.take();
            if (queue.isEmpty()) {
                break;
            }
            frontTopic = queue.peek();
        }
        if (queue.contains(newTopic)) {
            return;
        }
        queue.put(newTopic);
    }

    private long calculateTopicIntervalTime(Topic beforeTopic, Topic newTopic) {
        return Math.abs(Long.valueOf(newTopic.getEventTimeTamp()) - Long.valueOf(beforeTopic.getEventTimeTamp()));
    }
}
