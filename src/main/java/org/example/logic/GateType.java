package org.example.logic;


import org.example.model.Signal;

public enum GateType {

    AND("AND", (a, b) -> {
        if (a == Signal.UNDEFINED || b == Signal.UNDEFINED) return Signal.UNDEFINED;
        return Signal.from(a.isHigh() && b.isHigh());
    }),

    OR("OR", (a, b) -> {
        if (a == Signal.UNDEFINED || b == Signal.UNDEFINED) return Signal.UNDEFINED;
        return Signal.from(a.isHigh() || b.isHigh());
    }),

    NAND("NAND", (a, b) -> {
        if (a == Signal.UNDEFINED || b == Signal.UNDEFINED) return Signal.UNDEFINED;
        return Signal.from(!(a.isHigh() && b.isHigh()));
    }),

    NOR("NOR", (a, b) -> {
        if (a == Signal.UNDEFINED || b == Signal.UNDEFINED) return Signal.UNDEFINED;
        return Signal.from(!(a.isHigh() || b.isHigh()));
    }),

    XOR("XOR", (a, b) -> {
        if (a == Signal.UNDEFINED || b == Signal.UNDEFINED) return Signal.UNDEFINED;
        return Signal.from(a.isHigh() ^ b.isHigh());
    }),

    XNOR("XNOR", (a, b) -> {
        if (a == Signal.UNDEFINED || b == Signal.UNDEFINED) return Signal.UNDEFINED;
        return Signal.from(!(a.isHigh() ^ b.isHigh()));
    });

    private final String label;
    private final GateLogic logic;

    GateType(String label, GateLogic logic) {
        this.label = label;
        this.logic = logic;
    }

    public String getLabel() {
        return label;
    }

    public Signal compute(Signal a, Signal b) {
        return logic.compute(a, b);
    }

}
