
package orghua.recipes;


public class Ingredient {
    private String name;         // Όνομα του υλικού (π.χ., "γάλα", "αλεύρι")
    private double quantity;     // Ποσότητα (π.χ., 250)
    private String unit;         // Μονάδα μέτρησης (π.χ., "ml", "gr")

    // --- Constructors ---
    public Ingredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Ingredient(String name) {
        this(name, 0, "");
    }

    // --- Getters & Setters ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // --- Utility Methods ---

    // Συνδυάζει δύο ίδιου τύπου υλικά
    public void addQuantity(double additionalQuantity) {
        this.quantity += additionalQuantity;
    }

    // Επιστρέφει το υλικό ως String για εκτύπωση
    @Override
    public String toString() {
        return quantity + " " + unit;
    }

    // Σύγκριση για ισότητα (με βάση το όνομα του υλικού)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ingredient that = (Ingredient) obj;
        return name.equalsIgnoreCase(that.name); // Συγκρίνει τα ονόματα (case-insensitive)
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
