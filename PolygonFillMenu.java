import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PolygonFillMenu extends JPanel implements MouseListener, ActionListener {

    ArrayList<Point> polygon = new ArrayList<>();
    boolean polygonClosed = false;
    Color fillColor = Color.RED;

    public PolygonFillMenu(){
        setBackground(Color.WHITE);
        addMouseListener(this);

        JMenuBar bar = new JMenuBar();

        JMenu m = new JMenu("Fill");
        JMenuItem i1 = new JMenuItem("Scale / Scanline Fill");
        JMenuItem i2 = new JMenuItem("Flood Fill");
        JMenuItem i3 = new JMenuItem("Seed Fill");

        i1.addActionListener(this);
        i2.addActionListener(this);
        i3.addActionListener(this);

        m.add(i1); m.add(i2); m.add(i3);
        bar.add(m);

        JFrame f = new JFrame("Concave Polygon Fill");
        f.setJMenuBar(bar);
        f.add(this);
        f.setSize(700,700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void mouseClicked(MouseEvent e){
        if(!polygonClosed){
            if(e.getButton()==3){ // right click closes shape
                polygonClosed = true;
                repaint();
            }else{
                polygon.add(e.getPoint());
                repaint();
            }
        }
    }
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        for(int i=0;i<polygon.size()-1;i++){
            g.drawLine(polygon.get(i).x, polygon.get(i).y,
                       polygon.get(i+1).x, polygon.get(i+1).y);
        }
        if(polygonClosed && polygon.size()>2){
            g.drawLine(polygon.get(0).x, polygon.get(0).y,
                       polygon.get(polygon.size()-1).x, polygon.get(polygon.size()-1).y);
        }
    }

    // ---------- algorithms -----------

    void scanlineFill(Graphics g){
        g.setColor(fillColor);
        Polygon p = makePolygon();
        Rectangle r = p.getBounds();
        for(int y=r.y; y<r.y+r.height; y++){
            for(int x=r.x; x<r.x+r.width; x++){
                if(p.contains(x,y)) g.drawLine(x,y,x,y);
            }
        }
    }

    void floodFill(int x,int y, Color target, Color replacement, Graphics g){
        if(x<0||y<0 || x>=getWidth()||y>=getHeight())return;
        if(g.getColor().equals(replacement))return;
        g.fillRect(x,y,1,1);
        floodFill(x+1,y,target,replacement,g);
        floodFill(x-1,y,target,replacement,g);
        floodFill(x,y+1,target,replacement,g);
        floodFill(x,y-1,target,replacement,g);
    }

    void seedFill(Graphics g){
        Point seed = polygon.get(0); // inside concave region
        g.setColor(fillColor);
        floodFill(seed.x, seed.y, Color.WHITE, fillColor, g);
    }

    public void actionPerformed(ActionEvent e){
        Graphics g = getGraphics();
        if(polygonClosed==false) return;

        String cmd = e.getActionCommand();
        if(cmd.contains("Scale")) scanlineFill(g);
        if(cmd.contains("Flood")) floodFill(polygon.get(0).x, polygon.get(0).y, Color.WHITE, fillColor, g);
        if(cmd.contains("Seed")) seedFill(g);
    }

    Polygon makePolygon(){
        Polygon p = new Polygon();
        for(Point pt: polygon) p.addPoint(pt.x,pt.y);
        return p;
    }

    public static void main(String[] args){
        new PolygonFillMenu();
    }
}