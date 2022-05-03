package pl.polsl.biai.model;

import java.util.List;

public class Neuron {

    private List<Double> weights;
    private List<Double> cacheWeights;
    private double gradient;
    private double value = 0;

    /**
     * Constructor for hidden/output neurons
     *
     * @param weights weights of the neuron
     */
    public Neuron(List<Double> weights) {
        this.weights = weights;
        this.cacheWeights = this.weights;
        this.gradient = 0;
    }

    /**
     * Constructor for input neurons
     *
     * @param value of the neuron
     */
    public Neuron(double value) {
        this.weights = null;
        this.cacheWeights = null;
        this.gradient = -1;
        this.value = value;
    }

    public List<Double> getWeights() {
        return weights;
    }


    public List<Double> getCacheWeights() {
        return cacheWeights;
    }

    public double getGradient() {
        return gradient;
    }

    public void setGradient(double gradient) {
        this.gradient = gradient;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Method used to update weights after the backpropagation iteration
     */
    public void updateWeight() {
        this.weights = this.cacheWeights;
    }

}
