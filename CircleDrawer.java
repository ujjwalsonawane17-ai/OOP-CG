import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

class CirclePanel extends JPanel {
    private int xc, yc, r;
    private String algorithm, style;

    public CirclePanel(int xc, int yc, int r, String algorithm, String style) {
        this.xc = xc;
        this.yc = yc;
        this.r = r;
        this.algorithm = algorithm;
        this.style = style;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (algorithm.toLowerCase()) {
            case "dda":
                drawCircleDDA(g, xc, yc, r);
                break;
            case "bresenham":
                drawCircleBresenham(g, xc, yc, r);
                break;
            case "midpoint":
                drawCircleMidpoint(g, xc, yc, r);
                break;
            default:
                System.out.println("Invalid algorithm choice!");
        }
    }

    private void plot(Graphics g, int x, int y) {
        if (style.equalsIgnoreCase("solid")) {
            g.fillRect(x, y, 1, 1);
        } else if (style.equalsIgnoreCase("dotted")) {
            if ((x + y) % 2 == 0) g.fillRect(x, y, 1, 1);
        } else if (style.equalsIgnoreCase("dashed")) {
            if ((x + y) % 5 != 0) g.fillRect(x, y, 1, 1);
        }
    }

    private void plot8Points(Graphics g, int xc, int yc, int x, int y) {
        plot(g, xc + x, yc + y);
        plot(g, xc - x, yc + y);
        plot(g, xc + x, yc - y);
        plot(g, xc - x, yc - y);
        plot(g, xc + y, yc + x);
        plot(g, xc - y, yc + x);
        plot(g, xc + y, yc - x);
        plot(g, xc - y, yc - x);
    }

    private void drawCircleDDA(Graphics g, int xc, int yc, int r) {
        double x = r, y = 0;
        double dTheta = 1.0 / r;
        while (x > y) {
            plot8Points(g, xc, yc, (int) x, (int) y);
            y += dTheta;
            x = Math.sqrt(r * r - y * y);
        }
    }

    private void drawCircleBresenham(Graphics g, int xc, int yc, int r) {
        int x = 0, y = r;
        int d = 3 - 2 * r;
        while (x <= y) {
            plot8Points(g, xc, yc, x, y);
            if (d <= 0) d += 4 * x + 6;
            else {
                d += 4 * (x - y) + 10;
                y--;
            }
            x++;
        }
    }

    private void drawCircleMidpoint(Graphics g, int xc, int yc, int r) {
        int x = 0, y = r;
        int p = 1 - r;
        while (x <= y) {
            plot8Points(g, xc, yc, x, y);
            x++;
            if (p < 0) p += 2 * x + 1;
            else {
                y--;
                p += 2 * (x - y) + 1;
            }
        }
    }
}

public class CircleDrawer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter center coordinates (xc yc): ");
        int xc = sc.nextInt();
        int yc = sc.nextInt();

        System.out.print("Enter radius: ");
        int r = sc.nextInt();

        System.out.print("Choose algorithm (DDA / Bresenham / Midpoint): ");
        String algorithm = sc.next();

        System.out.print("Choose style (solid / dotted / dashed): ");
        String style = sc.next();

        JFrame frame = new JFrame("Circle Drawing");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CirclePanel(xc, yc, r, algorithm, style));
        frame.setVisible(true);

        sc.close();
    }
}