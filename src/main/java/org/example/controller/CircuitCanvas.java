package org.example.controller;

import org.example.logic.GateType;
import org.example.model.*;
import org.example.ui.GateRenderer;
import org.example.ui.JointRenderer;
import org.example.ui.SwitchRenderer;
import org.example.ui.WireRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CircuitCanvas extends JPanel {

    private CanvasController controller;

    private final GateRenderer gateRenderer = new GateRenderer();
    private final SwitchRenderer switchRenderer = new SwitchRenderer();
    private final JointRenderer jointRenderer = new JointRenderer();
    private final WireRenderer wireRenderer = new WireRenderer();

    public CircuitCanvas() {
        setBackground(new Color(20, 24, 32));
        setFocusable(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
                if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
                    showContextMenu(e);
                } else {
                    controller.handleMousePressed(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showContextMenu(e);
                else controller.handleMouseReleased(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                controller.handleMouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                controller.handleMouseMoved(e);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE ||
                        e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    controller.deleteSelected();
                }
            }
        });
    }

    public void setController(CanvasController controller) {
        this.controller = controller;
        controller.getModel().addChangeListener(this::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (controller == null) return;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g2);
        var model = controller.getModel();
        for (Wire wire : model.getWires()) {
            boolean sel = wire == controller.getSelectedWire();
            wireRenderer.render(g2, wire, sel);
        }
        for (CircuitComponent comp : model.getComponents()) {
            boolean sel = comp == controller.getSelectedComponent();
            if (comp instanceof Gate gate) gateRenderer.render(g2, gate, sel);
            else if (comp instanceof SwitchComponent sw) switchRenderer.render(g2, sw, sel);
            else if (comp instanceof Joint j) jointRenderer.render(g2, j, sel);
        }
        controller.getCurrentState().paintOverlay(g2);
        g2.dispose();
    }

    private void drawGrid(Graphics2D g2) {
        g2.setColor(new Color(35, 42, 55));
        int step = 20;
        for (int x = 0; x < getWidth(); x += step) g2.drawLine(x, 0, x, getHeight());
        for (int y = 0; y < getHeight(); y += step) g2.drawLine(0, y, getWidth(), y);
    }

    private void showContextMenu(MouseEvent e) {
        Point p = e.getPoint();
        JPopupMenu menu = new JPopupMenu();

        JMenu gateMenu = new JMenu("Add Gate");
        for (GateType type : GateType.values()) {
            JMenuItem item = new JMenuItem(type.getLabel());
            item.addActionListener(ev -> {
                Gate gate = new Gate(type, p);
                controller.getModel().addComponent(gate);
            });
            gateMenu.add(item);
        }
        menu.add(gateMenu);
        JMenuItem swItem = new JMenuItem("Add Switch");
        swItem.addActionListener(ev -> controller.getModel().addComponent(new SwitchComponent(p)));
        menu.add(swItem);
        JMenuItem jItem = new JMenuItem("Add Joint");
        jItem.addActionListener(ev -> controller.getModel().addComponent(new Joint(p)));
        menu.add(jItem);
        if (controller.getSelectedComponent() != null || controller.getSelectedWire() != null) {
            menu.addSeparator();
            JMenuItem del = new JMenuItem("Delete Selected");
            del.addActionListener(ev -> controller.deleteSelected());
            menu.add(del);
        }
        Wire nearWire = controller.getModel().findWireNear(p, 6);
        if (nearWire != null) {
            menu.addSeparator();
            JMenuItem delWire = new JMenuItem("Delete Wire");
            delWire.addActionListener(ev -> controller.getModel().removeWire(nearWire));
            menu.add(delWire);
        }
        menu.show(this, p.x, p.y);
    }
}