package com.github.giulioscattolin;

import java.util.function.Consumer;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class EagerGoogleRawGnssMeasurementLineReader {
    private final GoogleGnssLoggerRecordCollector itsCollector;

    public EagerGoogleRawGnssMeasurementLineReader(GoogleGnssLoggerRecordCollector collector) {
        itsCollector = collector;
    }

    public void readLine(String line) {
        if (line.startsWith("Raw"))
            itsCollector.collect(new Read(line).itsGoogleRawGnssMeasurement);
    }

    static class Read {
        MutableGoogleRawGnssMeasurement itsGoogleRawGnssMeasurement = new MutableGoogleRawGnssMeasurement();
        String[] itsFields;

        Read(String line) {
            itsFields = line.split(",");
            read();
        }

        private void read() {
            trySupplyLongAt(1, itsGoogleRawGnssMeasurement::setUtcTimeMillis);
            trySupplyLongAt(2, itsGoogleRawGnssMeasurement::setTimeNanos);
            trySupplyIntegerAt(3, itsGoogleRawGnssMeasurement::setLeapSecond);
            trySupplyDoubleAt(4, itsGoogleRawGnssMeasurement::setTimeUncertaintyNanos);
            trySupplyLongAt(5, itsGoogleRawGnssMeasurement::setFullBiasNanos);
            trySupplyDoubleAt(6, itsGoogleRawGnssMeasurement::setBiasNanos);
            trySupplyDoubleAt(7, itsGoogleRawGnssMeasurement::setBiasUncertaintyNanos);
            trySupplyDoubleAt(8, itsGoogleRawGnssMeasurement::setDriftNanosPerSecond);
            trySupplyDoubleAt(9, itsGoogleRawGnssMeasurement::setDriftUncertaintyNanosPerSecond);
            trySupplyIntegerAt(10, itsGoogleRawGnssMeasurement::setHardwareClockDiscontinuityCount);
            trySupplyIntegerAt(11, itsGoogleRawGnssMeasurement::setSvid);
            trySupplyDoubleAt(12, itsGoogleRawGnssMeasurement::setTimeOffsetNanos);
            trySupplyIntegerAt(13, itsGoogleRawGnssMeasurement::setState);
            trySupplyLongAt(14, itsGoogleRawGnssMeasurement::setReceivedSvTimeNanos);
            trySupplyLongAt(15, itsGoogleRawGnssMeasurement::setReceivedSvTimeUncertaintyNanos);
            trySupplyDoubleAt(16, itsGoogleRawGnssMeasurement::setCn0DbHz);
            trySupplyDoubleAt(17, itsGoogleRawGnssMeasurement::setPseudorangeRateMetersPerSecond);
            trySupplyDoubleAt(18, itsGoogleRawGnssMeasurement::setPseudorangeRateUncertaintyMetersPerSecond);
            trySupplyIntegerAt(19, itsGoogleRawGnssMeasurement::setAccumulatedDeltaRangeState);
            trySupplyDoubleAt(20, itsGoogleRawGnssMeasurement::setAccumulatedDeltaRangeMeters);
            trySupplyDoubleAt(21, itsGoogleRawGnssMeasurement::setAccumulatedDeltaRangeUncertaintyMeters);
            trySupplyDoubleAt(22, itsGoogleRawGnssMeasurement::setCarrierFrequencyHz);
            trySupplyLongAt(23, itsGoogleRawGnssMeasurement::setCarrierCycles);
            trySupplyDoubleAt(24, itsGoogleRawGnssMeasurement::setCarrierPhase);
            trySupplyDoubleAt(25, itsGoogleRawGnssMeasurement::setCarrierPhaseUncertainty);
            trySupplyIntegerAt(26, itsGoogleRawGnssMeasurement::setMultipathIndicator);
            trySupplyDoubleAt(27, itsGoogleRawGnssMeasurement::setSnrInDb);
            trySupplyIntegerAt(28, itsGoogleRawGnssMeasurement::setConstellationType);
            trySupplyDoubleAt(29, itsGoogleRawGnssMeasurement::setAgcDb);
            trySupplyDoubleAt(30, itsGoogleRawGnssMeasurement::setBasebandCn0DbHz);
            trySupplyDoubleAt(31, itsGoogleRawGnssMeasurement::setFullInterSignalBiasNanos);
            trySupplyDoubleAt(32, itsGoogleRawGnssMeasurement::setFullInterSignalBiasUncertaintyNanos);
            trySupplyDoubleAt(33, itsGoogleRawGnssMeasurement::setSatelliteInterSignalBiasNanos);
            trySupplyDoubleAt(34, itsGoogleRawGnssMeasurement::setSatelliteInterSignalBiasUncertaintyNanos);
            trySupplyStringAt(35, itsGoogleRawGnssMeasurement::setCodeType);
            trySupplyLongAt(36, itsGoogleRawGnssMeasurement::setChipsetElapsedRealtimeNanos);
        }

        private void trySupplyLongAt(int index, Consumer<Long> consumer) {
            try {
                consumer.accept(getLongAt(index));
            } catch (Exception ignored) {
            }
        }

        private void trySupplyIntegerAt(int index, Consumer<Integer> consumer) {
            try {
                consumer.accept(getIntegerAt(index));
            } catch (Exception ignored) {
            }
        }

        private void trySupplyDoubleAt(int index, Consumer<Double> consumer) {
            try {
                consumer.accept(getDoubleAt(index));
            } catch (Exception ignored) {
            }
        }

        private void trySupplyStringAt(int index, Consumer<String> consumer) {
            try {
                String field = getFieldAt(index);
                if (!field.isEmpty())
                    consumer.accept(field);
            } catch (Exception ignored) {
            }
        }

        private long getLongAt(int index) {
            return parseLong(getFieldAt(index));
        }

        private int getIntegerAt(int index) {
            return parseInt(getFieldAt(index));
        }

        private double getDoubleAt(int index) {
            return parseDouble(getFieldAt(index));
        }

        private String getFieldAt(int index) {
            return itsFields[index];
        }
    }
}
