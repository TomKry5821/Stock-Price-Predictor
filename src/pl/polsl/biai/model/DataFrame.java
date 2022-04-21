package pl.polsl.biai.model;

import java.util.ArrayList;
import java.util.List;


public class DataFrame implements Normalizable {
    private List<Row> rows = new ArrayList<>();
    private List<Double> expectedOutputs = new ArrayList<>();
    private double minOpenRate;
    private double maxOpenRate;
    private double minDayBeforeCloseRate;
    private double maxDayBeforeCloseRate;
    private double minHighRate;
    private double maxHighRate;
    private double minLowRate;
    private double maxLowRate;
    private double minVolume;
    private double maxVolume;

    public DataFrame() {
        this.minOpenRate = 0.0;
        this.maxOpenRate = 0.0;
        this.minDayBeforeCloseRate = 0.0;
        this.maxDayBeforeCloseRate = 0.0;
        this.minHighRate = 0.0;
        this.maxHighRate = 0.0;
        this.minLowRate = 0.0;
        this.maxLowRate = 0.0;
        this.minVolume = 0.0;
        this.maxVolume = 0.0;
    }

    public double getMinOpenRate() {
        return minOpenRate;
    }

    public void setMinOpenRate(double minOpenRate) {
        this.minOpenRate = minOpenRate;
    }

    public double getMaxOpenRate() {
        return maxOpenRate;
    }

    public void setMaxOpenRate(double maxOpenRate) {
        this.maxOpenRate = maxOpenRate;
    }

    public double getMinDayBeforeCloseRate() {
        return minDayBeforeCloseRate;
    }

    public void setMinDayBeforeCloseRate(double minDayBeforeCloseRate) {
        this.minDayBeforeCloseRate = minDayBeforeCloseRate;
    }

    public double getMaxDayBeforeCloseRate() {
        return maxDayBeforeCloseRate;
    }

    public void setMaxDayBeforeCloseRate(double maxDayBeforeCloseRate) {
        this.maxDayBeforeCloseRate = maxDayBeforeCloseRate;
    }

    public double getMinHighRate() {
        return minHighRate;
    }

    public void setMinHighRate(double minHighRate) {
        this.minHighRate = minHighRate;
    }

    public double getMaxHighRate() {
        return maxHighRate;
    }

    public void setMaxHighRate(double maxHighRate) {
        this.maxHighRate = maxHighRate;
    }

    public double getMinLowRate() {
        return minLowRate;
    }

    public void setMinLowRate(double minLowRate) {
        this.minLowRate = minLowRate;
    }

    public double getMaxLowRate() {
        return maxLowRate;
    }

    public void setMaxLowRate(double maxLowRate) {
        this.maxLowRate = maxLowRate;
    }

    public double getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(double minVolume) {
        this.minVolume = minVolume;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        this.minOpenRate = rows.get(0).getOpenRate();
        this.maxOpenRate = rows.get(0).getOpenRate();
        this.minDayBeforeCloseRate = rows.get(0).getCloseRate();
        this.maxDayBeforeCloseRate = rows.get(0).getCloseRate();
        this.minHighRate = rows.get(0).getHighRate();
        this.maxHighRate = rows.get(0).getHighRate();
        this.maxLowRate = rows.get(0).getLowRate();
        this.minVolume = rows.get(0).getVolume();
        this.maxVolume = rows.get(0).getVolume();
    }

    public List<Double> getExpectedOutputs() {
        return expectedOutputs;
    }

    public void setExpectedOutputs() {
        for (int i = 1; i < this.rows.size(); i++) {
            this.expectedOutputs.add(rows.get(i).getCloseRate());
        }
        rows.remove(rows.size() - 1); // we need to remove last item in rows because we do not have expected output for last item
    }

    public void prepareToMinMaxNormalizationAndNormalize(double maxRange, double minRange) {
        this.findMinimums();
        this.findMaxes();
        for (Row r : this.rows) {
            r.setVolume(this.minMaxNormalization(maxRange, minRange, this.maxVolume, this.minVolume, r.getVolume()));
            r.setLowRate(this.minMaxNormalization(maxRange, minRange, this.maxLowRate, this.minLowRate, r.getLowRate()));
            r.setHighRate(this.minMaxNormalization(maxRange, minRange, this.maxHighRate, this.minHighRate, r.getHighRate()));
            r.setCloseRate(this.minMaxNormalization(maxRange, minRange, this.maxDayBeforeCloseRate, this.minDayBeforeCloseRate, r.getCloseRate()));
            r.setOpenRate(this.minMaxNormalization(maxRange, minRange, this.maxOpenRate, this.minOpenRate, r.getOpenRate()));
        }
        for(int i = 0; i < this.expectedOutputs.size(); i++){
            this.expectedOutputs.set(i, this.minMaxNormalization(maxRange, minRange, this.maxDayBeforeCloseRate, this.minDayBeforeCloseRate, this.expectedOutputs.get(i)));
        }
    }

    public void findMinimums() {
        this.rows.stream().forEach(row -> {
            if (row.getVolume() <= this.minVolume) {
                this.minVolume = row.getVolume();
            }
            if (row.getCloseRate() <= this.minDayBeforeCloseRate) {
                this.minDayBeforeCloseRate = row.getCloseRate();
            }
            if (row.getHighRate() <= this.minHighRate) {
                this.minHighRate = row.getHighRate();
            }
            if (row.getLowRate() <= this.minLowRate) {
                this.minLowRate = row.getLowRate();
            }
            if (row.getOpenRate() <= this.minOpenRate) {
                this.minOpenRate = row.getOpenRate();
            }
        });
        return;
    }

    public void findMaxes() {
        this.rows.stream().forEach(row -> {
            if (row.getVolume() >= this.maxVolume) {
                this.maxVolume = row.getVolume();
            }
            if (row.getCloseRate() >= this.maxDayBeforeCloseRate) {
                this.maxDayBeforeCloseRate = row.getCloseRate();
            }
            if (row.getHighRate() >= this.maxHighRate) {
                this.maxHighRate = row.getHighRate();
            }
            if (row.getLowRate() >= this.maxLowRate) {
                this.maxLowRate = row.getLowRate();
            }
            if (row.getOpenRate() >= this.maxOpenRate) {
                this.maxOpenRate = row.getOpenRate();
            }
        });
        return;
    }
}
