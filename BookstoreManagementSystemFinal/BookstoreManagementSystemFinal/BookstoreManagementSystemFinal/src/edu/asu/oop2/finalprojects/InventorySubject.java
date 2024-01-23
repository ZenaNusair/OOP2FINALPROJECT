
package edu.asu.oop2.finalprojects;

import java.util.ArrayList;
import java.util.List;

public class InventorySubject {
    private final List<InventoryObserver> observers = new ArrayList<>();

    public void addObserver(InventoryObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (InventoryObserver observer : observers) {
            observer.update();
        }
    }
}