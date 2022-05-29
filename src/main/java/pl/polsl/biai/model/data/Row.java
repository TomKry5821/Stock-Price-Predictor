package pl.polsl.biai.model.data;

import com.opencsv.bean.CsvBindByName;

import java.util.ArrayList;

public class Row {

    @CsvBindByName(column = "date", required = true)
    private String date;
    @CsvBindByName(column = "open", required = true)
    private double openRate;
    @CsvBindByName(column = "close", required = true)
    private double closeRate;
    @CsvBindByName(column = "high", required = true)
    private double highRate;
    @CsvBindByName(column = "low", required = true)
    private double lowRate;
    @CsvBindByName(column = "volume", required = true)
    private int volume;
    private double normalizedVolume;

    public Row() {
        this.date = "";
        this.openRate = 0.0;
        this.closeRate = 0.0;
        this.highRate = 0.0;
        this.lowRate = 0.0;
        this.volume = 0;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getOpenRate() {
        return openRate;
    }

    public void setOpenRate(double openRate) {
        this.openRate = openRate;
    }

    public double getCloseRate() {
        return closeRate;
    }

    public void setCloseRate(double closeRate) {
        this.closeRate = closeRate;
    }

    public double getHighRate() {
        return highRate;
    }

    public void setHighRate(double highRate) {
        this.highRate = highRate;
    }

    public double getLowRate() {
        return lowRate;
    }

    public void setLowRate(double lowRate) {
        this.lowRate = lowRate;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getNormalizedVolume() {
        return normalizedVolume;
    }

    public void setNormalizedVolume(double normalizedVolume) {
        this.normalizedVolume = normalizedVolume;
    }


    public ArrayList<Double> getInput() {
        ArrayList<Double> input = new ArrayList<>();
        input.add(this.openRate);
        input.add(this.closeRate);
        input.add(this.highRate);
        input.add(this.normalizedVolume);
        input.add(this.lowRate);
        return input;
    }

    @Override
    public String toString() {
        return "Row{" +
                "date='" + date + '\'' +
                ", openRate=" + openRate +
                ", closeRate=" + closeRate +
                ", highRate=" + highRate +
                ", lowRate=" + lowRate +
                ", volume=" + volume +
                ", normalizedVolume=" + normalizedVolume +
                '}';
    }
}
