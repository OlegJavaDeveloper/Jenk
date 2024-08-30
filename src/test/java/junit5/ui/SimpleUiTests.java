package junit5.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class SimpleUiTests {
    private WebDriver driver;
    private String downloadFolder = System.getProperty("User.dir") + File.separator + "build" + File.separator + "downloadFiles";
    private String k;
    private String a;

    @BeforeAll
    public static void downloadDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        Map<String, String> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFolder);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @AfterEach
    public void closeDriver() {
        driver.close();
    }

    @Test
    public void simpleUiTest() {
        String expectedName = "Thomas Anderson";
        String expectedEmail = "tomas@matrix.ru";
        String expectedCurrentAddress = "USA Los Angeles";
        String expectedPermanentAddress = "USA Miami";

        driver.get("http://85.192.34.140:8081");
        WebElement elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementsCard.click();

        WebElement elementTextBox = driver.findElement(By.xpath("//span[text()='Text Box']"));
        elementTextBox.click();

        WebElement fullName = driver.findElement(By.id("userName"));
        WebElement eMail = driver.findElement(By.id("userEmail"));
        WebElement currenrAddress = driver.findElement(By.id("currentAddress"));
        WebElement permanentAddress = driver.findElement(By.id("permanentAddress"));
        WebElement submit = driver.findElement(By.id("submit"));

        fullName.sendKeys(expectedName);
        eMail.sendKeys(expectedEmail);
        currenrAddress.sendKeys(expectedCurrentAddress);
        permanentAddress.sendKeys(expectedPermanentAddress);
        submit.click();

        WebElement nameNew = driver.findElement(By.id("name"));
        WebElement emailNew = driver.findElement(By.id("email"));
        WebElement currentAddressNew = driver.findElement(By.xpath("//div[@id='output']//p[@id='currentAddress']"));
        WebElement permanentAddressNew = driver.findElement(By.xpath("//div[@id='output']//p[@id='permanentAddress']"));

        String actualName = nameNew.getText();
        String actualEmail = emailNew.getText();
        String actualCurrentAddress = currentAddressNew.getText();
        String actualPermanentAddress = permanentAddressNew.getText();

        Assertions.assertTrue(actualName.contains(expectedName));
        Assertions.assertTrue(actualEmail.contains(expectedEmail));
        Assertions.assertTrue(actualCurrentAddress.contains(expectedCurrentAddress));
        Assertions.assertTrue(actualPermanentAddress.contains(expectedPermanentAddress));

    }


    @Test
    public void uploadAndDownloadTest() {
        driver.get("http://85.192.34.140:8081");
        WebElement elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementsCard.click();

        WebElement elementTextBox = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        elementTextBox.click();

        WebElement uploadButton = driver.findElement(By.id("uploadFile"));
        uploadButton.sendKeys(System.getProperty("user.dir") + "/src/test/resources/sticker.png");

        WebElement uploadedFakePath = driver.findElement(By.id("uploadedFilePath"));
        Assertions.assertTrue(uploadedFakePath.getText().contains("sticker.png"));
    }


    @Test
    public void downloadTest() {
        driver.get("http://85.192.34.140:8081");
        WebElement elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementsCard.click();

        WebElement elementTextBox = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        elementTextBox.click();

        WebElement downloadButton = driver.findElement(By.id("downloadButton"));
        downloadButton.click();

        int a = 1;


    }

    @Test
    public void sliderTest() {
        driver.get("http://85.192.34.140:8081");
        WebElement elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Widgets']"));
        elementsCard.click();

        WebElement elementTextBox = driver.findElement(By.xpath("//span[text()='Slider']"));
        elementTextBox.click();

        WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));

        Actions action = new Actions(driver);
        action.dragAndDropBy(slider, 50, 0).build().perform();
    }

}
