package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/feature",
        glue = {"stepDefinitions","helper"},
        monochrome = true,
        tags = "@hafele", plugin = "json:target/Cucumber-Report/json.html"
)
public class TestRunner extends AbstractTestNGCucumberTests {

}
