// Импорт необходимых библиотек
import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    // Поля класса
    Axes Ox, Oy; // Оси
    ArrayList<MyPoint> points = new ArrayList<>(); // Массив точек
    boolean pointOnClick = false; // Режим курсора

    // Итоговые окружности
    MyPoint circle1 = null;
    MyPoint circle2 = null;

    // Переменные, связанные с масштабированием
    double scale = 1.0;
    double translateX = 0; // Смещение по OX, нужно для перемещения холста
    double translateY = 0; // Смещение по OY, нужно для перемещения холста
    Point lastDragPoint; // Последняя сдвинутая точка курсора

    // Переменные, связанные с цветами
    Color pointColor = Color.RED;
    Color circleColor = Color.RED;

    Canvas() {

        // Добавляем все слушатели событий
        addMouseListener(this);
        addMouseMotionListener(this);

        addMouseWheelListener(we -> {
            double zoomFactor = we.getWheelRotation() < 0 ? 1.1 : 0.9; // Уменьшение или увеличение
            Point mousePos = we.getPoint(); // Точка позиции курсора

            Point2D worldPosBefore = screenToWorld(mousePos); // Позиция курсора до зума

            // Границы зума
            if (scale < 10 && scale > 0.01) {
                scale *= zoomFactor;
            } else if ((scale >= 10 && zoomFactor == 0.9) || (scale <= 0.01 && zoomFactor == 1.1)) {
                scale *= zoomFactor;
            }

            Point2D worldPosAfter = screenToWorld(mousePos); // позиция курсора после зума

            // Насторйка смещений под корректный зум
            translateX += (worldPosAfter.getX() - worldPosBefore.getX()) * scale;
            translateY += (worldPosAfter.getY() - worldPosBefore.getY()) * scale;

            repaint();
        });
    }

    private Point2D screenToWorld(Point2D screenPoint) {

        // Перевод координат из экранных в привычные
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        return new Point2D.Double(
                (screenPoint.getX() - centerX - translateX) / scale,
                (screenPoint.getY() - centerY - translateY) / scale
        );
    }

    private Point worldToScreen(Point2D worldPoint) {

        // Перевод координат в экранные
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        return new Point(
                (int)(worldPoint.getX() * scale + centerX + translateX),
                (int)(worldPoint.getY() * scale + centerY + translateY)
        );
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform originalTransform = g2d.getTransform(); // Сохраняем все текущие сдвиги для дальнейшей отрисовки точек, размер которых не зависит от зума и сдвига

        // Настройка смещения и зума холста
        g2d.translate(getWidth()/2, getHeight()/2); // центрирование координат
        g2d.translate(translateX, translateY); // Сдвиги холста мышкой
        g2d.scale(scale, scale); // Зум

        // Отрисовка осей координат
        Ox = new Axes((int)((-getWidth()-translateX)/scale), 0, (int)((getWidth()-translateX)/scale), 0);
        Oy = new Axes(0, (int)((-getHeight()-translateY)/scale), 0, (int)((getHeight()-translateY)/scale));

        g.setColor(Color.BLACK);
        g.drawLine(Oy.x1, Oy.y1, Oy.x2, Oy.y2);
        g.drawLine(Ox.x1, Ox.y1, Ox.x2, Ox.y2);


        // Отрисовка единичных отрезков
        for (int i = (int)((-getWidth()/2-translateX)/scale); i<(int)((getWidth()/2-translateX)/scale); i++) {
            if (i%40 == 0) {
                g.drawLine(i, 4, i, -4);
                g.drawString(Integer.toString(i/40), i, 15);
            }
        }

        for (int i = (int)((-getHeight()/2+translateY)/scale); i<(int)((getHeight()/2+translateY)/scale); i++) {
            if (i%40 == 0 && i != 0) {
                g.drawLine(4, -i, -4, -i);
                g.drawString(Integer.toString(i/40), 15, -i);
            }
        }

        // Подписи осей
        g.drawString("Y", -15, -(int)((getHeight()/2+translateY-15)/scale));
        g.drawString("X", (int)((getWidth()/2-translateX-15)/scale), -10);

        // Отрисовка окружностей
        g.setColor(circleColor);
        if (circle1 != null && circle2 != null) {
            g.drawOval((int) (circle1.x-circle1.possible_rad), (int) (circle1.y-circle1.possible_rad), (int)circle1.possible_rad*2, (int)circle1.possible_rad*2);
            g.drawOval((int) (circle2.x-circle2.possible_rad), (int) (circle2.y-circle2.possible_rad), (int)circle2.possible_rad*2, (int)circle2.possible_rad*2);
        }

        // Восстанавливаем оригинальные координаты для точек
        g2d.setTransform(originalTransform);

        // Отрисовка точек без масштабирования
        g2d.setColor(pointColor);
        for (MyPoint point : points) {
            Point screenPos = worldToScreen(new Point2D.Double(point.x, point.y));
            g2d.fillOval(screenPos.x - 3, screenPos.y - 3, 6, 6);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        // Добавление точек по клику мышки
        if (pointOnClick){
            double invertedX = (e.getX()/scale-(getWidth()/2+translateX) / scale);
            double invertedY = (e.getY()/scale-(getHeight()/2+translateY) / scale);

            points.add(new MyPoint(invertedX, invertedY));
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Запоминаем, в какой точке курсор был зажат
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

        // Настройка смещения
        if (!pointOnClick) {
            translateX += e.getX() - lastDragPoint.x; // Обновляем сдвиг по OX
            translateY += e.getY() - lastDragPoint.y; // Обновляем сдвиг по OY
            lastDragPoint = e.getPoint(); // Обновляем последнюю сдвинутую точку

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
