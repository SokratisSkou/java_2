package orghua.recipes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Recipe implements Iterable<Step> {
    private String name;
    private List<Step> steps;
    private Map<String, Ingredient> ingredients;
    private Set<String> utensils;

    // Constructor
    public Recipe(String name) {
        this.name = name;
        this.steps = new ArrayList<>();
        this.ingredients = new HashMap<>();
        this.utensils = new HashSet<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Map<String, Ingredient> getIngredients() {
        return ingredients;
    }

    public Set<String> getUtensils() {
        return utensils;
    }

    // Add a Step
    public void addStep(Step step) {
        steps.add(step);

        // Update ingredients and utensils from the step
        for (Ingredient ingredient : step.getIngredients()) {
            String ingredientName = ingredient.getName();
            double quantity = ingredient.getQuantity();

            // Update quantity if ingredient already exists
            ingredients.merge(ingredientName, ingredient, 
                (existing, newIngredient) -> {
                    existing.addQuantity(newIngredient.getQuantity());
                    return existing;
                });
        }

        // Add utensils from the step
        utensils.addAll(step.getUtensils());
    }

    // Calculate total ingredients with multiplier
    public Map<String, Double> getTotalIngredients(int multiplier) {
        Map<String, Double> totalIngredients = new HashMap<>();
        for (Map.Entry<String, Ingredient> entry : ingredients.entrySet()) {
            totalIngredients.put(entry.getKey(), entry.getValue().getQuantity() * multiplier);
        }
        return totalIngredients;
    }

    // Get required utensils
    public Set<String> getRequiredUtensils() {
        return new HashSet<>(utensils);
    }

    // Calculate total time
    public int getTotalTime() {
        return steps.stream().mapToInt(Step::getDuration).sum();
    }

    // Load Recipe from File 
   

    // Print Recipe
    public void printRecipe() {
        System.out.println("Recipe: " + name);
        System.out.println("\nIngredients:");
        ingredients.forEach((k, v) -> System.out.println("- " + k + ": " + v));
        
        System.out.println("\nUtensils:");
        utensils.forEach(utensil -> System.out.println("- " + utensil));
        
        System.out.println("\nSteps:");
        int i = 1;
        for (Step step : this) {
            System.out.println(i++ + ". " + step);
        }
        
        System.out.println("\nTotal Time: " + getTotalTime() + " minutes");
    }

    // Implement the iterator() method to make Recipe iterable over steps
    @Override
    public Iterator<Step> iterator() {
        return steps.iterator();
    }
}
