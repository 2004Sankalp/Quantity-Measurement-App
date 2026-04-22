/**
 * UC4: Extended Unit Support (Yards + Centimeters)
 */

public class QuantifyMeasurementApp {

    // 🔹 ENUM with ALL units (base = FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),          // 1 inch = 1/12 feet
        YARDS(3.0),                // 1 yard = 3 feet
        CENTIMETER(0.393701 / 12); // 1 cm = 0.393701 inches → convert to feet

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    // 🔹 Generic Quantity Class (UNCHANGED)
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toFeet());
        }
    }

    // 🔹 MAIN (TEST CASES)
    public static void main(String[] args) {

        // Yard ↔ Feet
        System.out.println(new Quantity(1.0, LengthUnit.YARDS)
                .equals(new Quantity(3.0, LengthUnit.FEET)));

        // Yard ↔ Inches
        System.out.println(new Quantity(1.0, LengthUnit.YARDS)
                .equals(new Quantity(36.0, LengthUnit.INCH)));

        // Yard ↔ Yard
        System.out.println(new Quantity(2.0, LengthUnit.YARDS)
                .equals(new Quantity(2.0, LengthUnit.YARDS)));

        // CM ↔ CM
        System.out.println(new Quantity(2.0, LengthUnit.CENTIMETER)
                .equals(new Quantity(2.0, LengthUnit.CENTIMETER)));

        // CM ↔ Inches
        System.out.println(new Quantity(1.0, LengthUnit.CENTIMETER)
                .equals(new Quantity(0.393701, LengthUnit.INCH)));
    }
}