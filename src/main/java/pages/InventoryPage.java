package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Elements
    private By sortDropdown = By.className("product_sort_container");
    private By addToCartButtons = By.cssSelector(".btn_inventory");
    private By cartItemCount = By.className("shopping_cart_badge");
    private By itemNames = By.className("inventory_item_name");

    // Constructor
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Page Actions
    public void sortItemsFromZtoA() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));
        new Select(dropdown).selectByVisibleText("Name (Z to A)");
    }

    public List<String> getItemNames() {
        List<WebElement> items = driver.findElements(itemNames);
        return items.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void addItemToCart(int itemIndex) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        if (itemIndex >= 0 && itemIndex < buttons.size()) {
            buttons.get(itemIndex).click();
        } else {
            throw new IndexOutOfBoundsException("Invalid item index: " + itemIndex);
        }
    }

    public int getCartItemCount() {
        String count = wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemCount)).getText();
        return Integer.parseInt(count);
    }
}
