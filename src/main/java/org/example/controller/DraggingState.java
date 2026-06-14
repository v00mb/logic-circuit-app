package org.example.controller;

import org.example.model.CircuitComponent;

import java.awt.*;
import java.awt.event.MouseEvent;

public class DraggingState implements CanvasState {

    private final CanvasController ctx;
    private final CircuitComponent component;
    private Point last;

    public DraggingState(CanvasController ctx, CircuitComponent component, Point start) {
        this.ctx = ctx;
        this.component = component;
        this.last = start;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        Point current = e.getPoint();
        int dx = current.x - last.x;
        int dy = current.y - last.y;
        component.move(dx, dy);
        last = current;
        ctx.repaint();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        ctx.setState(new IdleState(ctx));
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
    }
}