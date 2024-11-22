
package orghua.recipes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;
import java.util.*;

public class main {

    public static void main(String[] args) {
        if (args.length == 1) {
            // Display the recipe
            displayRecipe(args[0]);
            
        } else if (args.length > 1 && args[0].equals("-list")) {
            // Generate a shopping list
            generateShoppingList(Arrays.copyOfRange(args, 1, args.length));
        } else {
            System.out.println("Usage:");
            System.out.println("  java -jar recipes.jar <recipe_file>");
            System.out.println("  java -jar recipes.jar -list <recipe_files...>");
        }
    }

    private static void displayRecipe(String filename) {
        try {
            Recipe recipe = RecipeParser.parseRecipe(filename);
            recipe.printRecipe();
            executeRecipe(recipe);
        } catch (IOException e) {
            System.err.println("Error reading recipe file: " + e.getMessage());
        }
    }

    private static void executeRecipe(Recipe recipe) {
        System.out.println("\n--- Start Cooking ---\n");

        Scanner scanner = new Scanner(System.in);
        for (Step step : recipe) {
            System.out.println(step);
            System.out.print("Press Enter when done with this step...");
            scanner.nextLine();
        }

        System.out.println("\nRecipe completed!");
    }

    private static void generateShoppingList(String[] filenames) {
        Map<String, Ingredient> totalIngredients = new HashMap<>();

        for (String filename : filenames) {
            try {
                Recipe recipe = RecipeParser.parseRecipe(filename);

                for (Map.Entry<String, Ingredient> entry : recipe.getIngredients().entrySet()) {
                    String name = entry.getKey();
                    Ingredient ingredient = entry.getValue();

                    // Add quantities for each ingredient
                    totalIngredients.merge(name, ingredient,
                            (existing, newIngredient) -> {
                                existing.addQuantity(newIngredient.getQuantity());
                                return existing;
                            });
                }
            } catch (IOException e) {
                System.err.println("Error reading recipe file " + filename + ": " + e.getMessage());
            }
        }

        System.out.println("Shopping List:");
        for (Map.Entry<String, Ingredient> entry : totalIngredients.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }
    }
}