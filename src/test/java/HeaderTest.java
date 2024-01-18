import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HeaderTest {
    @Test
    public void verifyHeaderElementTest() {
        // Setup
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(TestData.URL);

        // Validare intrare in site
        String expectedResult = "Qafox.com";
        // css-> #valueid / .valueclass
        String actualResult = driver.findElement(By.id("logo")).getText();
        Assert.assertEquals(actualResult, expectedResult, ErrorMessage.TITLE_TEXT_NOT_CORRECT);

        // For css
        // driver.findElement(By.cssSelector("#logo"));

                          // A boolean
        Assert.assertTrue(driver.findElement(By.cssSelector("#logo")).isDisplayed());

        String actualColor = driver.findElement(By.xpath("//a[contains(text(), 'Qafox.com')]"))
                .getCssValue("color");
        Assert.assertEquals(actualColor, TestData.BLUE_COLOR, ErrorMessage.TITLE_COLOR_NOT_CORRECT);

        // Validare butonul
        WebElement cartButton = driver.findElement(By.xpath("(//button[@data-toggle='dropdown'])[2]"));

        Assert.assertTrue(cartButton.isDisplayed());
        Assert.assertEquals(cartButton.getCssValue("background-color"), TestData.BLACK_COLOR,
                String.format(ErrorMessage.ELEMENT_NOT_CORRECT, "Cart"));
    }
}
