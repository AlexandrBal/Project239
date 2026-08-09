import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<MyPoint> solution(ArrayList<MyPoint> points) {
        if (points.isEmpty()) {
            return null;
        }

        int n = points.size(); // Для удобства выносим размер массива в отделбную переменную

        double minMaxRadius = Double.POSITIVE_INFINITY; // Минимальный максимальный радиус
        int bestI = -1; // Индекс первой окружности
        int bestJ = -1; // Индекс вотрой окружности
        double bestR1 = 0; // Радиус первой окружности
        double bestR2 = 0; // Радиус второй окружности

        // Перебором проходимся по всем парам точек и ставниваем расстояния от других до каждой из них
        // К какой ближе, в "область" той она и входит
        for (int i = 0; i < n; i++) {
            MyPoint c1 = points.get(i); // Определяем первую окружность
            for (int j = 0; j < n; j++) {
                MyPoint c2 = points.get(j); // Определяем вторую окружность

                double maxSq1 = 0; // Наибольшее расстояние до первой точки
                double maxSq2 = 0; // Наибольшее расстояние до второй точки

                for (MyPoint p : points) {
                    double sq1 = Math.pow(p.x - c1.x, 2) + Math.pow(p.y - c1.y, 2); // Расстояние до первой точки

                    double sq2 = Math.pow(p.x - c2.x, 2) + Math.pow(p.y - c2.y, 2); // Расстояние до второй точки

                    // Переподсчёт наибольшео расстояния
                    if (sq1 <= sq2) {
                        if (sq1 > maxSq1) {
                            maxSq1 = sq1;
                        }
                    } else {
                        if (sq2 > maxSq2) {
                            maxSq2 = sq2;
                        }
                    }
                }

                // Ищем наибольшее из 2-ух текущих расстояний
                double r1 = Math.sqrt(maxSq1); // Радиус первой окружности
                double r2 = Math.sqrt(maxSq2); // Радиус второй окружности
                double currentMax = Math.max(r1, r2); // Наибольший из двух радиусов

                // Обновляем значения
                if (currentMax < minMaxRadius) {
                    minMaxRadius = currentMax;
                    bestI = i;
                    bestJ = j;
                    bestR1 = r1;
                    bestR2 = r2;
                }
            }
        }

        // Если ничего не нашли
        if (bestI == -1 || bestJ == -1) {
            return null;
        }

        // Выбираем точки из списка с нужными индексами
        MyPoint center1 = points.get(bestI);
        MyPoint center2 = points.get(bestJ);

        // Устанавливаем значения радиусов для точек - центров окружностей
        center1.possible_rad = bestR1;
        center2.possible_rad = bestR2;

        return List.of(center1, center2);
    }
}
