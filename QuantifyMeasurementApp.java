/**
 * UC9: Weight Measurement (Kg, Gram, Pound)
 */

// 🔹 LENGTH ENUM (already from UC8)
enum LengthUnit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETER(0.393701 / 12.0);

    private final double toFeet;

    LengthUnit(double toFeet) {
        this.toFeet = toFeet;
    }

    public double convertToBaseUnit(double value) {
        return value * toFeet;
    }

    public double convertFromBaseUnit(double feet) {
        return feet / toFeet;
    }
}

// 🔹 WEIGHT ENUM (NEW)
enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKg;

    WeightUnit(double toKg) {
        this.toKg = toKg;
    }

    public double convertToBaseUnit(double value) {
        return value * toKg;
    }

    public double convertFromBaseUnit(double kg) {
        return kg / toKg;
    }
}

// 🔹 LENGTH CLASS (unchanged)
class Quantity {
    private final double value;
    private final LengthUnit unit;

    public Quantity(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException();
        if (unit == null) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    private double toFeet() {
        return unit.convertToBaseUnit(value);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quantity other = (Quantity) obj;
        return Double.compare(this.toFeet(), other.toFeet()) == 0;
    }
}

// 🔹 WEIGHT CLASS (NEW)
class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    private double toKg() {
        return unit.convertToBaseUnit(value);
    }

    // 🔹 EQUALITY
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        return Double.compare(this.toKg(), other.toKg()) == 0;
    }

    // 🔹 CONVERSION
    public QuantityWeight convertTo(WeightUnit target) {

        double kg = this.toKg();
        double result = target.convertFromBaseUnit(kg);

        return new QuantityWeight(result, target);
    }

    // 🔹 ADD (default → first unit)
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    // 🔹 ADD (target unit)
    public QuantityWeight add(QuantityWeight other, WeightUnit target) {

        if (other == null) throw new IllegalArgumentException();

        double sumKg = this.toKg() + other.toKg();
        double result = target.convertFromBaseUnit(sumKg);

        return new QuantityWeight(result, target);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}

// 🔹 MAIN
public class QuantifyMeasurementApp {

    public static void main(String[] args) {

        // 🔥 Equality
        System.out.println(new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                .equals(new QuantityWeight(1000.0, WeightUnit.GRAM)));

        // 🔥 Conversion
        System.out.println(new QuantityWeight(2.0, WeightUnit.POUND)
                .convertTo(WeightUnit.KILOGRAM));

        // 🔥 Addition
        System.out.println(new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                .add(new QuantityWeight(1000.0, WeightUnit.GRAM)));

        // 🔥 Explicit target
        System.out.println(new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                .add(new QuantityWeight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM));
    }
}