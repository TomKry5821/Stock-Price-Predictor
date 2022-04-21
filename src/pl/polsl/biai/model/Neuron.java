package pl.polsl.biai.model;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    static final double minWeightValue = -1;
    static final double maxWeightValue = 1;

    // list of weights that comes into neuron
    List<Double> weights = new ArrayList<>();
    List<Double> cache_weights = new ArrayList<>();
    double gradient;
    double bias;
    double value = 0;

    // Constructor for the hidden / output neurons
    public Neuron(List<Double> weights, double bias) {
        this.weights = weights;
        this.bias = bias;
        this.cache_weights = this.weights;
        this.gradient = 0;
    }

    // Constructor for the input neurons
    public Neuron(double value) {
        this.weights = null;
        this.bias = -1;
        this.cache_weights = null;
        this.gradient = -1;
        this.value = value;
    }

    // Function used at the end of the backprop to switch the calculated value in the
    // cache weight in the weights
    public void updateWeight() {
        this.weights = this.cache_weights;
    }
}
