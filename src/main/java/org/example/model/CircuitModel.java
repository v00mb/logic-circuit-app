package org.example.model;

import org.example.model.connector.Connector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircuitModel {

    private final List<CircuitComponent> components = new ArrayList<>();
    private final List<Wire> wires = new ArrayList<>();
    private final List<Runnable> listeners = new ArrayList<>();

    public void addComponent(CircuitComponent c) {
        components.add(c);
        notifyListeners();
    }

    public void removeComponent(CircuitComponent c) {
        List<Wire> toRemove = new ArrayList<>();
        for (Connector connector : c.getConnectors()) {
            for (Wire wire : wires) {
                if (wire.getSource() == connector || wire.getDestination() == connector) {
                    toRemove.add(wire);
                }
            }
        }
        toRemove.forEach(this::removeWire);
        components.remove(c);
        notifyListeners();
    }

    public List<CircuitComponent> getComponents() {
        return Collections.unmodifiableList(components);
    }

    public String tryAddWire(Connector from, Connector to) {
        Connector src = from;
        Connector dst = to;
        if (src.isInput() && dst.isOutput()) {
            src = to;
            dst = from;
        }
        if (!src.isOutput()) {
            return "Cannot connect two INPUT connectors together.";
        }
        if (!dst.isInput()) {
            return "Cannot connect two OUTPUT connectors together.";
        }
        for (Wire w : wires) {
            if (w.getSource() == src && w.getDestination() == dst) {
                return "This connection already exists.";
            }
        }

        for (Wire w : wires) {
            if (w.getDestination() == dst) {
                return "This input connector is already connected. Disconnect the existing wire first.";
            }
        }
        if (wouldCreateCycle(src, dst)) {
            return "This connection would create a feedback loop (cycle).";
        }
        Wire wire = new Wire(src, dst);
        wires.add(wire);
        notifyListeners();
        return null;
    }

    private boolean wouldCreateCycle(Connector src, Connector dst) {
        CircuitComponent dstOwner = findOwner(dst);
        CircuitComponent srcOwner = findOwner(src);
        if (dstOwner == null || srcOwner == null) return false;
        return canReach(dstOwner, srcOwner, new ArrayList<>());
    }

    private boolean canReach(CircuitComponent from, CircuitComponent target, List<CircuitComponent> visited) {
        if (from == target) return true;
        if (visited.contains(from)) return false;
        visited.add(from);
        for (Connector out : from.getConnectors()) {
            if (!out.isOutput()) continue;
            for (Wire w : wires) {
                if (w.getSource() == out) {
                    CircuitComponent next = findOwner(w.getDestination());
                    if (next != null && canReach(next, target, visited)) return true;
                }
            }
        }
        return false;
    }

    public void removeWire(Wire wire) {
        wire.disconnect();
        wires.remove(wire);
        notifyListeners();
    }

    public List<Wire> getWires() {
        return Collections.unmodifiableList(wires);
    }

    public CircuitComponent findOwner(Connector connector) {
        for (CircuitComponent c : components) {
            if (c.getConnectors().contains(connector)) return c;
        }
        return null;
    }

    public Connector findConnectorNear(Point p, int radius) {
        for (CircuitComponent c : components) {
            for (Connector con : c.getConnectors()) {
                if (con.getAbsolutePosition().distance(p) <= radius) {
                    return con;
                }
            }
        }
        return null;
    }

    public CircuitComponent findComponentAt(Point p) {
        for (int i = components.size() - 1; i >= 0; i--) {
            if (components.get(i).contains(p)) return components.get(i);
        }
        return null;
    }

    public Wire findWireNear(Point p, int tolerance) {
        for (Wire w : wires) {
            Point a = w.getSource().getAbsolutePosition();
            Point b = w.getDestination().getAbsolutePosition();
            if (distanceToSegment(p, a, b) <= tolerance) return w;
        }
        return null;
    }

    private double distanceToSegment(Point p, Point a, Point b) {
        double dx = b.x - a.x, dy = b.y - a.y;
        if (dx == 0 && dy == 0) return p.distance(a);
        double t = Math.max(0, Math.min(1, ((p.x - a.x) * dx + (p.y - a.y) * dy) / (dx * dx + dy * dy)));
        return p.distance(a.x + t * dx, a.y + t * dy);
    }

    public void addChangeListener(Runnable r) {
        listeners.add(r);
    }

    private void notifyListeners() {
        listeners.forEach(Runnable::run);
    }
}