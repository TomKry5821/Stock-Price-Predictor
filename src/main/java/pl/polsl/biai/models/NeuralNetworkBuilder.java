package pl.polsl.biai.models;

public class NeuralNetworkBuilder implements Builder {

    private NeuralNetwork neuralNetwork;

    public NeuralNetworkBuilder() {
        this.neuralNetwork = new NeuralNetwork();
    }

    @Override
    public NeuralNetworkBuilder buildInputLayer(int neuronsNumber) {
        this.neuralNetwork.setInputLayer(neuronsNumber);
        return this;
    }

    @Override
    public NeuralNetworkBuilder buildHiddenLayer(int neuronsNumber) {
        this.neuralNetwork.setHiddenLayer(neuronsNumber);
        return this;
    }

    @Override
    public NeuralNetworkBuilder buildOutputLayer(int neuronsNumber) {
        this.neuralNetwork.setOutputLayer(neuronsNumber);
        return this;
    }

    public NeuralNetwork build(){
        return this.neuralNetwork;
    }
}
