package org.example.observer;

import org.example.model.Signal;

@FunctionalInterface
public interface SignalChangeListener {
    void onSignalChanged(Signal newSignal);
}