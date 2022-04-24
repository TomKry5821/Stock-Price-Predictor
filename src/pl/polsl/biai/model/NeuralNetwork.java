package pl.polsl.biai.model;

import pl.polsl.biai.activationfunction.Sigmoid;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements Backpropagation {

    private List<Layer> layers = new ArrayList<>();
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
        int inputNeurons = this.layers.get(this.layers.size() - 1).getNeurons().size();
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
        int inputNeurons = this.layers.get(this.layers.size() - 1).getNeurons().size();
        Layer outputLayer = new Layer(inputNeurons, neuronsNumber);

        this.layers.add(outputLayer);
        this.isOutputLayerSet = true;
    }

    /**
     * Method prints layers with neurons and connections weights
     */
    public void printLayers() {
        for (Layer layer : this.layers) {
            System.out.println("layer");
            for (Neuron neuron : layer.getNeurons()) {
                if (neuron.getWeights() == null) {
                    System.out.println("Neuron " + neuron.getValue());
                } else {
                    System.out.println("Neuron " + neuron.getValue() + " weight " + neuron.getCacheWeights().toString());
                }
            }
        }
    }

    /**
     * Method used to train network with forward and backward methods
     *
     * @param learningRate learning rate used in the backpropagation method
     * @param dataFrame    dataset from which network is trained
     */
    public void train(double learningRate, DataFrame dataFrame) {
        for (int i = 0; i < this.epochs; i++) {
            for (int j = 0; j < dataFrame.getRows().size(); j++) {
                forward(dataFrame.getRows().get(j));
                backward(learningRate, dataFrame.getExpectedOutputs().get(j));
            }
        }
        System.out.println("Network trained!");
    }

    /**
     * Method that is used to calculate prediction of our neural network
     *
     * @param row actual record in dataset
     */
    public void forward(Row row) {
        this.layers.set(0, new Layer(row.getInput()));
        for (int i = 1; i < layers.size(); i++) {
            for (int j = 0; j < layers.get(i).getNeurons().size(); j++) {
                double sum = 0;
                for (int k = 0; k < layers.get(i - 1).getNeurons().size(); k++) {
                    sum += layers.get(i - 1).getNeurons().get(k).getValue() * layers.get(i).getNeurons().get(j).getWeights().get(k);
                }
                //sum += layers[i].neurons[j].bias; // TODO add in the bias
                layers.get(i).getNeurons().get(j).setValue(Sigmoid.execute(sum));
            }
        }
    }

    public void backward(double learningRate, double expectedOutput) {
        int layersNumber = layers.size();
        int lastLayerIndex = layersNumber - 1;

        updateOutputLayers(learningRate, expectedOutput, lastLayerIndex);
        //Update all the subsequent hidden layers
        updateSubsequentLayers(learningRate, lastLayerIndex);
        // Here we do another pass where we update all the weights
        updateWeights();

    }

    /**
     * Method that updates weights in all layers
     */
    private void updateWeights() {
        for (Layer layer : layers) {
            for (int j = 0; j < layer.getNeurons().size(); j++) {
                layer.getNeurons().get(j).updateWeight();
            }
        }
    }

    /**
     * Method that updates all subsequent hidden layers
     * @param learningRate learning rate used in backpropagation
     * @param lastLayerIndex last layer index
     */
    private void updateSubsequentLayers(double learningRate, int lastLayerIndex) {
        for (int i = lastLayerIndex - 1; i > 0; i--) {
            // For all neurons in that layers
            for (int j = 0; j < layers.get(i).getNeurons().size(); j++) {
                double output = layers.get(i).getNeurons().get(j).getValue();
                double gradientSum = sumGradient(j, layers.get(i + 1));
                double delta = (gradientSum) * (output * (1 - output));
                layers.get(i).getNeurons().get(j).setGradient(delta);
                // And for all their weights
                for (int k = 0; k < layers.get(i).getNeurons().get(j).getWeights().size(); k++) {
                    double previousOutput = layers.get(i - 1).getNeurons().get(k).getValue();
                    double error = delta * previousOutput;
                    layers.get(i).getNeurons().get(j).getCacheWeights().set(k, layers.get(i).getNeurons().get(j).getWeights().get(k) - learningRate * error);
                }
            }
        }
    }

    /**
     * Method that updates all output neurons in output layer
     * @param learningRate learning rate used in backpropagation
     * @param expectedOutput target output
     * @param lastLayerIndex index of the last layer
     */
    private void updateOutputLayers(double learningRate, double expectedOutput, int lastLayerIndex) {
        for (int i = 0; i < layers.get(lastLayerIndex).getNeurons().size(); i++) {
            // and for each of their weights
            double output = layers.get(lastLayerIndex).getNeurons().get(i).getValue();
            double derivative = output - expectedOutput;
            double delta = derivative * (output * (1 - output));
            layers.get(lastLayerIndex).getNeurons().get(i).setGradient(delta);
            for (int j = 0; j < layers.get(lastLayerIndex).getNeurons().get(i).getWeights().size(); j++) {
                double previousOutput = layers.get(lastLayerIndex - 1).getNeurons().get(j).getValue();
                double error = delta * previousOutput;
                layers.get(lastLayerIndex).getNeurons().get(i).getCacheWeights().set(j, layers.get(lastLayerIndex).getNeurons().get(i).getWeights().get(j) - learningRate * error);
            }
        }
    }

    /**
     * Method is used to calculate predictions of neural network from test dataset
     *
     * @param rows    list of test records
     * @param outputs list of target outputs
     */
    public void test(List<Row> rows, List<Double> outputs) {
        for (int i = 0; i < rows.size(); i++) {
            forward(rows.get(i));
            System.out.println("Calculated - " + layers.get(layers.size() - 1).getNeurons().get(0).getValue());
            System.out.println("Target - " + outputs.get(i));
        }
    }
}
