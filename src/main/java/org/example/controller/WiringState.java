package org.example.controller;

import org.example.model.connector.Connector;
import org.example.ui.WireRenderer;

import java.awt.*;
import java.awt.event.MouseEvent;

public class WiringState implements CanvasState {

    private final CanvasController ctx;
    private final Connector sourceConnector;
    private Point current;
    private final WireRenderer wireRenderer = new WireRenderer();

    public WiringState(CanvasController ctx, Connector source, Point startPoint) {
        this.ctx = ctx;
        this.sourceConnector = source;
        this.current = startPoint;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        current = e.getPoint();
        ctx.repaint();
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        current = e.getPoint();
        ctx.repaint();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        Connector target = ctx.getModel().findConnectorNear(e.getPoint(), 12);

        if (target != null && target != sourceConnector) {
            String error = ctx.getModel().tryAddWire(sourceConnector, target);
            if (error != null) {
                ctx.showError(error);
            }
        }
        ctx.setState(new IdleState(ctx));
        ctx.repaint();
    }

    @Override
    public void paintOverlay(Graphics2D g2) {
        if (current != null) {
            wireRenderer.renderPreview(g2, sourceConnector.getAbsolutePosition(), current);
        }
    }
}