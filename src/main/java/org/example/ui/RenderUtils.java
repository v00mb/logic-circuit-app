package org.example.ui;

import org.example.model.Signal;
import org.example.model.connector.Connector;

import java.awt.*;

public final class RenderUtils {

    public static final Color COLOR_HIGH = new Color(0, 200, 80);
    public static final Color COLOR_LOW = new Color(220, 60, 60);
    public static final Color COLOR_UNDEFINED = new Color(160, 160, 160);
    public static final Color COLOR_BODY = new Color(40, 60, 90);
    public static final Color COLOR_BODY_SEL = new Color(60, 100, 160);
    public static final Color COLOR_TEXT = Color.WHITE;
    public static final Color COLOR_SELECTION = new Color(100, 160, 255, 120);

    public static final int CONNECTOR_RADIUS = 5;
    public static final Font GATE_FONT = new Font("SansSerif", Font.BOLD, 12);

    private RenderUtils() {
    }

    public static Color signalColor(Signal s) {
        return switch (s) {
            case HIGH -> COLOR_HIGH;
            case LOW -> COLOR_LOW;
            case UNDEFINED -> COLOR_UNDEFINED;
        };
    }

    public static void drawConnector(Graphics2D g2, Connector c) {
        Point p = c.getAbsolutePosition();
        Color col = signalColor(c.getSignal());
        g2.setColor(col);
        g2.fillOval(p.x - CONNECTOR_RADIUS, p.y - CONNECTOR_RADIUS,
                CONNECTOR_RADIUS * 2, CONNECTOR_RADIUS * 2);
        g2.setColor(Color.WHITE);
        g2.drawOval(p.x - CONNECTOR_RADIUS, p.y - CONNECTOR_RADIUS,
                CONNECTOR_RADIUS * 2, CONNECTOR_RADIUS * 2);
    }

    public static void drawSelectionOverlay(Graphics2D g2, Rectangle bounds) {
        g2.setColor(COLOR_SELECTION);
        g2.fillRoundRect(bounds.x - 3, bounds.y - 3, bounds.width + 6, bounds.height + 6, 8, 8);
        g2.setColor(new Color(100, 160, 255));
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                0, new float[]{4, 4}, 0));
        g2.drawRoundRect(bounds.x - 3, bounds.y - 3, bounds.width + 6, bounds.height + 6, 8, 8);
        g2.setStroke(new BasicStroke(1));
    }
}