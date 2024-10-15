package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators for checkout fields, button, and total amount
    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By taxAmount = By.cssSelector(".summary_tax_label");
    private By totalAmount = By.cssSelector(".summary_total_label");
    private By finishButton = By.id("finish");

    // Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Method to fill out the checkout form
    public void enterCheckoutInformation(String firstName, String lastName, String postalCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField)).sendKeys(lastName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(postalCodeField)).sendKeys(postalCode);
    }

    // Method to click the continue button
    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    // Method to retrieve the tax amount
    public String getTaxAmount() {
        WebElement taxElement = wait.until(ExpectedConditions.visibilityOfElementLocated(taxAmount));
        return taxElement.getText();
    }

    // Method to retrieve the total amount
    public String getTotalAmount() {
        WebElement totalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(totalAmount));
        return totalElement.getText();
    }

    // Method to click the finish button
    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
    }
}
