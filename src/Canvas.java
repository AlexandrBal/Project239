import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    int w = 700;
    int h = 700;
    ArrayList<Point> points = new ArrayList<>();
    boolean pointOnClick = false;
    int pressX, pressY;

    Canvas() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.red);


        for (Point point : points){
            g.fillOval(point.x-5, point.y-5, 10, 10);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (pointOnClick){
            points.add(new Point(e.getX(), e.getY()));
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressX = e.getX();
        pressY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (Point point : points) {
            point.x += (e.getX() - pressX);
            point.y += (e.getY() - pressY);
        }
        pressX = e.getX();
        pressY = e.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
