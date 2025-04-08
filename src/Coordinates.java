import javax.swing.*;
import java.awt.*;

public class Coordinates extends JPanel {
    private static final double SCALE_STEP = 0.1;
    private double scale = 1.0;
    private Point centerPoint;
    double translateX = 0; // Смещение по X
    double translateY = 0;
    private Point lastDragPoint;
    // Преобразование экранных координат в логические
    MyPoint screenToLogic(int x, int y) {
        int dx = x - getWidth()/2 - (int)translateX;
        int dy = y - getHeight()/2 - (int)translateY;
        return new MyPoint(
                (int)(dx / scale),
                (int)(-dy / scale) // Инвертируем Y для математической системы координат
        );
    }

    // Преобразование логических координат в экранные
    MyPoint logicToScreen(int x, int y) {
        return new MyPoint(
                (int)(x * scale) + getWidth()/2 + (int)translateX,
                (int)(-y * scale) + getHeight()/2 + (int)translateY
        );
    }
}
