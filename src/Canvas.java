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

    Canvas() {
        JLabel label = new JLabel("Тащи меня");
        add(label);
        addMouseListener(this);
        label.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen(), e.getYOnScreen());
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
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
        points.add(new Point(e.getX(), e.getY()));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

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

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
