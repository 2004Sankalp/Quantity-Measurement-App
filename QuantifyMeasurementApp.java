/**
 * UC5: Unit-to-Unit Conversion
 */

public class QuantifyMeasurementApp {

    // 🔹 ENUM (base = FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeet;
        }
    }

    // 🔹 Quantity Class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // 🔹 Convert to another unit (instance method)
        public Quantity convertTo(LengthUnit targetUnit) {
            double feet = this.toFeet();
            double converted = targetUnit.fromFeet(feet);
            return new Quantity(converted, targetUnit);
        }

        // 🔹 Static conversion API
        public static double convert(double value, LengthUnit source, LengthUnit target) {

            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (source == null || target == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            double feet = source.toFeet(value);
            return target.fromFeet(feet);
        }

        // 🔹 Equals (same as UC4)
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔹 Demo Methods (API usage)
    static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = Quantity.convert(value, from, to);
        System.out.println("convert(" + value + ", " + from + ", " + to + ") = " + result);
    }

    static void demonstrateLengthConversion(Quantity q, LengthUnit to) {
        Quantity result = q.convertTo(to);
        System.out.println(q + " -> " + result);
    }

    // 🔹 MAIN
    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARDS);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH);
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCH);

        // Instance method demo
        Quantity q = new Quantity(2.0, LengthUnit.YARDS);
        demonstrateLengthConversion(q, LengthUnit.INCH);
    }
}