package testcases;

import baseTest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.LoginSteps;

public class LoginTest extends BaseTest {

    @Test(description = "Successful login with valid credentials")
    public void testSuccessfulLogin() {
        log.info("TEST: testSuccessfulLogin");

        // loginPage is inherited from BaseTest — page chaining starts here
        new LoginSteps(loginPage)
                .goToLoginPage()
                .loginWithDefaultCredentials();

        Assert.assertFalse(
                loginPage.isLoginErrorDisplayed(),
                "Login error should NOT be shown for valid credentials");
    }

    @Test(description = "Login fails with invalid credentials")
    public void testLoginWithInvalidCredentials() {
        log.info("TEST: testLoginWithInvalidCredentials");

        LoginSteps steps = new LoginSteps(loginPage);

        steps.goToLoginPage()
                .loginWith("invalid@test.com", "wrongpassword");

        Assert.assertTrue(
                steps.isLoginErrorVisible(),
                "Login error message SHOULD be shown for invalid credentials");
    }
}
