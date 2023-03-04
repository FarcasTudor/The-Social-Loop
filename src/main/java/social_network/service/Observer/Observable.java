package social_network.service.Observer;

import social_network.service.Events.Event;

public interface Observable <E extends Event> {

    void addObserver(Observer<E> e);

    void removeObserver(Observer<E> e);

    void notifyObservers(E e);

}
