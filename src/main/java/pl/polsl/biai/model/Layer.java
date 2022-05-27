package pl.polsl.biai.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Layer {
    private ArrayList<Neuron> neurons;

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
            ArrayList<Double> weights = new ArrayList<Double>(inputNeurons);
            for (int j = 0; j < inputNeurons; j++) {
                randomDouble = r.nextDouble(-1.0, 1.0);
                weights.add(randomDouble);
            }
            neurons.add(new Neuron(weights));
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

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(ArrayList<Neuron> neurons) {
        this.neurons = neurons;
    }
}
