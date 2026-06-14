package org.example.model;

import org.example.model.connector.Connector;
import org.example.model.connector.ConnectorType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Joint extends CircuitComponent {

    public static final int RADIUS = 8;
    public static final int SIZE = RADIUS * 2;

    private final Connector input;
    private final List<Connector> outputs = new ArrayList<>();

    public Joint(Point position) {
        super(position);
        this.input = new Connector(ConnectorType.INPUT);
        input.addListener(s -> evaluate());
        addOutputConnector();
        addOutputConnector();
        updateConnectorPositions();
    }

    public Connector addOutputConnector() {
        Connector out = new Connector(ConnectorType.OUTPUT);
        outputs.add(out);
        updateConnectorPositions();
        return out;
    }

    public void removeOutputConnector(Connector c) {
        outputs.remove(c);
        updateConnectorPositions();
    }

    @Override
    public List<Connector> getConnectors() {
        List<Connector> all = new ArrayList<>();
        all.add(input);
        all.addAll(outputs);
        return all;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x - RADIUS, position.y - RADIUS, SIZE, SIZE);
    }

    @Override
    protected void updateConnectorPositions() {
        input.setAbsolutePosition(new Point(position.x - RADIUS, position.y));
        int n = outputs.size();
        for (int i = 0; i < n; i++) {
            int offsetY = (n == 1) ? 0 : (i - (n - 1) / 2) * 20;
            outputs.get(i).setAbsolutePosition(new Point(position.x + RADIUS, position.y + offsetY));
        }
    }

    @Override
    public void evaluate() {
        for (Connector out : outputs) {
            out.setSignal(input.getSignal());
        }
    }

    public Connector getInput() {
        return input;
    }

    public List<Connector> getOutputs() {
        return outputs;
    }
}