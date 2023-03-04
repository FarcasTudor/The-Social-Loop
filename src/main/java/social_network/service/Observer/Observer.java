package social_network.service.Observer;

import social_network.service.Events.Event;

public interface Observer<E extends Event> {
    void update(E e);

}
