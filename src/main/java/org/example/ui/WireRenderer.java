package org.example.ui;

import org.example.model.Wire;

import java.awt.*;
import java.awt.geom.CubicCurve2D;

public class WireRenderer {

    private static final Stroke WIRE_STROKE = new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final Stroke PREVIEW_STROKE = new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
            0, new float[]{6, 4}, 0);

    public void render(Graphics2D g2, Wire wire, boolean selected) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point a = wire.getSource().getAbsolutePosition();
        Point b = wire.getDestination().getAbsolutePosition();

        Color col = RenderUtils.signalColor(wire.getSignal());
        if (selected) {
            g2.setColor(new Color(255, 220, 80));
            g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            drawCurve(g2, a, b);
        }

        g2.setColor(col);
        g2.setStroke(WIRE_STROKE);
        drawCurve(g2, a, b);
        g2.setStroke(new BasicStroke(1));
    }

    public void renderPreview(Graphics2D g2, Point from, Point to) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(200, 200, 200, 180));
        g2.setStroke(PREVIEW_STROKE);
        drawCurve(g2, from, to);
        g2.setStroke(new BasicStroke(1));
    }

    private void drawCurve(Graphics2D g2, Point a, Point b) {
        int cx = (a.x + b.x) / 2;
        CubicCurve2D curve = new CubicCurve2D.Double(
                a.x, a.y,
                cx, a.y,
                cx, b.y,
                b.x, b.y
        );
        g2.draw(curve);
    }
}