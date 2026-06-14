package org.example.ui;

import org.example.model.Joint;

import java.awt.*;

public class JointRenderer implements ComponentRenderer<Joint> {

    @Override
    public void render(Graphics2D g2, Joint joint, boolean selected) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point p = joint.getPosition();
        int r = Joint.RADIUS;
        if (selected) {
            g2.setColor(new Color(100, 160, 255, 120));
            g2.fillOval(p.x - r - 4, p.y - r - 4, (r + 4) * 2, (r + 4) * 2);
        }
        Color col = RenderUtils.signalColor(joint.getInput().getSignal());
        g2.setColor(col);
        g2.fillOval(p.x - r, p.y - r, r * 2, r * 2);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawOval(p.x - r, p.y - r, r * 2, r * 2);
        g2.setStroke(new BasicStroke(1));
//        for (Connector c : joint.getConnectors()) {
//            RenderUtils.drawConnector(g2, c);
//        }
    }
}