import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    MyPoint centerPoint = new MyPoint(0, 0);
    Axes Ox, Oy;
    ArrayList<MyPoint> points = new ArrayList<>();
    boolean pointOnClick = false;
    int pressX, pressY;
    MyPoint circle1 = null;
    MyPoint circle2 = null;
    double scale = 1.0; // Текущий масштаб (1 = 100%)
    double translateX = 0; // Смещение по X
    double translateY = 0; // Смещение по Y
    Point lastDragPoint; // Для перемещения холста

    Canvas() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(we -> {
            double zoomFactor = we.getWheelRotation() < 0 ? 1.1 : 0.9;
            Point mousePos = we.getPoint();

            Point2D worldPosBefore = screenToWorld(mousePos);
            scale *= zoomFactor;
            Point2D worldPosAfter = screenToWorld(mousePos);

            translateX += (worldPosAfter.getX() - worldPosBefore.getX()) * scale;
            translateY += (worldPosAfter.getY() - worldPosBefore.getY()) * scale;

            repaint();
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        points.add(centerPoint);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(getWidth()/2, getHeight()/2);


        g2d.translate(translateX, translateY);
        g2d.scale(scale, scale);

        System.out.println(centerPoint.x + " " + centerPoint.y);


        Ox = new Axes((int)((-getWidth()-translateX)/scale), 0, (int)((getWidth()-translateX)/scale), 0);
        Oy = new Axes(0, (int)((-getHeight()-translateY)/scale), 0, (int)((getHeight()-translateY)/scale));

        g.setColor(Color.BLACK);
        g.drawLine(Oy.x1, Oy.y1, Oy.x2, Oy.y2);
        g.drawLine(Ox.x1, Ox.y1, Ox.x2, Ox.y2);

        g.setColor(Color.red);
        for (MyPoint point : points){
            g.fillOval(point.x-4, point.y-4, 8, 8);
        }

        if (circle1 != null && circle2 != null) {
            g.drawOval(circle1.x-(int)circle1.possible_rad, circle1.y-(int)circle1.possible_rad, (int)circle1.possible_rad*2, (int)circle1.possible_rad*2);
            g.drawOval(circle2.x-(int)circle2.possible_rad, circle2.y-(int)circle2.possible_rad, (int)circle2.possible_rad*2, (int)circle2.possible_rad*2);
        }
    }

    private Point2D screenToWorld(Point screenPoint) {
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        return new Point2D.Double(
                (screenPoint.x - centerX - translateX) / scale,
                (screenPoint.y - centerY - translateY) / scale
        );
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (pointOnClick){
            double invertedX = (e.getX()/scale-(getWidth()/2+translateX) / scale);
            double invertedY = (e.getY()/scale-(getHeight()/2+translateY) / scale);
            points.add(new MyPoint((int) invertedX, (int) invertedY));
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastDragPoint = e.getPoint();
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
        if (!pointOnClick) {
            int dx = e.getX() - lastDragPoint.x;
            int dy = e.getY() - lastDragPoint.y;
            translateX += dx;
            translateY += dy;
            lastDragPoint = e.getPoint();


            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
