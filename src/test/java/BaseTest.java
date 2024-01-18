import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class BaseTest {
    @Test
    public void goToWebsiteTestChrome() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://tutorialsninja.com/demo/");
    }

    @Test
    public void goToWebsiteTestEdge() {
        WebDriver driver = new EdgeDriver();
        driver.get("https://tutorialsninja.com/demo/");
    }

    @Test
    public void goToWebsiteTestMozilla() {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://tutorialsninja.com/demo/");
    }
}
