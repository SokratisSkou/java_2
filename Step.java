/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orghua.recipes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Step {
    private List<Ingredient> ingredients = new ArrayList<>();
    private Set<String> utensils = new HashSet<>();
    private int duration; // in minutes
    private String description;

    public Step() {
        this.description = "";
    }

    public void parseLine(String line) {
        // Append the line to the step's description
        description += line + "\n";
        
        // Regex to parse ingredients, utensils, and time from the line
        Pattern ingredientPattern = Pattern.compile("@(\\w+|\\w+\\s\\w+)?\\{(\\d+\\.?\\d*)?%?(\\w+)?}");
        Pattern utensilPattern = Pattern.compile("#(\\w+|\\w+\\s\\w+)");
        Pattern timePattern = Pattern.compile("~\\{(\\d+)%?(\\w+)?}");

        Matcher ingredientMatcher = ingredientPattern.matcher(line);
        while (ingredientMatcher.find()) {
            String name = ingredientMatcher.group(1);
            double quantity = ingredientMatcher.group(2) == null ? 1.0 : Double.parseDouble(ingredientMatcher.group(2));
            String unit = ingredientMatcher.group(3) == null ? "" : ingredientMatcher.group(3);
            ingredients.add(new Ingredient(name, quantity, unit));
        }

        Matcher utensilMatcher = utensilPattern.matcher(line);
        while (utensilMatcher.find()) {
            utensils.add(utensilMatcher.group(1));
        }

        Matcher timeMatcher = timePattern.matcher(line);
        if (timeMatcher.find()) {
            duration += Integer.parseInt(timeMatcher.group(1));
        }
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Set<String> getUtensils() {
        return utensils;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isEmpty() {
        return description.isEmpty();
    }

    @Override
    public String toString() {
        return description;
    }
}