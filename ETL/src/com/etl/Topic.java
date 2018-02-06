package com.etl;

public class Topic implements Kafka {
    private long eventId;
    private String eventTimeTamp;
    private String serviceCode;
    private String eventContext;

    public Topic(long eventId, String eventTimeTamp, String serviceCode, String eventContext) {
        this.eventId = eventId;
        this.eventTimeTamp = eventTimeTamp;
        this.serviceCode = serviceCode;
        this.eventContext = eventContext;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof Topic == false) {
            return false;
        }
        Topic topic = (Topic) obj;

        return topic.eventId == this.eventId;
    }

    public boolean isValidData() {
        if (eventId < 0) {
            return false;
        }
        if (StringUtils.isEmpty(eventContext)) {
            return false;
        }
        if (StringUtils.isEmpty(eventTimeTamp)) {
            return false;
        }
        if (StringUtils.isEmpty(serviceCode)) {
            return false;
        }
        return true;
    }

    public String getEventTimeTamp() {
        return eventTimeTamp;
    }

    public long getEventId() {
        return eventId;
    }

    public String getEventContext() {
        return eventContext;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public static class TopicBuilder {
        private long eventId;
        private String eventTimeTamp;
        private String serviceCode;
        private String eventContext;

        public TopicBuilder setEventContext(String eventContext) {
            this.eventContext = eventContext;
            return this;
        }

        public TopicBuilder setEventId(long eventId) {
            this.eventId = eventId;
            return this;
        }

        public TopicBuilder setEventTimeTamp(String eventTimeTamp) {
            this.eventTimeTamp = eventTimeTamp;
            return this;
        }

        public TopicBuilder setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
            return this;
        }

        public Topic build() {
            return new Topic(eventId, eventTimeTamp, serviceCode, eventContext);
        }
    }
}
