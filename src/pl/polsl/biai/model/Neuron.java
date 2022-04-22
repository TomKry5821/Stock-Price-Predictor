package pl.polsl.biai.model;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    static final double minWeightValue = -1;
    static final double maxWeightValue = 1;

    List<Double> weights;
    List<Double> cache_weights;
    double gradient;
    double bias;
    double value = 0;

    /**
     * Constructor for hidden/output neurons
     *
     * @param weights weights of the neuron
     * @param bias    neuron bias
     */
    public Neuron(List<Double> weights, double bias) {
        this.weights = weights;
        this.bias = bias;
        this.cache_weights = this.weights;
        this.gradient = 0;
    }

    /**
     * Constructor for input neurons
     *
     * @param value of the neuron
     */
    public Neuron(double value) {
        this.weights = null;
        this.bias = -1;
        this.cache_weights = null;
        this.gradient = -1;
        this.value = value;
    }

    /**
     * Method used to update weights after the backpropagation iteration
     */
    public void updateWeight() {
        this.weights = this.cache_weights;
    }
}
