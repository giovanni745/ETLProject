package com.etl;
 
import java.util.Random;

public class Producer {

    private static Producer producer;

    private Producer() {

    }

    public static Producer getInstance() {
        if (producer == null) {
            producer = new Producer();
        }
        return producer;
    }

    private DataGenerator generator;


    public void produceKafkaData() {
        if (generator == null) {
            generator = new DataGenerator();
        }
        generator.generateKakfaData(topic -> Broker.getInstance().sendData(topic));
    }

    private static class DataGenerator {

        private final int MAXIMUM_TIME = 100000;
        private final int SLEEP_TIME = 1000;
        private int timeCount;
        private Random random;

        public interface GenerateListener {
            void onGenerated(Topic topic);
        }

        public void generateKakfaData(GenerateListener listener) {
            random = new Random();
            Thread thread = new Thread(() -> {
                while (timeCount++ < MAXIMUM_TIME) {
                    listener.onGenerated(new Topic.TopicBuilder()
                            .setEventContext("eventContext")
                            .setServiceCode("serviceCode")
                            .setEventId(random.nextInt(20) + 1)
                            .setEventTimeTamp(String.valueOf(timeCount))
                            .build());
                    waitForDataGenerate();
                }
            });
            thread.start();
        }

        private void waitForDataGenerate() {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
