package bdd.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/bdd/features",
        glue = {"bdd.stepDefinitions", "bdd.hooks"},
        tags = "@Smoke",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/smoke-report.html",
                "json:target/cucumber-reports/smoke.json",
                "junit:target/cucumber-reports/smoke.xml",
                "timeline:target/cucumber-reports/smoke-timeline",
                "rerun:target/cucumber-reports/smoke-rerun.txt",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        dryRun = false
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
