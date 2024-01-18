import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class RegisterTest {
    // Instantiem un nou driver care deschide un browser de Edge
    WebDriver driver = new EdgeDriver();

    // Date personale fundamentale atat pentru creare cont cat si pentru logare
    private final String email = TestData.generateNewEmail(), inputPassword = "password", name = "Crocodil", lname = "Pisica";

    @Test
    public void createNewAccountTest() throws InterruptedException {
        // Setam fereastra browserului in fullscreen
        driver.manage().window().maximize();
        // Navigam pe URL ul dorit
        driver.get("https://tutorialsninja.com/demo/");
        // Click pe MyAccountButton
        TestData.clickOnElement(driver, "//a[@title='My Account']");
        // Click pe Register Button
        TestData.clickOnElement(driver, "//a[contains(text(), 'Register')]");
        // Asteapta o secunda
        Thread.sleep(1000);
        // Valideaza titlul paginii
        String expectedTitle = "Register Account";
        String actualTitle = TestData.getTextOnElement(driver, "//h1[contains(text(), 'Register Account')]");
        Assert.assertEquals(actualTitle, expectedTitle, "The page title is not correct");
        // Completam cu date valide
        TestData.writeTextOnElement(driver, "//input[@id='input-firstname']", name);
        TestData.writeTextOnElement(driver, "//input[@id='input-lastname']", lname);
        TestData.writeTextOnElement(driver, "//input[@id='input-email']", email);
        TestData.writeTextOnElement(driver, "//input[@id='input-telephone']", "0712344567");
        TestData.writeTextOnElement(driver, "//input[@id='input-password']", inputPassword);
        TestData.writeTextOnElement(driver, "//input[@id='input-confirm']", inputPassword);
        // Dau click pe radio button YES
        TestData.clickOnElement(driver, "(//input[@name='newsletter'])[1]");
        // Bifez checkBox ul de privacy
        TestData.clickOnElement(driver, "//input[@name='agree']");
        // Apas pe butonul de continue
        TestData.clickOnElement(driver, "//input[@value='Continue']");
        // Validez ca am ajuns la mesajul de succes
        String expectedSuccesMassage = "Your Account Has Been Created!";
        String actualSuccesMassage = TestData.getTextOnElement(driver, "//h1[contains(text(), 'Your Account')]");
        Assert.assertEquals(actualSuccesMassage, expectedSuccesMassage, "The success massage is not correct");

        // Verificam daca utilizatorul este intrat in cont
        try {
            String expectedText = "Logout";
            String actualText = TestData.getTextOnElement(driver, "(//a[contains(text(), 'Logout')])[2]");

            if (actualText.equals(expectedText)) {
                TestData.clickOnElement(driver, "(//a[contains(text(), 'Logout')])[2]");
                logIntoAccountTest();
            } else logIntoAccountTest();
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }

    public void logIntoAccountTest() throws InterruptedException {
        // Click pe Log In Button
        TestData.clickOnElement(driver, "//span[contains(text(), 'My Account')]");
        TestData.clickOnElement(driver, "(//a[contains(text(), 'Login')])[1]");

        // Asteapta o secunda
        Thread.sleep(1000);

        // Validare Intrarea in pagina
        String expectedTitle = "Returning Customer";
        String actualTitle = TestData.getTextOnElement(driver, "//h2[contains(text(), 'Returning')]");
        Assert.assertEquals(actualTitle, expectedTitle);

        // Completare cu detaliile contului
        TestData.writeTextOnElement(driver, "//input[@id='input-email']", email);
        TestData.writeTextOnElement(driver, "//input[@id='input-password']", inputPassword);
        TestData.clickOnElement(driver, "//input[@value = 'Login']");

        // Validare logare cu succes
        String expectedText = "Logout";
        String actualText = TestData.getTextOnElement(driver, "(//a[contains(text(), 'Logout')])[2]");
        Assert.assertEquals(actualText, expectedText, "Could not find Logout Button");

        buyAProductTest();
    }

    public void buyAProductTest() throws InterruptedException {
        // Date pentru checkout
        String company = "Company", address = "Sos. Eminescu", city = "Bucuresti", postalCode = "111111";

        // Intrare in meniul principal
        TestData.clickOnElement(driver, "//i[@class='fa fa-home']");

        // Alegere la intamplare unul dintre cele 4 produse
        Random product = new Random();
        int number;

        // in caz ca numarul este 0 repeta pana cand e diferit
        do {
            number = product.nextInt(5);
        } while (number == 0);

        // executa comanda
        TestData.clickOnElement(driver, "(//span[contains(text(), 'Add to Cart')])[" + number + "]");

        // Unele produse sunt adaugate in cos iar altele intra in pagina lor asa ca se vor creea 2 cazuri

        /*
        In cazul in care se adauga in cos se intra direct in meniul de checkout iar pentru celelalte in pagina lor
         */

        // Primul caz vrea sa exprima ca produsul nu a fost adaugat in cos iar al doilea opusul
        switch (number) {
            case 1, 2 -> {
                // Intrare in checkout
                TestData.clickOnElement(driver, "(//i[@class='fa fa-share'])[1]");
                Thread.sleep(1000);

                if (number == 1) {
                    // Completare detalii
                    TestData.writeTextOnElement(driver, "//input[@id='input-payment-firstname']",name);
                    TestData.writeTextOnElement(driver, "//input[@id='input-payment-lastname']", lname);
                    TestData.writeTextOnElement(driver, "//input[@id='input-payment-company']", company);
                    TestData.writeTextOnElement(driver, "//input[@id='input-payment-address-1']", address);
                    TestData.writeTextOnElement(driver, "//input[@id='input-payment-city']", city);
                    TestData.writeTextOnElement(driver, "//input[@id='input-payment-postcode']", postalCode);

                    Thread.sleep(1000);
                    TestData.clickOnElement(driver, "//select[@id='input-payment-country']");
                    TestData.clickOnElement(driver, "//option[@value='175']");
                    TestData.clickOnElement(driver, "//select[@id='input-payment-zone']");
                    Thread.sleep(1000);
                    TestData.clickOnElement(driver, "//option[@value='2679']"); // ???????? (no such element: Unable to locate element: {"method":"xpath","selector":"//option[@value='2679']"})
                    TestData.clickOnElement(driver, "//input[@id='button-payment-address']");

                    Thread.sleep(1000);
                    String expectedResult = "Add Comments About Your Order";
                    String actualResult = TestData.getTextOnElement(driver, "//strong[contains(text(), 'Add')]");
                    Assert.assertEquals(actualResult, expectedResult);

                    // Nu se poate continua deoarece apare eroarea de
                    // NO PAYMENT OPTIONS ARE AVAILABLE asa ca testul se opreste aici
                } else {
                    // Completare informatii
                    Thread.sleep(1000);
                    TestData.clickOnElement(driver, "//a[contains(text(), 'Estimate Shipping & Taxes ')]");
                    Thread.sleep(1000);
                    TestData.clickOnElement(driver, "//select[@id='input-country']"); // ???? (org.openqa.selenium.ElementNotInteractableException: element not interactable)
                    TestData.clickOnElement(driver, "//option[@value='175']");
                    Thread.sleep(1000);
                    TestData.clickOnElement(driver, "//select[@id='input-zone']");
                    TestData.clickOnElement(driver, "//option[@value='2688']");
                    TestData.writeTextOnElement(driver, "//input[@id='input-postcode']", postalCode);

                    TestData.clickOnElement(driver, "//a[contains(text(), 'Checkout')]");

                    // La fiecare Checkout va aparea o eroare PRODUSUL NU ESTE IN STOC
                }
            } case 3 -> {
                // Completare detalii
                Thread.sleep(1000);
                TestData.clickOnElement(driver, "(//input[@value='5'])[2]");
                TestData.clickOnElement(driver, "(//input[@name='option[223][]'])[1]");
                TestData.clickOnElement(driver, "//select[@id='input-option217']");
                TestData.clickOnElement(driver, "//option[@value='4']");
                TestData.writeTextOnElement(driver, "//textarea[@id='input-option209']", "Lorem Ipsum");
                TestData.clickOnElement(driver, "//button[@id='button-upload222']");

                try {
                    WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
                    fileInput.sendKeys(TestData.textFileCreator().getAbsolutePath());

                    TestData.clickOnElement(driver, "//button[@id='button-cart']"); // Trebuia sa aiba si asta o fita
                } catch (TimeoutException e) {
                    e.getMessage();
                }

                String expectedText = "shopping cart";
                String actualText = TestData.getTextOnElement(driver, "//a[contains(text(), 'shopping cart')]");
                Assert.assertEquals(actualText, expectedText);
            } case 4 -> // Acesta este un deadline
                    System.err.println("Accest produs nu poate fi adaugat in cos");
        }
    }

    // De separat
}
