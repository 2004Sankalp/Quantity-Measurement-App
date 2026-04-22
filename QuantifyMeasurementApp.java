/**
 * UC1: Quantity Measurement - Feet Equality
 */

public class QuantifyMeasurementApp {

    // 🔹 Inner class representing Feet
    static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        // Override equals() method
        @Override
        public boolean equals(Object obj) {

            // Same reference
            if (this == obj) return true;

            // Null or different type
            if (obj == null || getClass() != obj.getClass()) return false;

            // Type casting
            Feet other = (Feet) obj;

            // Compare double safely
            return Double.compare(this.value, other.value) == 0;
        }

        // Optional (good practice when equals is overridden)
        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    // 🔹 Main method
    public static void main(String[] args) {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        boolean result = f1.equals(f2);

        System.out.println("Equal (" + result + ")");
    }
}