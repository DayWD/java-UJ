package uj.pwj2020.introduction;

public class QuadraticEquation {

    public double[] findRoots(double a, double b, double c) {
        double delta = b * b - 4.0 * a * c;
        if (delta > 0.0) {
            double[] x = new double[2];
            x[0] = (-b + Math.pow(delta, 0.5)) / (2.0 * a);
            x[1] = (-b - Math.pow(delta, 0.5)) / (2.0 * a);
            return x;
        } else if (delta == 0.0) {
            return new double[]{-b / (2.0 * a)};
        } else {
            return new double[0];
        }
    }
}

