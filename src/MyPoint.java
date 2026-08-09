public class MyPoint {
    double x;
    double y;
    double possible_rad; // Для удобства устанавливаем радиус, ели точка станетцентром одной из окружностей

    MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
        this.possible_rad = 0.0;
    }
}
