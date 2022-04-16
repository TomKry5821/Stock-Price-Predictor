package pl.polsl.biai.model;

public interface Backpropagation {

    default double calculateSquaredError(double output, double targetOutput) {
        return (double) (0.5 * Math.pow(2, (targetOutput - output)));
    }

    default double sumSquaredError(double[] outputs, double[] targetOutputs) {
        double errorsSum = 0;
        for (int i = 0; i < outputs.length; i++) {
            errorsSum += calculateSquaredError(outputs[i], targetOutputs[i]);
        }
        return errorsSum;
    }
}
