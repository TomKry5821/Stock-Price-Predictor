package pl.polsl.biai.model;

import java.util.Random;

public class Layer {
    public Neuron[] neurons;

    // Constructor for the hidden and output layer
    public Layer(int inputNeurons, int neuronsNumber) {
        this.neurons = new Neuron[neuronsNumber];

        double randomDouble;
        Random r = new Random();
        for (int i = 0; i < neuronsNumber; i++) {
            double[] weights = new double[inputNeurons];
            for (int j = 0; j < inputNeurons; j++) {

                randomDouble = r.nextDouble(Neuron.minWeightValue, Neuron.maxWeightValue);
                weights[j] = randomDouble;
            }
            randomDouble = r.nextDouble(Neuron.minWeightValue, Neuron.maxWeightValue);
            neurons[i] = new Neuron(weights, randomDouble);
        }
    }


    // Constructor for the input layer
    public Layer(double inputValues[]) {
        this.neurons = new Neuron[inputValues.length];
        for (int i = 0; i < inputValues.length; i++) {
            this.neurons[i] = new Neuron(inputValues[i]);
        }
    }
}
