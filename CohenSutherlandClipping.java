import javax.swing.*;
import java.awt.*;

public class CohenSutherlandClipping extends JPanel {
    // Clipping window boundaries
    final int xMin = 100, yMin = 100, xMax = 300, yMax = 300;

    // Region codes
    final int INSIDE = 0; // 0000
    final int LEFT = 1;   // 0001
    final int RIGHT = 2;  // 0010
    final int BOTTOM = 4; // 0100
    final int TOP = 8;    // 1000

    // Function to compute region code for a point (x, y)
    private int computeCode(int x, int y) {
        int code = INSIDE;

        if (x < xMin) code |= LEFT;
        else if (x > xMax) code |= RIGHT;
        if (y < yMin) code |= BOTTOM;
        else if (y > yMax) code |= TOP;

        return code;
    }

    // Cohen-Sutherland line clipping algorithm
    private void cohenSutherlandClip(Graphics g, int x1, int y1, int x2, int y2) {
        int code1 = computeCode(x1, y1);
        int code2 = computeCode(x2, y2);

        boolean accept = false;

        while (true) {
            if ((code1 | code2) == 0) {
                // Both points inside
                accept = true;
                break;
            } else if ((code1 & code2) != 0) {
                // Both outside (same region)
                break;
            } else {
                // Line needs clipping
                int codeOut;
                int x = 0, y = 0;

                // Choose one point outside
                codeOut = (code1 != 0) ? code1 : code2;

                if ((codeOut & TOP) != 0) {
                    x = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
                    y = yMax;
                } else if ((codeOut & BOTTOM) != 0) {
                    x = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
                    y = yMin;
                } else if ((codeOut & RIGHT) != 0) {
                    y = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);
                    x = xMax;
                } else if ((codeOut & LEFT) != 0) {
                    y = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);
                    x = xMin;
                }

                // Replace outside point with intersection
                if (codeOut == code1) {
                    x1 = x;
                    y1 = y;
                    code1 = computeCode(x1, y1);
                } else {
                    x2 = x;
                    y2 = y;
                    code2 = computeCode(x2, y2);
                }
            }
        }

        // Draw clipped line if accepted
        if (accept) {
            g.setColor(Color.GREEN);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw clipping window
        g.setColor(Color.BLACK);
        g.drawRect(xMin, yMin, xMax - xMin, yMax - yMin);

        // Original lines (in red)
        g.setColor(Color.RED);
        g.drawLine(50, 50, 350, 350);   // Diagonal
        g.drawLine(120, 80, 280, 320);  // Partially inside
        g.drawLine(50, 200, 80, 250);   // Completely outside

        // Apply Cohen-Sutherland (draw in green)
        cohenSutherlandClip(g, 50, 50, 350, 350);
        cohenSutherlandClip(g, 120, 80, 280, 320);
        cohenSutherlandClip(g, 50, 200, 80, 250);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cohen-Sutherland Line Clipping Algorithm");
        CohenSutherlandClipping panel = new CohenSutherlandClipping();

        frame.add(panel);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
