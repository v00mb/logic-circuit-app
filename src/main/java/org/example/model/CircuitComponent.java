package org.example.model;

import org.example.model.connector.Connector;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public abstract class CircuitComponent {

    private final String id;
    protected Point position;

    protected CircuitComponent(Point position) {
        this.id = UUID.randomUUID().toString();
        this.position = new Point(position);
    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return new Point(position);
    }

    public void setPosition(Point p) {
        this.position = new Point(p);
        updateConnectorPositions();
    }

    public void move(int dx, int dy) {
        position.translate(dx, dy);
        updateConnectorPositions();
    }

    public abstract List<Connector> getConnectors();

    public abstract Rectangle getBounds();

    protected abstract void updateConnectorPositions();

    public abstract void evaluate();

    public boolean contains(Point p) {
        return getBounds().contains(p);
    }
}
