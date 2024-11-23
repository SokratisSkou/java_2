
package orghua.recipes;



import java.io.IOException;
import java.util.*;

public class main {
    public static void main(String[] args) {
        // Check if arguments are provided
        if (args.length < 2) {
            System.out.println("Usage: java RecipeMain -list <filename1> <filename2> ... OR <filename1> <filename2> ...");
            return;
        }

        // Check if the first argument is "-list"
        if (args[0].equals("-list")) {
            // Consolidated shopping list for all ingredients
            Map<String, Double> shoppingList = new HashMap<>();

            // Loop through each file after the "-list" argument
            for (int i = 1; i < args.length; i++) {
                String filename = args[i];
                try {
                    // Parse the recipe from the file
                    Recipe recipe = RecipeParser.parseRecipe(filename);

                    // Get the ingredients and their quantities
                    for (Map.Entry<String, Ingredient> entry : recipe.getIngredients().entrySet()) {
                        String ingredientName = entry.getKey();
                        double quantity = entry.getValue().getQuantity();

                        // Add to the shopping list, summing quantities if ingredient already exists
                        shoppingList.merge(ingredientName, quantity, Double::sum);
                    }
                } catch (IOException e) {
                    System.out.println("Error: Unable to load recipe from file '" + filename + "'.");
                    System.out.println("Details: " + e.getMessage());
                }
            }

            // Print the consolidated shopping list
            System.out.println("=== Shopping List ===");
            shoppingList.forEach((ingredient, quantity) -> System.out.println("- " + ingredient + ": " + quantity));
        } else {
            // If "-list" is not present, treat each argument as a separate recipe file
            for (String filename : args) {
                try {
                    // Parse and print each recipe
                    Recipe recipe = RecipeParser.parseRecipe(filename);
                    System.out.println("=== Recipe from file: " + filename + " ===");
                    recipe.printRecipe();
                    System.out.println(); // Add a blank line between recipes for clarity
                } catch (IOException e) {
                    System.out.println("Error: Unable to load recipe from file '" + filename + "'.");
                    System.out.println("Details: " + e.getMessage());
                }
            }
        }
    }
}
