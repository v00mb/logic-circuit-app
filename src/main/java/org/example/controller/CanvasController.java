package org.example.controller;

import org.example.model.CircuitComponent;
import org.example.model.CircuitModel;
import org.example.model.Wire;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class CanvasController {

    private final CircuitModel model;
    private final CircuitCanvas canvas;
    private CanvasState currentState;

    private CircuitComponent selectedComponent = null;
    private Wire selectedWire = null;

    public CanvasController(CircuitModel model, CircuitCanvas canvas) {
        this.model = model;
        this.canvas = canvas;
        this.currentState = new IdleState(this);
    }

    public void handleMousePressed(MouseEvent e) {
        currentState.onMousePressed(e);
    }

    public void handleMouseDragged(MouseEvent e) {
        currentState.onMouseDragged(e);
    }

    public void handleMouseReleased(MouseEvent e) {
        currentState.onMouseReleased(e);
    }

    public void handleMouseMoved(MouseEvent e) {
        currentState.onMouseMoved(e);
    }

    public void setState(CanvasState state) {
        this.currentState = state;
    }

    public CanvasState getCurrentState() {
        return currentState;
    }

    public void setSelected(CircuitComponent c) {
        selectedComponent = c;
        selectedWire = null;
        repaint();
    }

    public void setSelectedWire(Wire w) {
        selectedWire = w;
        selectedComponent = null;
        repaint();
    }

    public void clearSelection() {
        selectedComponent = null;
        selectedWire = null;
        repaint();
    }

    public CircuitComponent getSelectedComponent() {
        return selectedComponent;
    }

    public Wire getSelectedWire() {
        return selectedWire;
    }

    public void deleteSelected() {
        if (selectedComponent != null) {
            model.removeComponent(selectedComponent);
            selectedComponent = null;
        } else if (selectedWire != null) {
            model.removeWire(selectedWire);
            selectedWire = null;
        }
        repaint();
    }

    public CircuitModel getModel() {
        return model;
    }

    public void repaint() {
        canvas.repaint();
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(canvas, msg, "Connection Error", JOptionPane.ERROR_MESSAGE);
    }
}