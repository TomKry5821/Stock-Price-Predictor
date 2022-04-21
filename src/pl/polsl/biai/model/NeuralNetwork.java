package pl.polsl.biai.model;

import pl.polsl.biai.activationfunction.Sigmoid;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements Backpropagation {

    List<Layer> layers = new ArrayList<>();
    private Boolean isInputLayerSet = false;
    private Boolean isOutputLayerSet = false;
    private Integer epochs;

    public void setEpochs(Integer epochs) {
        this.epochs = epochs;
    }

    public void setInputLayer(int neuronsNumber) {
        List<Double> inputNeurons = new ArrayList<>();
        for (int i = 0; i < neuronsNumber; i++) {
            inputNeurons.add(0.0);
        }
        this.setInputLayer(inputNeurons);
    }

    private void setInputLayer(List<Double> inputValues) {
        if (inputValues.size() < 1) {
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
        int inputNeurons = this.layers.get(this.layers.size() - 1).neurons.size();
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
        int inputNeurons = this.layers.get(this.layers.size() - 1).neurons.size();
        Layer outputLayer = new Layer(inputNeurons, neuronsNumber);

        this.layers.add(outputLayer);
        this.isOutputLayerSet = true;
    }

    public void printLayers() {
        for (Layer layer : this.layers) {
            System.out.println("layer");
            for (Neuron neuron : layer.neurons) {
                if (neuron.weights == null) {
                    System.out.println("Neuron " + neuron.value);
                } else {
                    System.out.println("Neuron " + neuron.value + " weight " + neuron.weights.toString());
                }
            }
        }
    }

    // This function is used to train being forward and backward.
    public void train(double learningRate, DataFrame dataFrame) {
        for (int i = 0; i < this.epochs; i++) {
            if (i == dataFrame.getRows().size() - 1) {
                System.out.println("Network trained!");
                return;
            }
            forward(dataFrame.getRows().get(i));
            backward(learningRate, dataFrame.getExpectedOutputs().get(i));
        }
    }

    public void forward(Row row) {
        this.layers.set(0, new Layer(row.getInput()));
        for (int i = 1; i < layers.size(); i++) {
            for (int j = 0; j < layers.get(i).neurons.size(); j++) {
                double sum = 0;
                for (int k = 0; k < layers.get(i - 1).neurons.size(); k++) {
                    sum += layers.get(i - 1).neurons.get(k).value * layers.get(i).neurons.get(j).weights.get(k);
                }
                //sum += layers[i].neurons[j].bias; // TODO add in the bias
                layers.get(i).neurons.get(j).value = Sigmoid.execute(sum);
            }
        }
    }

    public void backward(double learningRate, double expectedOutput) {

        int layersNumber = layers.size();
        int outIndex = layersNumber - 1;

        // Update the output layers
        // For each output
        for (int i = 0; i < layers.get(outIndex).neurons.size(); i++) {
            // and for each of their weights
            double output = layers.get(outIndex).neurons.get(i).value;
            double target = expectedOutput;
             System.out.println("Target - " + target);
             System.out.println("Calculated - " + output);
            // System.out.println("Error - " + calculateSquaredError(output, target));
            double derivative = output - target;
            double delta = derivative * (output * (1 - output));
            layers.get(outIndex).neurons.get(i).gradient = delta;
            for (int j = 0; j < layers.get(outIndex).neurons.get(i).weights.size(); j++) {
                double previousOutput = layers.get(outIndex - 1).neurons.get(j).value;
                double error = delta * previousOutput;
                layers.get(outIndex).neurons.get(i).cache_weights.set(j, layers.get(outIndex).neurons.get(i).weights.get(j) - learningRate * error);
            }
        }

        //Update all the subsequent hidden layers
        for (int i = outIndex - 1; i > 0; i--) {
            // For all neurons in that layers
            for (int j = 0; j < layers.get(i).neurons.size(); j++) {
                double output = layers.get(i).neurons.get(j).value;
                double gradientSum = sumGradient(j, layers.get(i + 1));
                double delta = (gradientSum) * (output * (1 - output));
                layers.get(i).neurons.get(j).gradient = delta;
                // And for all their weights
                for (int k = 0; k < layers.get(i).neurons.get(j).weights.size(); k++) {
                    double previousOutput = layers.get(i - 1).neurons.get(k).value;
                    double error = delta * previousOutput;
                    layers.get(i).neurons.get(j).cache_weights.set(k, layers.get(i).neurons.get(j).weights.get(k) - learningRate * error);
                }
            }
        }
        // Here we do another pass where we update all the weights
        for (int i = 0; i < layers.size(); i++) {
            for (int j = 0; j < layers.get(i).neurons.size(); j++) {
                layers.get(i).neurons.get(j).updateWeight();
            }
        }

    }

    public void test(List<Row> rows, List<Double> outputs) {
        for (int i = 0; i < rows.size(); i++) {
            forward(rows.get(i));
            System.out.println("Calculated - " + layers.get(2).neurons.get(0).value);
            System.out.println("Target - " + outputs.get(i));
        }
    }
}
