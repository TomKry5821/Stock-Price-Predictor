package pl.polsl.biai.model;

import java.util.List;

public interface Backpropagation {

    /**
     * Method used to calculate squared error
     *
     * @param output       value
     * @param targetOutput target value
     * @return result squared error
     */
    default double calculateSquaredError(double output, double targetOutput) {
        return (double) (0.5 * Math.pow(2, (targetOutput - output)));
    }

    /**
     * Method used to calculate sum of squared errors
     *
     * @param outputs       list of values
     * @param targetOutputs list of target values
     * @return result sum of squared errors
     */
    default double sumSquaredError(List<Double> outputs, List<Double> targetOutputs) {
        double errorsSum = 0;
        for (int i = 0; i < outputs.size(); i++) {
            errorsSum += calculateSquaredError(outputs.get(i), targetOutputs.get(i));
        }
        return errorsSum;
    }

    /**
     * Method sums up all the gradient connecting a given neuron in a given layer
     *
     * @param nIndex       weight index
     * @param currentLayer current layers in which gradient is calculated
     * @return result sum gradient
     */
    default double sumGradient(int nIndex, Layer currentLayer) {
        double gradientSum = 0.0;
        for (int i = 0; i < currentLayer.neurons.size(); i++) {
            Neuron currentNeuron = currentLayer.neurons.get(i);
            gradientSum += currentNeuron.weights.get(nIndex) * currentNeuron.gradient;
        }
        return gradientSum;
    }

    /**
     * Method is used to change connections weights in our neural network based on error between targer and calculated output
     *
     * @param learningRate   learning rate used in the backpropagation method
     * @param expectedOutput target output
     */
    void backward(double learningRate, double expectedOutput);

}
