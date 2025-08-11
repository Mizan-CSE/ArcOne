import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Path to your feature files
        glue = "stepDefinitions", // Package containing step definitions
        plugin = {
                "pretty",
                "json:target/cucumber.json", // Correctly formatted output path
                "html:target/cucumber-html" // Correctly formatted output path
        },
        monochrome = true // Optional: for better console output
)
public class TestRunner {
}