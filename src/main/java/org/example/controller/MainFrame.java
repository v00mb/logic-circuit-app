package org.example.controller;


import org.example.model.CircuitModel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Logic Circuit Simulator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        CircuitModel model = new CircuitModel();
        CircuitCanvas canvas = new CircuitCanvas();
        CanvasController controller = new CanvasController(model, canvas);
        canvas.setController(controller);
        setLayout(new BorderLayout());
        add(buildToolBar(controller), BorderLayout.NORTH);
        add(new JScrollPane(canvas), BorderLayout.CENTER);
        add(buildStatusBar(), BorderLayout.SOUTH);
        setJMenuBar(buildMenuBar(controller));
        applyDarkTheme();
    }

    private JToolBar buildToolBar(CanvasController controller) {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        tb.setBackground(new Color(30, 36, 50));

        JLabel hint = new JLabel(
                "  Right-click canvas to add components  |  " +
                        "Left-click connector to start wire  |  " +
                        "Left-click Switch to toggle  |  " +
                        "Drag component to move  |  DEL = delete selected");
        hint.setForeground(new Color(160, 180, 220));
        hint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tb.add(hint);

        tb.addSeparator();

        JButton delBtn = new JButton("🗑 Delete");
        delBtn.setToolTipText("Delete selected component or wire (DEL)");
        delBtn.addActionListener(e -> controller.deleteSelected());
        styleButton(delBtn);
        tb.add(delBtn);

        return tb;
    }

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        bar.setBackground(new Color(20, 24, 32));
        JLabel lbl = new JLabel("Logic Circuit Simulator — Right-click to add gates, switches, joints");
        lbl.setForeground(new Color(120, 140, 170));
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        bar.add(lbl);

        // Legend
        JLabel[] legend = {
                colorDot(new Color(0, 200, 80), " HIGH (1)"),
                colorDot(new Color(220, 60, 60), " LOW (0)"),
                colorDot(new Color(160, 160, 160), " UNDEFINED"),
        };
        for (JLabel l : legend) {
            bar.add(Box.createHorizontalStrut(12));
            bar.add(l);
        }
        return bar;
    }

    private JLabel colorDot(Color c, String text) {
        JLabel lbl = new JLabel("●" + text);
        lbl.setForeground(c);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        return lbl;
    }

    private JMenuBar buildMenuBar(CanvasController controller) {
        JMenuBar mb = new JMenuBar();
        mb.setBackground(new Color(30, 36, 50));

        JMenu editMenu = new JMenu("Edit");
        editMenu.setForeground(Color.WHITE);

        JMenuItem delItem = new JMenuItem("Delete Selected");
        delItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        delItem.addActionListener(e -> controller.deleteSelected());
        editMenu.add(delItem);

        mb.add(editMenu);
        return mb;
    }

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(50, 60, 85));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
    }

    private void applyDarkTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        getContentPane().setBackground(new Color(20, 24, 32));
    }
}