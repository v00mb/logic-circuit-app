package org.example.model;

import org.example.logic.GateType;
import org.example.model.connector.Connector;
import org.example.model.connector.ConnectorType;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Gate extends CircuitComponent {

    public static final int WIDTH = 80;
    public static final int HEIGHT = 60;

    private final GateType gateType;
    private final Connector inputA;
    private final Connector inputB;
    private final Connector output;

    public Gate(GateType gateType, Point position) {
        super(position);
        this.gateType = gateType;
        this.inputA = new Connector(ConnectorType.INPUT);
        this.inputB = new Connector(ConnectorType.INPUT);
        this.output = new Connector(ConnectorType.OUTPUT);

        inputA.addListener(s -> evaluate());
        inputB.addListener(s -> evaluate());

        updateConnectorPositions();
    }

    @Override
    public List<Connector> getConnectors() {
        return Arrays.asList(inputA, inputB, output);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, WIDTH, HEIGHT);
    }

    @Override
    protected void updateConnectorPositions() {
        inputA.setAbsolutePosition(new Point(position.x, position.y + HEIGHT / 3));
        inputB.setAbsolutePosition(new Point(position.x, position.y + 2 * HEIGHT / 3));
        output.setAbsolutePosition(new Point(position.x + WIDTH, position.y + HEIGHT / 2));
    }

    @Override
    public void evaluate() {
        output.setSignal(gateType.compute(inputA.getSignal(), inputB.getSignal()));
    }

    public GateType getGateType() {
        return gateType;
    }

    public Connector getInputA() {
        return inputA;
    }

    public Connector getInputB() {
        return inputB;
    }

    public Connector getOutput() {
        return output;
    }
}
