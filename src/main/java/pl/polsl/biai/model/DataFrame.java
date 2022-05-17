package pl.polsl.biai.model;

import pl.polsl.biai.normalizationmethod.MinMaxNormalizable;

import java.util.ArrayList;
import java.util.List;


public class DataFrame implements MinMaxNormalizable {
    private List<Row> rows = new ArrayList<>();
    private final ArrayList<Double> expectedOutputs = new ArrayList<>();
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

    /**
     * Method used to prepare all parameters to min max normalization
     *
     * @param maxRange maximum range after normalization
     * @param minRange minimum range after normalization
     */
    public void prepareToMinMaxNormalizationAndNormalize(double maxRange, double minRange) {
        this.findMinimums();
        this.findMaximums();
        for (Row r : this.rows) {
            r.setVolume(this.minMaxNormalization(maxRange, minRange, this.maxVolume, this.minVolume, r.getVolume()));
            r.setLowRate(this.minMaxNormalization(maxRange, minRange, this.maxLowRate, this.minLowRate, r.getLowRate()));
            r.setHighRate(this.minMaxNormalization(maxRange, minRange, this.maxHighRate, this.minHighRate, r.getHighRate()));
            r.setCloseRate(this.minMaxNormalization(maxRange, minRange, this.maxDayBeforeCloseRate, this.minDayBeforeCloseRate, r.getCloseRate()));
            r.setOpenRate(this.minMaxNormalization(maxRange, minRange, this.maxOpenRate, this.minOpenRate, r.getOpenRate()));
        }
        for (int i = 0; i < this.expectedOutputs.size(); i++) {
            this.expectedOutputs.set(i, this.minMaxNormalization(maxRange, minRange, this.maxDayBeforeCloseRate, this.minDayBeforeCloseRate, this.expectedOutputs.get(i)));
        }
    }

    /**
     * Method finds minimum values of fields in dataset
     */
    public void findMinimums() {
        this.rows.forEach(row -> {
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
    }

    /**
     * Method fins minimum values of fields in dataset
     */
    public void findMaximums() {
        this.rows.forEach(row -> {
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
    }
}
