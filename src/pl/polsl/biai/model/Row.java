package pl.polsl.biai.model;

import com.opencsv.bean.CsvBindByName;

public class Row {
    @CsvBindByName(column = "open", required = true)
    private double openRate;
    @CsvBindByName(column = "close", required = true)
    private double dayBeforeCloseRate;
    @CsvBindByName(column = "high", required = true)
    private double highRate;
    @CsvBindByName(column = "low", required = true)
    private double lowRate;
    @CsvBindByName(column = "volume", required = true)
    private double volume;

    public Row() {
        this.openRate = 0.0;
        this.dayBeforeCloseRate = 0.0;
        this.highRate = 0.0;
        this.lowRate = 0.0;
        this.volume = 0.0;
    }

    public double getOpenRate() {
        return openRate;
    }

    public void setOpenRate(double openRate) {
        this.openRate = openRate;
    }

    public double getDayBeforeCloseRate() {
        return dayBeforeCloseRate;
    }

    public void setDayBeforeCloseRate(double dayBeforeCloseRate) {
        this.dayBeforeCloseRate = dayBeforeCloseRate;
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
