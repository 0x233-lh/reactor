package customDataStream;

public interface EventListener {

    void onEvent(EventSource.Event event);

    void onEventStopped();
}
