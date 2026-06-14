package org.example.model;

import org.example.model.connector.Connector;
import org.example.observer.SignalChangeListener;

import java.util.UUID;

public class Wire {
    private final String id;
    private final Connector source;
    private final Connector destination;
    private final SignalChangeListener propagator;

    public Wire(Connector source, Connector destination) {
        if (!source.isOutput()) {
            throw new IllegalArgumentException("Wire source must be an OUTPUT connector.");
        }
        if (!destination.isInput()) {
            throw new IllegalArgumentException("Wire destination must be an INPUT connector.");
        }

        this.id = UUID.randomUUID().toString();
        this.source = source;
        this.destination = destination;

        this.propagator = signal -> destination.setSignal(signal);
        source.addListener(propagator);
        destination.setSignal(source.getSignal());
    }

    public void disconnect() {
        source.removeListener(propagator);
        destination.setSignal(Signal.UNDEFINED);
    }

    public String getId() {
        return id;
    }

    public Connector getSource() {
        return source;
    }

    public Connector getDestination() {
        return destination;
    }

    public Signal getSignal() {
        return source.getSignal();
    }
}