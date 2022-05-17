package pl.polsl.biai.learningmethod;

import pl.polsl.biai.model.Layer;
import pl.polsl.biai.model.Neuron;

import java.util.ArrayList;

public interface Backpropagation {

    /**
     * Method used to calculate squared error
     *
     * @param output       value
     * @param targetOutput target value
     * @return result squared error
     */
    default double calculateSquaredError(double output, double targetOutput) {
        return 0.5 * Math.pow(2, (targetOutput - output));
    }

    /**
     * Method used to calculate sum of squared errors(Not used because in our case there is just 1 output neuron)
     *
     * @param outputs       list of values
     * @param targetOutputs list of target values
     * @return result sum of squared errors
     */
    default double sumSquaredError(ArrayList<Double> outputs, ArrayList<Double> targetOutputs) {
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
        for (int i = 0; i < currentLayer.getNeurons().size(); i++) {
            Neuron currentNeuron = currentLayer.getNeurons().get(i);
            gradientSum += currentNeuron.getWeights().get(nIndex) * currentNeuron.getGradient();
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
