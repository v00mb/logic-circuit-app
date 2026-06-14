package org.example.ui;

import org.example.model.CircuitComponent;

import java.awt.*;

public interface ComponentRenderer<T extends CircuitComponent> {
    void render(Graphics2D g2, T component, boolean selected);
}