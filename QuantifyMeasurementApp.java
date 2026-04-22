/**
 * UC8: Refactoring LengthUnit as Standalone with Conversion Responsibility
 */

// 🔹 STANDALONE ENUM (TOP LEVEL)
enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETER(0.393701 / 12.0);

    private final double toFeet;

    LengthUnit(double toFeet) {
        this.toFeet = toFeet;
    }

    // Convert TO base (feet)
    public double convertToBaseUnit(double value) {
        return value * toFeet;
    }

    // Convert FROM base (feet)
    public double convertFromBaseUnit(double feetValue) {
        return feetValue / toFeet;
    }
}

// 🔹 MAIN APP CLASS
public class QuantifyMeasurementApp {

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
            return unit.convertToBaseUnit(value);
        }

        // 🔹 Conversion
        public Quantity convertTo(LengthUnit targetUnit) {
            double feet = this.toFeet();
            double result = targetUnit.convertFromBaseUnit(feet);
            return new Quantity(result, targetUnit);
        }

        // 🔹 Addition (UC7)
        public Quantity add(Quantity other, LengthUnit targetUnit) {

            if (other == null) {
                throw new IllegalArgumentException("Other cannot be null");
            }
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double sumFeet = this.toFeet() + other.toFeet();
            double result = targetUnit.convertFromBaseUnit(sumFeet);

            return new Quantity(result, targetUnit);
        }

        // 🔹 Equality
        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // 🔹 MAIN (TESTS)
    public static void main(String[] args) {

        // Conversion
        System.out.println(new Quantity(1.0, LengthUnit.FEET)
                .convertTo(LengthUnit.INCH));

        // Addition
        System.out.println(new Quantity(1.0, LengthUnit.FEET)
                .add(new Quantity(12.0, LengthUnit.INCH), LengthUnit.FEET));

        // Equality
        System.out.println(new Quantity(36.0, LengthUnit.INCH)
                .equals(new Quantity(1.0, LengthUnit.YARDS)));

        // Direct enum usage
        System.out.println(LengthUnit.INCH.convertToBaseUnit(12.0));
    }
}