package org.example.controller;

import org.example.model.CircuitComponent;
import org.example.model.SwitchComponent;
import org.example.model.Wire;
import org.example.model.connector.Connector;

import java.awt.*;
import java.awt.event.MouseEvent;

public class IdleState implements CanvasState {

    private final CanvasController ctx;

    public IdleState(CanvasController ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point p = e.getPoint();
        if (e.getButton() == MouseEvent.BUTTON1) {
            Connector con = ctx.getModel().findConnectorNear(p, 10);
            if (con != null) {
                ctx.setState(new WiringState(ctx, con, p));
                return;
            }
            CircuitComponent comp = ctx.getModel().findComponentAt(p);
            if (comp != null) {
                if (comp instanceof SwitchComponent sw) {
                    sw.toggle();
                    ctx.repaint();
                    return;
                }
                ctx.setSelected(comp);
                ctx.setState(new DraggingState(ctx, comp, p));
                return;
            }
            Wire wire = ctx.getModel().findWireNear(p, 6);
            if (wire != null) {
                ctx.setSelectedWire(wire);
                ctx.repaint();
                return;
            }
            ctx.clearSelection();
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
    }
}