package pl.polsl.biai.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Layer {
    public List<Neuron> neurons;

    // Constructor for the hidden and output layer
    public Layer(int inputNeurons, int neuronsNumber) {
        this.neurons = new ArrayList<>(neuronsNumber);

        double randomDouble;
        Random r = new Random();
        for (int i = 0; i < neuronsNumber; i++) {
            List<Double> weights = new ArrayList<Double>(inputNeurons);
            for (int j = 0; j < inputNeurons; j++) {

                randomDouble = r.nextDouble(Neuron.minWeightValue, Neuron.maxWeightValue);
                weights.add(randomDouble);
            }
            randomDouble = r.nextDouble(Neuron.minWeightValue, Neuron.maxWeightValue);
            neurons.add(new Neuron(weights, randomDouble));
        }
    }


    // Constructor for the input layer
    public Layer(List<Double> inputValues) {
        this.neurons = new ArrayList<Neuron>(inputValues.size());
        for (int i = 0; i < inputValues.size(); i++) {
            this.neurons.add(new Neuron(inputValues.get(i)));
        }
    }
}
