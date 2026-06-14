package org.example.ui;

import org.example.logic.GateType;
import org.example.model.Gate;
import org.example.model.connector.Connector;

import java.awt.*;
import java.awt.geom.Path2D;

public class GateRenderer implements ComponentRenderer<Gate> {

    @Override
    public void render(Graphics2D g2, Gate gate, boolean selected) {
        Rectangle b = gate.getBounds();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (selected) RenderUtils.drawSelectionOverlay(g2, b);

        GateType type = gate.getGateType();
        drawGateShape(g2, gate, b);

        g2.setColor(RenderUtils.COLOR_TEXT);
        g2.setFont(RenderUtils.GATE_FONT);
        FontMetrics fm = g2.getFontMetrics();
        String label = type.getLabel();
        int tx = b.x + (b.width - fm.stringWidth(label)) / 2;
        int ty = b.y + (b.height + fm.getAscent()) / 2 - 2;
        g2.drawString(label, tx, ty);

        for (Connector c : gate.getConnectors()) {
            RenderUtils.drawConnector(g2, c);
        }
    }

    private void drawGateShape(Graphics2D g2, Gate gate, Rectangle b) {
        GateType type = gate.getGateType();
        Path2D path = new Path2D.Double();

        int x = b.x, y = b.y, w = b.width, h = b.height;

        switch (type) {
            case AND, NAND -> {
                // D-shape: flat left, arc right
                path.moveTo(x + w * 0.35, y);
                path.lineTo(x + w * 0.35, y + h);
                path.lineTo(x, y + h);
                path.lineTo(x, y);
                path.closePath();

                // arc right half
                Path2D arc = new Path2D.Double();
                arc.moveTo(x + w * 0.35, y);
                arc.curveTo(x + w * 1.1, y, x + w * 1.1, y + h, x + w * 0.35, y + h);

                g2.setColor(RenderUtils.COLOR_BODY);
                g2.fill(arc);
                g2.setColor(new Color(80, 110, 150));
                g2.draw(arc);

                g2.setColor(RenderUtils.COLOR_BODY);
                g2.fill(path);
                g2.setColor(new Color(80, 110, 150));
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(path);

                if (type == GateType.NAND) drawBubble(g2, x + w, y + h / 2);
            }
            case OR, NOR, XOR, XNOR -> {
                // OR-like curved shape
                Path2D body = new Path2D.Double();
                body.moveTo(x + w * 0.1, y);
                body.curveTo(x + w * 0.6, y, x + w, y + h / 2.0, x + w * 0.6, y + h);
                body.lineTo(x + w * 0.1, y + h);
                body.curveTo(x + w * 0.35, y + h * 0.7, x + w * 0.35, y + h * 0.3, x + w * 0.1, y);
                body.closePath();

                g2.setColor(RenderUtils.COLOR_BODY);
                g2.fill(body);
                g2.setColor(new Color(80, 110, 150));
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(body);

                if (type == GateType.NOR || type == GateType.XNOR) {
                    drawBubble(g2, x + w, y + h / 2);
                }
                if (type == GateType.XOR || type == GateType.XNOR) {
                    // Extra curve on left
                    Path2D extra = new Path2D.Double();
                    extra.moveTo(x, y);
                    extra.curveTo(x + w * 0.25, y + h * 0.3, x + w * 0.25, y + h * 0.7, x, y + h);
                    g2.setColor(new Color(80, 110, 150));
                    g2.draw(extra);
                }
            }
        }
        g2.setStroke(new BasicStroke(1));
    }

    private void drawBubble(Graphics2D g2, int cx, int cy) {
        int r = 4;
        g2.setColor(RenderUtils.COLOR_BODY);
        g2.fillOval(cx - r, cy - r, r * 2, r * 2);
        g2.setColor(new Color(80, 110, 150));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawOval(cx - r, cy - r, r * 2, r * 2);
        g2.setStroke(new BasicStroke(1));
    }
}