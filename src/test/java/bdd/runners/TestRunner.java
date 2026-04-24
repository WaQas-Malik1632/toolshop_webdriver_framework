package bdd.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/bdd/features/Login.feature",
        glue = {"bdd.stepDefinitions", "bdd.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/test-runner-report.html",
                "json:target/cucumber-reports/test-runner.json",
                "junit:target/cucumber-reports/test-runner.xml",
                "timeline:target/cucumber-reports/timeline",
                "rerun:target/cucumber-reports/rerun.txt",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
