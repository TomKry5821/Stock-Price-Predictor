package pl.polsl.biai.models;

public class Sigmoid extends ActivationFunction {

    public static double execute(double x) {
        return 1 / (1 + Math.pow(Math.E, -x));
    }

    public static double derivative(double x) {
        return Sigmoid.execute(x) * (1 - Sigmoid.execute(x));
    }
}
