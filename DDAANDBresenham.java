import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DDAANDBresenham extends Frame {

    public DDAANDBresenham() {
        setTitle("DDA and Bresenham Line with Dot");
        setSize(600, 400);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    // DDA line drawing with dot in the middle
    private void drawDDALine(Graphics g, int x1, int y1, int x2, int y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float steps = Math.max(Math.abs(dx), Math.abs(dy));
        float xIncrement = dx / steps;
        float yIncrement = dy / steps;

        float x = x1;
        float y = y1;

        int midStep = Math.round(steps / 2);

        for (int i = 0; i <= steps; i++) {
            if (i == midStep) {
                // Draw middle dot (circle)
                drawDot(g, Math.round(x), Math.round(y), Color.RED);
            } else {
                g.drawLine(Math.round(x), Math.round(y), Math.round(x), Math.round(y));
            }
            x += xIncrement;
            y += yIncrement;
        }
    }

    // Bresenham line drawing with dot in the middle
    private void drawBresenhamLine(Graphics g, int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        boolean isSteep = dy > dx;

        int steps = Math.max(dx, dy);
        int midStep = steps / 2;
        int stepCounter = 0;

        int err = (isSteep ? dx : dy) / 2;

        int x = x1, y = y1;

        while (true) {
            if (stepCounter == midStep) {
                drawDot(g, x, y, Color.BLUE); // dot for Bresenham
            } else {
                g.drawLine(x, y, x, y); // plot pixel
            }

            if (x == x2 && y == y2) {
                break;
            }

            if (isSteep) {
                y += sy;
                err -= dx;
                if (err < 0) {
                    x += sx;
                    err += dy;
                }
            } else {
                x += sx;
                err -= dy;
                if (err < 0) {
                    y += sy;
                    err += dx;
                }
            }

            stepCounter++;
        }
    }

    // Draw a circle dot at (x, y)
    private void drawDot(Graphics g, int x, int y, Color color) {
        int size = 6;
        g.setColor(color);
        g.fillOval(x - size / 2, y - size / 2, size, size);
        g.setColor(Color.BLACK); // reset color
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);

        // Draw DDA line with a red dot in the middle
        drawDDALine(g, 50, 50, 250, 150);

        // Draw Bresenham line with a blue dot in the middle
        drawBresenhamLine(g, 50, 200, 250, 300);

        // Labels
        g.drawString("DDA Line with Red Dot", 260, 100);
        g.drawString("Bresenham Line with Blue Dot", 260, 250);
    }

    public static void main(String[] args) {
        new DDAANDBresenham();
    }
}