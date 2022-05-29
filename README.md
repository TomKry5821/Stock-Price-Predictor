# Stock-Price-Predictor
### An application to learn and test a neural network designed to predict stock market prices.
### About the application:
- It is a desktop application that was created in java language in MVC model using JavaFX and SceneBuilder technologies
- The OpenCSV library was used to read data into the dataframe
- The neural network, learning method, data normalization, and all things related to machine learning were created from scratch in java, which allowed us for a better understanding of how neural networks work and how networks learn, etc.
### Some screens of the GUI:
Empty GUI
![image](https://user-images.githubusercontent.com/93645494/170872476-d0188081-f83f-4935-9e7d-8ac0262a6855.png)

GUI after train and test
![image](https://user-images.githubusercontent.com/93645494/170872540-56570630-cfb3-41b2-991e-0e272022efa6.png)

GUI with table data
![image](https://user-images.githubusercontent.com/93645494/170872737-73415fd7-1ea5-45a2-a02c-fc42359a0a78.png)

### Implementation of machine learning in a program
In application has been implemented simple perceptron that consist of three layers:
- An input layer having 5 neurons that correspond sequentially values each day at stock market:
  - open rate
  - close rate
  - highest rate
  - lowest rate
  - volume
  - These values are normalized by the min-max method in the range of 0 - 1 before entering the network
- An hidden layer having 3 neurons(The number of neurons in this layer was being changed during testing)
- An Output layer having 1 neuron that corresponds value of predicted close rate in next day
#### Neural network learning method
In the program, to learn the network we implemented the method of back propagation of error, which is one of the basic methods of supervised learning based on correcting the weights of connections between neurons in order to obtain the smallest possible final error on the output layer

