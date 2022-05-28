package pl.polsl.biai.model;

import java.util.ArrayList;

public class Neuron {

    private ArrayList<Double> weights;
    private final ArrayList<Double> cacheWeights;
    private double gradient;
    private double value;

    /**
     * Constructor for hidden/output neurons
     *
     * @param weights weights of the neuron
     */
    public Neuron(ArrayList<Double> weights) {
        this.weights = weights;
        this.cacheWeights = this.weights;
        this.gradient = 0;
        this.value = 0;
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

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public ArrayList<Double> getCacheWeights() {
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
