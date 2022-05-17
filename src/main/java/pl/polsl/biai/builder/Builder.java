package pl.polsl.biai.builder;

public interface Builder {

    NeuralNetworkBuilder buildInputLayer(int neuronsNumber);

    NeuralNetworkBuilder buildHiddenLayer(int neuronsNumber);

    NeuralNetworkBuilder buildOutputLayer(int neuronsNumber);

}
