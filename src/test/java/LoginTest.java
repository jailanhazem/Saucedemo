import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "validCredentials")
    public void testValidLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed with valid credentials.");
    }

    @Test(dataProvider = "invalidCredentials")
    public void testInvalidLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(
                errorMessage.contains("Username is required") ||
                        errorMessage.contains("Password is required") ||
                        errorMessage.contains("Username and password do not match"),
                "Unexpected error message for invalid credentials: " + errorMessage
        );
    }

    @Test(dataProvider = "edgeCaseCredentials")
    public void testEdgeCaseLogin(String username, String password, String expectedErrorMessage) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();

        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message mismatch for edge case.");
    }

    @DataProvider(name = "validCredentials")
    public Object[][] validCredentials() {
        return new Object[][]{
                {"standard_user", "secret_sauce"}
        };
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"invalid_user", "wrong_password"},
                {"", "secret_sauce"},
                {"standard_user", ""},
                {"", ""}
        };
    }

    @DataProvider(name = "edgeCaseCredentials")
    public Object[][] edgeCaseCredentials() {
        return new Object[][]{
                {"!@#$%^&*", "password", "Epic sadface: Username and password do not match any user in this service"},
                {"longusernamebutnotvalidbecauseitistoolong", "password", "Epic sadface: Username and password do not match any user in this service"},
                {"standard_user", "verylongpasswordthatshouldnotbeacceptedbecauseitistoolong", "Epic sadface: Username and password do not match any user in this service"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"special_char@user.com", "passw0rd!", "Epic sadface: Username and password do not match any user in this service"},
        };
    }
}
