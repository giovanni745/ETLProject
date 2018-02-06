package com.etl.test;

import com.etl.Broker;
import com.etl.Producer;
import com.etl.Consumer;
 
public class ETLTest {

    public static void main(String[] args) {
        Producer.getInstance().produceKafkaData();
        Broker.getInstance().addObserver(new Consumer());
    }
}
 