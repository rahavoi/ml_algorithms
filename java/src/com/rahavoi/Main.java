package com.rahavoi;

import com.rahavoi.ml.GradientDescent;

import java.text.MessageFormat;

public class Main {
    private static final double TOLERANCE = 1E-11;
    private static final double[][] TDATA = {
            {1, 200, 20000},
            {1, 300, 41000},
            {1, 900, 141000},
            {1, 800, 41000},
            {1, 400, 51000},
            {1, 500, 61500}
    };
    
    public static void main(String[] args){


        double errorTolerance = 0.000001;
        double learningRate = 0.000001;

        GradientDescent gradientDescent = new GradientDescent(errorTolerance, learningRate, 0, 0);
        gradientDescent.train(TDATA);

        //TODO:
        //System.out.println(MessageFormat.format("Final Theta 0: {0}", gradientDescent.theta0));
        //System.out.println(MessageFormat.format("Final Theta 1: {0}", gradientDescent.theta1));

        double footage = 400;
        double[] input = {1, footage};

        System.out.println(MessageFormat.format("Prediction for footage {0}: {1}",
                footage, gradientDescent.predict(input)));

    }
}
