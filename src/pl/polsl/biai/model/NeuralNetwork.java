package pl.polsl.biai.model;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements Backpropagation {

    List<Layer> layers = new ArrayList<>();
    private Boolean isInputLayerSet = false;
    private Boolean isOutputLayerSet = false;

    public void setInputLayer(double inputValues[]) {
        if (inputValues.length < 1) {
            System.err.println("Add at least one neuron!");
            return;
        }
        if (this.isInputLayerSet) {
            System.err.println("Input layer has been set earlier!");
            return;
        }
        Layer inputLayer = new Layer(inputValues);
        this.layers.add(inputLayer);
        this.isInputLayerSet = true;
    }

    public void setHiddenLayer(int neuronsNumber) {
        if (this.layers.isEmpty()) {
            System.err.println("You have not added input layer yet!");
            return;
        }
        // Taking neurons number from previous layer
        int inputNeurons = this.layers.get(this.layers.size() - 1).neurons.length;
        Layer hiddenLayer = new Layer(inputNeurons, neuronsNumber);

        this.layers.add(hiddenLayer);
    }

    public void setOutputLayer(int neuronsNumber) {
        if (this.layers.isEmpty()) {
            System.err.println("You have not added input layer yet!");
            return;
        }
        if (this.isOutputLayerSet) {
            System.err.println("Output layer has been set earlier!");
            return;
        }
        // Taking neurons number from previous layer
        int inputNeurons = this.layers.get(this.layers.size() - 1).neurons.length;
        Layer outputLayer = new Layer(inputNeurons, neuronsNumber);

        this.layers.add(outputLayer);
        this.isOutputLayerSet = true;
    }
}
