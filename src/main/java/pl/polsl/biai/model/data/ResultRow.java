package pl.polsl.biai.model.data;

import pl.polsl.biai.learningmethod.Backpropagation;

import java.text.DecimalFormat;

public class ResultRow {
    private String date;
    private double calculated;
    private double target;
    private double error;

    public ResultRow(String date, double calculated, double target) {
        if(date.length()>11){
            this.date = date.substring(0,11);
        }else{
            this.date = date;
        }
        this.calculated = calculated;
        this.target = target;
        this.error = Backpropagation.calculateSquaredError(calculated, target);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCalculated() {
        return calculated;
    }

    public void setCalculated(double calculated) {
        this.calculated = calculated;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public double getError() {
        return error;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00000000");
        return date + ", " + df.format(calculated) + ", " + df.format(target);
    }
}
