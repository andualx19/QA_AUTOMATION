import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TestData {
    public static final String URL = "https://tutorialsninja.com/demo/", BLUE_COLOR = "rgba(35, 161, 209, 1)";
    public static final String BLACK_COLOR = "rgba(54, 54, 54, 1)";

    public static String generateNewEmail() {
        Random random = new Random();
        return "andu" + random.nextInt(1_000_000) + "@gmail.com";
    }

    public static File textFileCreator() {
        String path = System.getProperty("user.dir");
        File textFile = new File(path + "\\Test.txt");

        try {
            if (textFile.exists()) {
                return textFile;
            } else {
                if (textFile.createNewFile()) {
                    System.out.println("Success");
                } else {
                    System.err.println("Could not manage file");
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return textFile;
    }

    public static void clickOnElement(WebDriver driver, String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    public static String getTextOnElement(WebDriver driver, String xpath) {
        return driver.findElement(By.xpath(xpath)).getText();
    }

    public static void writeTextOnElement(WebDriver driver, String xpath, String message) {
        driver.findElement(By.xpath(xpath)).sendKeys(message);
    }
}
