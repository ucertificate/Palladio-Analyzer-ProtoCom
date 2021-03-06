package org.palladiosimulator.protocom.resourcestrategies.activeresource.cpu;

import java.util.Arrays;
import java.util.Random;

import org.palladiosimulator.protocom.resourcestrategies.activeresource.AbstractDemandStrategy;
import org.palladiosimulator.protocom.resourcestrategies.activeresource.ResourceTypeEnum;

public class SortArrayDemand extends AbstractDemandStrategy {

    private static final int DEFAULT_ARRAY_SIZE = 1024 * 1024; // Default: 1M Values

    private double[] values = null;

    private final int arraySize;

    /**
     * always use the same seed for the random values to reduce the variability of the sorting
     * algorithms (the same seed generates the same sequence of values)
     */
    private static final int SEED = 1234;

    public SortArrayDemand(final int arraySize) {
        super(-3, 0, 3, 10000, 50);
        this.arraySize = arraySize;
        this.values = new double[this.arraySize];
        final Random r = new Random(SEED);
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = r.nextDouble();
        }
    }

    public SortArrayDemand() {
        this(DEFAULT_ARRAY_SIZE);
    }

    private void sortArray(final int amountOfNumbers) {
        final int iterations = amountOfNumbers / this.arraySize;
        final int rest = amountOfNumbers % this.arraySize;
        for (int i = 0; i < iterations; i++) {
            final double[] lotsOfDoubles = getArray(this.arraySize);
            Arrays.sort(lotsOfDoubles);
        }
        final double[] lotsOfDoubles = getArray(rest);
        Arrays.sort(lotsOfDoubles);
    }

    private double[] getArray(final int amountOfNumbers) {
        return Arrays.copyOf(this.values, amountOfNumbers);
    }

    @Override
    public void run(final long initial) {
        sortArray((int) initial);
    }

    @Override
    public ResourceTypeEnum getStrategysResource() {
        return ResourceTypeEnum.CPU;
    }

    @Override
    public String getName() {
        return "SortArray";
    }

    // TODO: Check if array size is really needed in file name.
    // This doesn't work anymore because calibration file handling has been moved to
    // AbstractResourceEnvironment.

    /*
     * @Override protected String getCalibrationFileName() { return getCalibrationPath() + "/" +
     * getName() + "_" + CalibrationTable.DEFAULT_CALIBRATION_TABLE_SIZE + "_" + this.arraySize +
     * "_" + this.degreeOfAccuracy.name() + ".ser"; }
     */

    @Override
    public void cleanup() {
        // Do nothing.
    }
}
