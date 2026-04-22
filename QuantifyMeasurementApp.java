/**
 * UC7: Addition with Target Unit Specification
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

        // 🔹 UC6 METHOD (kept for backward compatibility)
        public Quantity add(Quantity other) {
            return add(other, this.unit);
        }

        // 🔹 UC7 METHOD (CORE)
        public Quantity add(Quantity other, LengthUnit targetUnit) {

            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            // Convert both to base unit
            double sumFeet = this.toFeet() + other.toFeet();

            // Convert to target unit
            double result = targetUnit.fromFeet(sumFeet);

            return new Quantity(result, targetUnit);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // 🔹 MAIN (TEST CASES)
    public static void main(String[] args) {

        // Feet target
        System.out.println(new Quantity(1.0, LengthUnit.FEET)
                .add(new Quantity(12.0, LengthUnit.INCH), LengthUnit.FEET));

        // Inches target
        System.out.println(new Quantity(1.0, LengthUnit.FEET)
                .add(new Quantity(12.0, LengthUnit.INCH), LengthUnit.INCH));

        // Yards target
        System.out.println(new Quantity(1.0, LengthUnit.FEET)
                .add(new Quantity(12.0, LengthUnit.INCH), LengthUnit.YARDS));

        // Yard + Feet → Yard
        System.out.println(new Quantity(1.0, LengthUnit.YARDS)
                .add(new Quantity(3.0, LengthUnit.FEET), LengthUnit.YARDS));

        // Inches + Yard → Feet
        System.out.println(new Quantity(36.0, LengthUnit.INCH)
                .add(new Quantity(1.0, LengthUnit.YARDS), LengthUnit.FEET));

        // CM + Inches → CM
        System.out.println(new Quantity(2.54, LengthUnit.CENTIMETER)
                .add(new Quantity(1.0, LengthUnit.INCH), LengthUnit.CENTIMETER));

        // Zero case
        System.out.println(new Quantity(5.0, LengthUnit.FEET)
                .add(new Quantity(0.0, LengthUnit.INCH), LengthUnit.YARDS));

        // Negative case
        System.out.println(new Quantity(5.0, LengthUnit.FEET)
                .add(new Quantity(-2.0, LengthUnit.FEET), LengthUnit.INCH));
    }
}