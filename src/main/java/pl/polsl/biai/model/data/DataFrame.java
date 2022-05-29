package pl.polsl.biai.model.data;

import pl.polsl.biai.normalizationmethod.MinMaxNormalizable;

import java.util.ArrayList;
import java.util.List;


public class DataFrame implements MinMaxNormalizable {
    private List<Row> rows = new ArrayList<>();
    private final ArrayList<Double> expectedOutputs = new ArrayList<>();
    private double minOpenRate;
    private double maxOpenRate;
    private double minCloseRate;
    private double maxCloseRate;
    private double minHighRate;
    private double maxHighRate;
    private double minLowRate;
    private double maxLowRate;
    private int minVolume;
    private int maxVolume;

    public DataFrame() {
        this.minOpenRate = 0.0;
        this.maxOpenRate = 0.0;
        this.minCloseRate = 0.0;
        this.maxCloseRate = 0.0;
        this.minHighRate = 0.0;
        this.maxHighRate = 0.0;
        this.minLowRate = 0.0;
        this.maxLowRate = 0.0;
        this.minVolume = 0;
        this.maxVolume = 0;
    }

    public double getMaxCloseRate() {
        return maxCloseRate;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        this.minOpenRate = rows.get(0).getOpenRate();
        this.maxOpenRate = rows.get(0).getOpenRate();
        this.minCloseRate = rows.get(0).getCloseRate();
        this.maxCloseRate = rows.get(0).getCloseRate();
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

    public double getMinCloseRate() {
        return this.minCloseRate;
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
            r.setNormalizedVolume(this.minMaxNormalization(maxRange, minRange, this.maxVolume, this.minVolume, r.getVolume()));
            r.setLowRate(this.minMaxNormalization(maxRange, minRange, this.maxLowRate, this.minLowRate, r.getLowRate()));
            r.setHighRate(this.minMaxNormalization(maxRange, minRange, this.maxHighRate, this.minHighRate, r.getHighRate()));
            r.setCloseRate(this.minMaxNormalization(maxRange, minRange, this.maxCloseRate, this.minCloseRate, r.getCloseRate()));
            r.setOpenRate(this.minMaxNormalization(maxRange, minRange, this.maxOpenRate, this.minOpenRate, r.getOpenRate()));
        }
        for (int i = 0; i < this.expectedOutputs.size(); i++) {
            this.expectedOutputs.set(i, this.minMaxNormalization(maxRange, minRange, this.maxCloseRate, this.minCloseRate, this.expectedOutputs.get(i)));
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
            if (row.getCloseRate() <= this.minCloseRate) {
                this.minCloseRate = row.getCloseRate();
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
            if (row.getCloseRate() >= this.maxCloseRate) {
                this.maxCloseRate = row.getCloseRate();
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
