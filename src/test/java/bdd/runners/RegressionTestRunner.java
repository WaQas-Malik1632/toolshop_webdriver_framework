package bdd.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/bdd/features",
        glue = {"bdd.stepDefinitions", "bdd.hooks"},
        tags = "@Regression",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/regression-report.html",
                "json:target/cucumber-reports/regression.json",
                "junit:target/cucumber-reports/regression.xml",
                "timeline:target/cucumber-reports/regression-timeline",
                "rerun:target/cucumber-reports/regression-rerun.txt",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        dryRun = false
)
public class RegressionTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
