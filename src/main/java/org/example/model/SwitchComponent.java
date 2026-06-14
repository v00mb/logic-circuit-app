package org.example.model;

import org.example.model.connector.Connector;
import org.example.model.connector.ConnectorType;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class SwitchComponent extends CircuitComponent {

    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;

    private boolean state = false;
    private final Connector output;

    public SwitchComponent(Point position) {
        super(position);
        this.output = new Connector(ConnectorType.OUTPUT);
        updateConnectorPositions();
        evaluate();
    }

    public void toggle() {
        state = !state;
        evaluate();
    }

    public boolean isOn() {
        return state;
    }

    @Override
    public List<Connector> getConnectors() {
        return Collections.singletonList(output);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, WIDTH, HEIGHT);
    }

    @Override
    protected void updateConnectorPositions() {
        output.setAbsolutePosition(new Point(position.x + WIDTH, position.y + HEIGHT / 2));
    }

    @Override
    public void evaluate() {
        output.setSignal(state
                ? Signal.HIGH
                : Signal.LOW);
    }

    public Connector getOutput() {
        return output;
    }
}