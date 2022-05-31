# Stock-Price-Predictor
### An application to teach and test a neural network designed to predict stock market prices.
### About the application:
- A desktop application created in java language in MVC model using JavaFX and SceneBuilder technologies
- The OpenCSV library was used to read data into the dataframe
- The neural network, learning method, data normalization, and all topics related to machine learning were created from scratch in java, which allowed us for a better understanding of how neural networks work, learn, etc.
### Some screens of the GUI:
User Interface as seen before any interactions
![image](https://user-images.githubusercontent.com/93645494/170872476-d0188081-f83f-4935-9e7d-8ac0262a6855.png)

User Interface after the training and testing process - Chart Tab
![image](https://user-images.githubusercontent.com/93645494/170872540-56570630-cfb3-41b2-991e-0e272022efa6.png)

User Interface after the training and testing process - Table Tab
![image](https://user-images.githubusercontent.com/93645494/170872737-73415fd7-1ea5-45a2-a02c-fc42359a0a78.png)

### Implementation of machine learning in the program
The neural network we created is a perceptron that has three layers:
 -an input layer with five neurons,
 -a hidden layer with three neurons,
 -an output layer with one neuron.
 
The input layer neurons represent values associated with stock market prices on a given day:
 -opening ratio
 -closing ratio
 -lowest stake
 -highest stake
 -volume

#### Neural network learning method
In the program, to teach the network we implemented the method of backpropagation of error.
It's one of the basic methods of supervised learning based on correcting the weights of connections between neurons in order to obtain the smallest possible final error on the output layer
