package pl.polsl.biai.normalizationmethod;

public interface MinMaxNormalizable {
    /**
     * Returns normalized value
     *
     * @param maxRange Maximum range of the normalized value
     * @param minRange Minimum range od the normalized value
     * @param max      Maximum value in current set
     * @param min      Minimum value in current set
     * @param actual   value to normalize
     * @return normalized value
     */
    default double minMaxNormalization(double maxRange, double minRange, double max, double min, double actual) {
        return (actual - min) / (max - min) * (maxRange - minRange) + minRange;
    }

    default double minMaxDenormalization(double max, double min, double actual) {
        return actual * (max - min) + min;
    }

}
