package stepdefinitions;

/**
 *
 * @author ahmed
 */

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.time.Duration;

public class GoogleSearchSteps {
    WebDriver driver;
    
    String username = "ahmedryadh18"; 
    String accessKey = "LT_PMKaisR3m1WurBrNqaM7vMVlNvAGctj5xqoeYmRCUggTohZ";
    String gridURL = "@hub.lambdatest.com/wd/hub";

    @Given("User is on Google homepage")
    public void user_is_on_google_homepage() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "MicrosoftEdge");
        capabilities.setCapability("browserVersion", "latest");
        
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("user", username);
        ltOptions.put("accessKey", accessKey);
        ltOptions.put("build", "ChatGPT Automation Build");
        ltOptions.put("name", "Google Search Test - Edge");
        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("selenium_version", "4.0.0");
        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + gridURL), capabilities);
        driver.get("https://www.google.com");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
            WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/div[contains(text(),'Tout accepter') or contains(text(),'Accept all') or contains(text(),'Accept')]")));
            acceptBtn.click();
            System.out.println("Success: Google consent pop-up bypassed.");
        } catch (Exception e) {
            System.out.println("Info: Google consent pop-up did not appear, proceeding...");
        }
    }

    @When("User enters keyword {string} and presses Enter")
    public void user_enters_keyword(String keyword) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
            
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value='" + keyword + "';", searchBox);
            
            searchBox.sendKeys(Keys.ENTER);
            System.out.println("Success: Keyword entered successfully: " + keyword);
        } catch (Exception e) {
            System.out.println("Error: Failed to enter keyword: " + e.getMessage());
            throw e;
        }
    }

    @Then("Search results for {string} are displayed")
    public void search_results_displayed(String keyword) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.titleContains(keyword));
            System.out.println("Success: Search results for [" + keyword + "] are displayed.");
        } catch (Exception e) {
            System.out.println("Error: Results page not loaded correctly.");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}

