import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<MyPoint> solution(ArrayList<MyPoint> points) {
        if (points.isEmpty()) {
            return null;
        }

        int n = points.size();
        double[][] dist = new double[n][n];


        double minMaxRadius = Double.POSITIVE_INFINITY;
        int bestI = -1;
        int bestJ = -1;
        double bestR1 = 0;
        double bestR2 = 0;

        for (int i = 0; i < n; i++) {
            MyPoint c1 = points.get(i);
            for (int j = 0; j < n; j++) {
                MyPoint c2 = points.get(j);

                double maxSq1 = 0;
                double maxSq2 = 0;

                for (MyPoint p : points) {
                    double dx1 = p.x - c1.x;
                    double dy1 = p.y - c1.y;
                    double sq1 = dx1 * dx1 + dy1 * dy1;

                    double dx2 = p.x - c2.x;
                    double dy2 = p.y - c2.y;
                    double sq2 = dx2 * dx2 + dy2 * dy2;

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

                double r1 = Math.sqrt(maxSq1);
                double r2 = Math.sqrt(maxSq2);
                double currentMax = Math.max(r1, r2);

                if (currentMax < minMaxRadius) {
                    minMaxRadius = currentMax;
                    bestI = i;
                    bestJ = j;
                    bestR1 = r1;
                    bestR2 = r2;
                }
            }
        }

        if (bestI == -1 || bestJ == -1) {
            return null;
        }

        MyPoint center1 = points.get(bestI);
        MyPoint center2 = points.get(bestJ);

        center1.possible_rad = bestR1;
        center2.possible_rad = bestR2;

        return List.of(center1, center2);
    }
}
