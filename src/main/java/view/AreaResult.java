package view;

import model.Dot;

public class AreaResult {

    public AreaResult() {
    }

    public static boolean isItInArea(Dot dot) {
        try {
            return isItInArea(dot.getX(), dot.getY(), dot.getR());
        } catch (NullPointerException e) {
            return false;
        }
    }


    public static boolean isItInArea(double x, double y, double r) {
        return ((inSecondQuad(x, y, r) || inFirstQuad(x, y, r) ||
                inThirdQuad(x, y, r) || inFourthQuad(x, y, r)))
                && r >= 0;
    }


    private static boolean inSecondQuad(double x, double y, double r) {
        return false;
    }

    private static boolean inFourthQuad(double x, double y, double r) {
        return x >= 0 &&
                y <= 0 &&
                x <= r &&
                y >= -r;
    }

    private static boolean inThirdQuad(double x, double y, double r) {
        return  x <= 0 &&
                y <= 0 &&
                y >= -r &&
                x >= -r / 2 &&
                y >= -2 * x - r;
    }

    private static boolean inFirstQuad(double x, double y, double r) {
        return  x >= 0 &&
                y >= 0 &&
                x * x + y * y <= r * r / 4;
    }
}