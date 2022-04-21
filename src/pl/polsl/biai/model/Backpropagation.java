package pl.polsl.biai.model;

import java.util.List;

public interface Backpropagation {

    default double calculateSquaredError(double output, double targetOutput) {
        return (double) (0.5 * Math.pow(2, (targetOutput - output)));
    }

    default double sumSquaredError(List<Double> outputs, List<Double> targetOutputs) {
        double errorsSum = 0;
        for (int i = 0; i < outputs.size(); i++) {
            errorsSum += calculateSquaredError(outputs.get(i), targetOutputs.get(i));
        }
        return errorsSum;
    }

    // This function sums up all the gradient connecting a given neuron in a given layer
    default double sumGradient(int nIndex, Layer currentLayer) {
        double gradientSum = 0.0;
        for (int i = 0; i < currentLayer.neurons.size(); i++) {
            Neuron currentNeuron = currentLayer.neurons.get(i);
            gradientSum += currentNeuron.weights.get(nIndex) * currentNeuron.gradient;
        }
        return gradientSum;
    }

}
