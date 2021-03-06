package pl.polsl.biai.model.activationfunction;

public class Sigmoid extends ActivationFunction {

    public static double execute(double x) {
        return 1 / (1 + Math.pow(Math.E, -x));
    }

}
