package org.example.logic;

import org.example.model.Signal;

@FunctionalInterface
public interface GateLogic {
    Signal compute(Signal a, Signal b);
}