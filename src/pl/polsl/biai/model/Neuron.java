package pl.polsl.biai.model;

public class Neuron {

    static final double minWeightValue = -1;
    static final double maxWeightValue = 1;

    double[] weights;
    double[] cache_weights;
    double gradient;
    double bias;
    double value = 0;

    // Constructor for the hidden / output neurons
    public Neuron(double[] weights, double bias) {
        this.weights = weights;
        this.bias = bias;
        this.cache_weights = this.weights;
        this.gradient = 0;
    }

    // Constructor for the input neurons
    public Neuron(double value) {
        this.weights = null;
        this.bias = -1;
        this.cache_weights = this.weights;
        this.gradient = -1;
        this.value = value;
    }

    // Function used at the end of the backprop to switch the calculated value in the
    // cache weight in the weights
    public void update_weight() {
        this.weights = this.cache_weights;
    }
}
