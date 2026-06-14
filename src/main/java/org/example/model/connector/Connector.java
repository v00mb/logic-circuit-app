package org.example.model.connector;


import org.example.model.Signal;
import org.example.observer.SignalChangeListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Connector {

    private final String id;
    private final ConnectorType type;
    private Signal signal;
    private Point absolutePosition;
    private final List<SignalChangeListener> listeners = new ArrayList<>();

    public Connector(ConnectorType type) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.signal = Signal.UNDEFINED;
        this.absolutePosition = new Point(0, 0);
    }

    public String getId() {
        return id;
    }

    public ConnectorType getType() {
        return type;
    }

    public Point getAbsolutePosition() {
        return absolutePosition;
    }

    public void setAbsolutePosition(Point p) {
        this.absolutePosition = new Point(p);
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal newSignal) {
        if (this.signal != newSignal) {
            this.signal = newSignal;
            notifyListeners();
        }
    }

    public void addListener(SignalChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(SignalChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (SignalChangeListener l : listeners) {
            l.onSignalChanged(signal);
        }
    }

    public boolean isInput() {
        return type == ConnectorType.INPUT;
    }

    public boolean isOutput() {
        return type == ConnectorType.OUTPUT;
    }

    @Override
    public String toString() {
        return "Connector[" + type + ", signal=" + signal + "]";
    }
}