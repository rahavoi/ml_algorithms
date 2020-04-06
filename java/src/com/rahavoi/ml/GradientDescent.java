package com.rahavoi.ml;

import java.text.MessageFormat;
import java.util.Arrays;

public class GradientDescent {
    private double errorTolerance;
    private double learningRate;
    private double[] thetas;

    public GradientDescent(double errorTolerance, double learningRate, double ... thetas) {
        this.errorTolerance = errorTolerance;
        this.learningRate = learningRate;
        this.thetas = thetas;
    }

    public void train(double[][] data){
        int iterations = 0;
        double[] deltas = new double[thetas.length];

        do {
            iterations++;

            double[] newThetas = new double[thetas.length];

            for(int i = 0; i < thetas.length; i++){
                newThetas[i] = thetas[i] -  learningRate *  (1.0 / data.length * getErrorSum(data, i));
                deltas[i] = thetas[i] - newThetas[i];
            }

            thetas = newThetas;

            if(iterations % 10000 == 0){
                System.out.println(MessageFormat.format("Training Cycle: {0}", iterations));
            }

        } while (Arrays.stream(deltas).map(Math::abs).sum() > errorTolerance);

        System.out.println(MessageFormat.format("Done in {0} cycles", iterations));
    }

    //Override this method with your own hypothesis impl.
    public double predict(double[] x) {
        return thetas[0] * x[0] + thetas[1] * x[1];
    }

    private double getErrorSum(double[][] data, double factor){
        double error = 0;

        for(int i = 0; i < data.length; i++){
            error += (predict(data[i]) - data[i][data[i].length - 1]) * factor;
        }

        return error;
    }

    private void printThetas(){
        for(int i = 0; i < thetas.length; i++){
            System.out.println(MessageFormat.format("Theta{0}: {1}", i, thetas[i]));
        }
    }
}