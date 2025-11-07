import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Transform2D extends JPanel {

    // Original polygon points (e.g., triangle)
    private double[][] points = {
        {100, 100},
        {150, 50},
        {200, 100}
    };

    // Transformation matrix settings
    private double translateX = 100;
    private double translateY = 50;
    private double rotateDegrees = 45; // Rotation angle
    private double scaleX = 2;
    private double scaleY =1;
    private double shearX = 0.5     ;
    private double shearY = 0;

    public Transform2D() {
        // Set up the menu bar
        setUpMenuBar();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smoother rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(getWidth() / 2, getHeight() / 2); // Center the origin

        // Draw axes
        drawAxes(g2d);

        // Draw original shape in BLUE
        g2d.setColor(Color.BLUE);
        drawPolygon(g2d, points);

        // Create a transform and apply transformations
        AffineTransform transform = new AffineTransform();

        // Order of operations: Scale → Shear → Rotate → Translate
        transform.translate(translateX, translateY);
        transform.rotate(Math.toRadians(rotateDegrees));
        transform.shear(shearX, shearY);
        transform.scale(scaleX, scaleY);

        // Apply transformation to shape and draw it in RED
        g2d.setColor(Color.RED);
        drawTransformedPolygon(g2d, points, transform);
    }

    // Set up the menu bar with options to modify transformation parameters
    private void setUpMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu for transformations
        JMenu transformMenu = new JMenu("Transformations");

        // Menu items for each transformation
        JMenuItem translateItem = new JMenuItem("Set Translation");
        JMenuItem rotateItem = new JMenuItem("Set Rotation");
        JMenuItem scaleItem = new JMenuItem("Set Scaling");
        JMenuItem shearItem = new JMenuItem("Set Shearing");

        // Add event listeners to menu items
        translateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter translation (x, y):");
                if (input != null && !input.isEmpty()) {
                    String[] values = input.split(",");
                    if (values.length == 2) {
                        try {
                            translateX = Double.parseDouble(values[0].trim());
                            translateY = Double.parseDouble(values[1].trim());
                            repaint();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values.");
                        }
                    }
                }
            }
        });

        rotateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter rotation angle in degrees:");
                if (input != null && !input.isEmpty()) {
                    try {
                        rotateDegrees = Double.parseDouble(input.trim());
                        repaint();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                    }
                }
            }
        });

        scaleItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter scaling factors (x, y):");
                if (input != null && !input.isEmpty()) {
                    String[] values = input.split(",");
                    if (values.length == 2) {
                        try {
                            scaleX = Double.parseDouble(values[0].trim());
                            scaleY = Double.parseDouble(values[1].trim());
                            repaint();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values.");
                        }
                    }
                }
            }
        });

        shearItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter shearing factors (x, y):");
                if (input != null && !input.isEmpty()) {
                    String[] values = input.split(",");
                    if (values.length == 2) {
                        try {
                            shearX = Double.parseDouble(values[0].trim());
                            shearY = Double.parseDouble(values[1].trim());
                            repaint();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values.");
                        }
                    }
                }
            }
        });

        // Add items to the transform menu and the menu bar
        transformMenu.add(translateItem);
        transformMenu.add(rotateItem);
        transformMenu.add(scaleItem);
        transformMenu.add(shearItem);
        menuBar.add(transformMenu);

        // Set the menu bar for the JFrame
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setJMenuBar(menuBar);
        }
    }

    // Draw axes
    private void drawAxes(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawLine(-getWidth(), 0, getWidth(), 0); // X-axis
        g2d.drawLine(0, -getHeight(), 0, getHeight()); // Y-axis
    }

    // Draw original polygon
    private void drawPolygon(Graphics2D g2d, double[][] pts) {
        Path2D path = new Path2D.Double();
        path.moveTo(pts[0][0], -pts[0][1]);
        for (int i = 1; i < pts.length; i++) {
            path.lineTo(pts[i][0], -pts[i][1]);
        }
        path.closePath();
        g2d.draw(path);
    }

    // Draw transformed polygon
    private void drawTransformedPolygon(Graphics2D g2d, double[][] pts, AffineTransform transform) {
        Path2D path = new Path2D.Double();
        double[] pt = new double[2];
        transform.transform(pts[0], 0, pt, 0, 1);
        path.moveTo(pt[0], -pt[1]);
        for (int i = 1; i < pts.length; i++) {
            transform.transform(pts[i], 0, pt, 0, 1);
            path.lineTo(pt[0], -pt[1]);
        }
        path.closePath();
        g2d.draw(path);
    }

    // Entry point
    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Transformations in Java");
        Transform2D panel = new Transform2D();
        frame.add(panel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
    
