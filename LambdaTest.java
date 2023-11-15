package Exam;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;

import org.testng.annotations.*;

import java.net.MalformedURLException;

import java.net.URL;

import java.time.Duration;

import java.util.concurrent.TimeUnit;

public class LambdaTest {
    private WebDriver driver;

    private String ltUsername = "koushika.r";

    private String ltAccessKey = "dezDcXTprnvscRmTPetvZdtbYrgbhQyjnngh6wMFzYZoVXpzQo";

    private String gridURL = "https://koushika.r:dezDcXTprnvscRmTPetvZdtbYrgbhQyjnngh6wMFzYZoVXpzQo@hub.lambdatest.com/wd/hub";

 

    @BeforeClass

    @Parameters({"browserName", "browserVersion", "platformName"})

    public void setUp(String browserName, String browserVersion, String platformName) throws MalformedURLException {

        DesiredCapabilities capabilities;

 

        if (browserName.equalsIgnoreCase("chrome")) {

            ChromeOptions chromeOptions = new ChromeOptions();

            capabilities = new DesiredCapabilities();

            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        } else if (browserName.equalsIgnoreCase("edge")) {

            EdgeOptions edgeOptions = new EdgeOptions();

            capabilities = new DesiredCapabilities();

            capabilities.setCapability(EdgeOptions.CAPABILITY, edgeOptions);

        } else {

            throw new IllegalArgumentException("Unsupported browser: " + browserName);

        }

 

        capabilities.setCapability("user", ltUsername);

        capabilities.setCapability("accessKey", ltAccessKey);

        capabilities.setCapability("browserName", browserName);

        capabilities.setCapability("browserVersion", browserVersion);

        capabilities.setCapability("platformName", platformName);

        capabilities.setCapability("build", "LambdaTest Parallel Test");

        capabilities.setCapability("name", "Test");

 

        driver = new RemoteWebDriver(new URL(gridURL), capabilities);

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.manage().window().maximize();

    }

 

    @Test

    public void testScenario() throws InterruptedException {

        driver.get("https://www.lambdatest.com");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement cookies = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Allow Cookie']")));

        cookies.click();

 

        WebElement seeAllIntegrations = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("SEE ALL INTEGRATIONS")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('target', '_blank');", seeAllIntegrations);

        seeAllIntegrations.click();

 

        String mainWindowHandle = driver.getWindowHandle();

        for (String windowHandle : driver.getWindowHandles()) {

            if (!windowHandle.equals(mainWindowHandle)) {

                driver.switchTo().window(windowHandle);

                break;

            }

        }

 

        String expectedURL = "https://www.lambdatest.com/integrations";

        String actualURL = driver.getCurrentUrl();

        Assert.assertEquals(actualURL, expectedURL, "URLs do not match.");

        driver.close();

        driver.switchTo().window(mainWindowHandle);

    }

 

    @AfterClass

    public void tearDown() {

        if (driver != null) {

            driver.quit();

        }

    }


}
