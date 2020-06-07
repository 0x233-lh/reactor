package customDataStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventSource {

    private List<EventListener> listenerList;

    public EventSource() {
        this.listenerList = new ArrayList<>();
    }

    public void register(EventListener eventListener) {
        listenerList.add(eventListener);
    }

    public void onEvent(Event event) {
        for (EventListener eventListener : listenerList) {
            eventListener.onEvent(event);
        }
    }

    public void eventStopped() {
        for (EventListener eventListener : listenerList) {
            eventListener.onEventStopped();
        }
    }

    public static class Event {
        private Date timestamp;
        private String message;

        public Event(Date timestamp, String message) {
            this.timestamp = timestamp;
            this.message = message;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "timestamp=" + timestamp +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
