package orghua.recipes;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeParser {

    public static Recipe parseRecipe(String filename) throws IOException {
        Recipe recipe = new Recipe(filename);
        Step currentStep = new Step();

        for (String line : Files.readAllLines(Paths.get(filename))) {
            if (line.trim().isEmpty()) {
                if (!currentStep.isEmpty()) {
                    recipe.addStep(currentStep);
                    currentStep = new Step();
                }
            } else {
                currentStep.parseLine(line);
            }
        }

        if (!currentStep.isEmpty()) {
            recipe.addStep(currentStep);
        }

        return recipe;
    }
}
