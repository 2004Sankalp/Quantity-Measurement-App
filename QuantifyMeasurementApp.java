/**
 * UC6: Addition of Length Units
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
                throw new IllegalArgumentException("Invalid value");
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

        // 🔹 ADD METHOD (CORE OF UC6)
        public Quantity add(Quantity other) {

            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }

            // Convert both to base unit
            double sumFeet = this.toFeet() + other.toFeet();

            // Convert back to unit of first operand
            double result = this.unit.fromFeet(sumFeet);

            return new Quantity(result, this.unit);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // 🔹 MAIN (TEST CASES)
    public static void main(String[] args) {

        // Same unit
        System.out.println(new Quantity(1.0, LengthUnit.FEET)
                .add(new Quantity(2.0, LengthUnit.FEET)));

        // Feet + Inches
        System.out.println(new Quantity(1.0, LengthUnit.FEET)
                .add(new Quantity(12.0, LengthUnit.INCH)));

        // Inches + Feet
        System.out.println(new Quantity(12.0, LengthUnit.INCH)
                .add(new Quantity(1.0, LengthUnit.FEET)));

        // Yard + Feet
        System.out.println(new Quantity(1.0, LengthUnit.YARDS)
                .add(new Quantity(3.0, LengthUnit.FEET)));

        // Inches + Yard
        System.out.println(new Quantity(36.0, LengthUnit.INCH)
                .add(new Quantity(1.0, LengthUnit.YARDS)));

        // CM + Inches
        System.out.println(new Quantity(2.54, LengthUnit.CENTIMETER)
                .add(new Quantity(1.0, LengthUnit.INCH)));

        // Zero case
        System.out.println(new Quantity(5.0, LengthUnit.FEET)
                .add(new Quantity(0.0, LengthUnit.INCH)));

        // Negative case
        System.out.println(new Quantity(5.0, LengthUnit.FEET)
                .add(new Quantity(-2.0, LengthUnit.FEET)));
    }
}