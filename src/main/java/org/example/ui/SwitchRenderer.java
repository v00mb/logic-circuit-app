package org.example.ui;

import org.example.model.SwitchComponent;
import org.example.model.connector.Connector;

import java.awt.*;

public class SwitchRenderer implements ComponentRenderer<SwitchComponent> {

    @Override
    public void render(Graphics2D g2, SwitchComponent sw, boolean selected) {
        Rectangle b = sw.getBounds();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (selected) RenderUtils.drawSelectionOverlay(g2, b);
        Color bodyColor = sw.isOn()
                ? new Color(30, 140, 60)
                : new Color(140, 40, 40);
        g2.setColor(bodyColor);
        g2.fillRoundRect(b.x, b.y, b.width, b.height, 12, 12);
        g2.setColor(bodyColor.brighter());
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(b.x, b.y, b.width, b.height, 12, 12);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        g2.drawString("SW", b.x + 4, b.y + 14);
        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        String stateStr = sw.isOn() ? "1" : "0";
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(stateStr,
                b.x + (b.width - fm.stringWidth(stateStr)) / 2,
                b.y + b.height - 6);
        for (Connector c : sw.getConnectors()) {
            RenderUtils.drawConnector(g2, c);
        }
    }
}