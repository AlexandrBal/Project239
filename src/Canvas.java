import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    int w = 700;
    int h = 700;
    MyPoint centerPoint = null;
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
            double delta = 0.1;
            if (we.getWheelRotation() < 0) {
                // Приближение (колесико вверх)
                scale *= 1.1;
            } else {
                // Отдаление (колесико вниз)
                scale /= 1.1;
            }
            repaint();
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if (centerPoint == null) {
            centerPoint = new MyPoint(getWidth()/2, getHeight()/2);
        }

        g2d.translate(translateX, translateY);
        System.out.println(scale);
        g2d.scale(scale, scale);


        Ox = new Axes((int) (-(double)translateX/scale), centerPoint.y, (int)((getWidth()-translateX)/scale), centerPoint.y);
        Oy = new Axes(centerPoint.x, (int)(-(double)translateY/scale), centerPoint.x, (int)((getHeight()-translateY)/scale));

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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (pointOnClick){
            System.out.println(e.getX() + " " + e.getY());
            double invertedX = (e.getX() - translateX) / scale;
            double invertedY = (e.getY() - translateY) / scale;
            points.add(new MyPoint((int) invertedX, (int) invertedY));
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressX = e.getX();
        pressY = e.getY();
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

//            for (MyPoint point : points) {
//                point.x += (e.getX() - pressX);
//                point.y += (e.getY() - pressY);
//            }

//            centerPoint.x += (e.getX() - pressX);
//            centerPoint.y += (e.getY() - pressY);

//            Ox.y1 += (e.getY() - pressY);
//            Ox.y2 += (e.getY() - pressY);
//            Oy.x1 += (e.getX() - pressX);
//            Oy.x2 += (e.getX() - pressX);

            pressX = e.getX();
            pressY = e.getY();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
