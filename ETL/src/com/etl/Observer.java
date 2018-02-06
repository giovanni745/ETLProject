package com.etl;
 
public interface Observer<T extends Kafka> {
    void onChanged(T kafka);
}
