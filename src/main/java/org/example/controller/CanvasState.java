package org.example.controller;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface CanvasState {
    void onMousePressed(MouseEvent e);

    void onMouseDragged(MouseEvent e);

    void onMouseReleased(MouseEvent e);

    void onMouseMoved(MouseEvent e);

    default void paintOverlay(Graphics2D g2) {
    }
}