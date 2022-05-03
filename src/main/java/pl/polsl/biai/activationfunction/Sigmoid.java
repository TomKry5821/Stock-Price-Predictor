package pl.polsl.biai.activationfunction;

public class Sigmoid extends ActivationFunction {

    public static double execute(double x) {
        return (double) (1 / (1 + Math.pow(Math.E, -x)));
    }

    public static double derivative(double x) {
        return Sigmoid.execute(x) * (1 - Sigmoid.execute(x));
    }
}
