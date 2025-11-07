import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

class DDAPanel extends JPanel {
    int x1, y1, x2, y2;
    String style;
    int thickness;

    public DDAPanel(int x1, int y1, int x2, int y2, String style, int thickness) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.style = style;
        this.thickness = thickness;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRectangleDDA(g, x1, y1, x2, y2);
    }

    private void drawPixel(Graphics g, int x, int y) {
        if (style.equalsIgnoreCase("solid")) {
            g.fillRect(x, y, thickness, thickness);
        } else if (style.equalsIgnoreCase("dotted")) {
            if ((x + y) % 4 == 0) // skip some pixels to create dotted effect
                g.fillRect(x, y, thickness, thickness);
        } else if (style.equalsIgnoreCase("thick")) {
            g.fillRect(x, y, thickness * 2, thickness * 2); // thicker pixels
        }
    }

    private void drawLineDDA(Graphics g, int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xInc = dx / (float) steps;
        float yInc = dy / (float) steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            drawPixel(g, Math.round(x), Math.round(y));
            x += xInc;
            y += yInc;
        }
    }

    private void drawRectangleDDA(Graphics g, int x1, int y1, int x2, int y2) {
        // Top edge
        drawLineDDA(g, x1, y1, x2, y1);
        // Right edge
        drawLineDDA(g, x2, y1, x2, y2);
        // Bottom edge
        drawLineDDA(g, x2, y2, x1, y2);
        // Left edge
        drawLineDDA(g, x1, y2, x1, y1);
    }
}

public class DDARectangleDrawer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter top-left coordinates (x1 y1): ");
        int x1 = sc.nextInt();
        int y1 = sc.nextInt();

        System.out.println("Enter bottom-right coordinates (x2 y2): ");
        int x2 = sc.nextInt();
        int y2 = sc.nextInt();

        System.out.println("Choose style (solid / dotted / thick): ");
        String style = sc.next();

        System.out.println("Enter thickness (1 for normal, 2-5 for thicker lines): ");
        int thickness = sc.nextInt();

        JFrame frame = new JFrame("DDA Rectangle Drawing");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new DDAPanel(x1, y1, x2, y2, style, thickness));
        frame.setVisible(true);
    }
}