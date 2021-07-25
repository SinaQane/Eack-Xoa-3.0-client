package event;

import response.Response;

public interface EventSender
{
    Response sendEvent(Event event);

    void close();
}