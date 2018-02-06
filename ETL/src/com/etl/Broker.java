package com.etl;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Broker<T extends TopicObserver, S extends Topic> implements Observable<T, S> {

    private static Broker instance;
    private List<T> observerList = Collections.synchronizedList(new ArrayList<>());

    private Broker() {

    }

    public static Broker getInstance() {
        if (instance == null) {
            instance = new Broker();
        }
        return instance;
    }

    @Override
    public void addObserver(T observer) {
        if (observerList == null) {
            return;
        }
        if (observerList.contains(observer)) {
            return;
        }
        observerList.add(observer);
    }

    @Override
    public void removeObserver(T observer) {
        if (observerList == null) {
            return;
        }
        observerList.remove(observer);
    }

    @Override
    public void notifyChange(S topic) {
        if (observerList == null) {
            return;
        }
        for (TopicObserver observer : observerList) {
            observer.onChanged(topic);
        }
    }

    public void sendData(S topic) {
        if (topic == null) {
            return;
        }
        if (!topic.isValidData()) {
            return;
        }
        notifyChange(topic);
    }
}
