package pl.polsl.biai.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Layer {
    public List<Neuron> neurons;

    /**
     * Constructor for hidden and output layers
     *
     * @param inputNeurons  input neurons in layer
     * @param neuronsNumber number of neurons in layer
     */
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


    /**
     * Constructor for input layer
     *
     * @param inputValues list of input values
     */
    public Layer(List<Double> inputValues) {
        this.neurons = new ArrayList<>(inputValues.size());
        for (Double inputValue : inputValues) {
            this.neurons.add(new Neuron(inputValue));
        }
    }
}
