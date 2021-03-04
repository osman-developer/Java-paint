/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lic04_paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author ULFS3
 */
public class MainFrame extends JFrame {

    private Line current = null;
    private JButton undoBtn = new JButton("Undo");
    private JButton clearBtn = new JButton("Clear");
    private JButton exitBtn = new JButton("Exit");

    private Color[] colors = {Color.red, Color.green, Color.blue, Color.yellow};
    private String[] colorNames = {"Red", "Green", "Blue", "Yellow"};
    private JComboBox colorBox = new JComboBox(colorNames);

    private JLabel mesg = new JLabel("Press To begin drawing");
    private JLabel coord = new JLabel("x: 0 , y: 0");

    List<Line> ls = new ArrayList();

    private JPanel canvas = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Line line : ls) {
                line.draw(g);
            }
        }
    };

    public MainFrame() {
        initComponents();
        initActions();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setTitle("Paint");

        JPanel northPane = new JPanel(new FlowLayout(FlowLayout.CENTER));//FlowLayout center
        northPane.add(colorBox);
        northPane.add(undoBtn);
        northPane.add(clearBtn);
        northPane.add(exitBtn);

        this.add(northPane, BorderLayout.NORTH);

        this.add(canvas, BorderLayout.CENTER);

        JPanel southPane = new JPanel(new GridLayout(1, 2));
        southPane.add(mesg);
        southPane.add(coord);

        this.add(southPane, BorderLayout.SOUTH);

        northPane.setBorder(BorderFactory.createEtchedBorder());
        canvas.setBorder(BorderFactory.createEtchedBorder());
        southPane.setBorder(BorderFactory.createEtchedBorder());
        canvas.setBackground(Color.white);
        canvas.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

    }

    private void initActions() {
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitActionPerformed(e);
            }
        });

        undoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undoPerformed(e);
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearPerformed(e);
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = colorBox.getSelectedIndex();
                current = new Line(e.getX(), e.getY(), e.getX(), e.getY(), colors[index]);
                ls.add(current);
                mesg.setText("Release to draw");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                current.setX2(e.getX());
//                current.setY2(e.getY());
//                canvas.repaint();
                mesg.setText("Press to begin");
            }
        });

        canvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                current.setX2(e.getX());
                current.setY2(e.getY());
                canvas.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseMovedPerformed(e);
            }
        });

    }

    private void clearPerformed(ActionEvent e) {
        ls.clear();
        canvas.repaint();
    }

    private void undoPerformed(ActionEvent e) {
        if (!ls.isEmpty()) {
            ls.remove(ls.size() - 1);
            canvas.repaint();
        }
    }
    private void exitActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void mouseMovedPerformed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        coord.setText("x: " + x + ", y: " + y);
    }

    

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

}
