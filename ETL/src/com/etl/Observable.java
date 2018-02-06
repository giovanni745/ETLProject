package com.etl;

public interface Observable<T, E extends Kafka> {
    void addObserver(T observer);

    void removeObserver(T observer);

    void notifyChange(E kafka);

}
