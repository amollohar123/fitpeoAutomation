	package Automation.fitpeoTask;
	import java.awt.AWTException;
	import java.time.Duration;
	import java.util.List;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.Assert;
	import io.github.bonigarcia.wdm.WebDriverManager;

	public class fitpeoAutomationTesting {

	    public static void main(String[] args) throws InterruptedException, AWTException {

	        WebDriver driver = setUpDriver();
	        
	        try {
	            // Test Case 1: Navigate to the FitPeo Homepage
	            try {
	                navigateToHomepage(driver);
	                System.out.println("Test Case 1: Navigate to FitPeo Homepage - PASSED");
	            } catch (AssertionError | Exception e) {
	                System.out.println("Test Case 1: Navigate to FitPeo Homepage - FAILED");
	            }

	            // Test Case 2: Navigate to the Revenue Calculator Page
	            try {
	            	navigateToRevenueCalculator(driver);
	                System.out.println("Test Case 2: Navigate to Revenue Calculator Page - PASSED");
	            } catch (AssertionError | Exception e) {
	                System.out.println("Test Case 2: Navigate to Revenue Calculator Page - FAILED");
	            }

	            // Test Case 3: Scroll Down to the Slider section
	            try {
	                scrollToSliderSection(driver);
	                System.out.println("Test Case 3: Scroll Down to the Slider section - PASSED");
	            } catch (AssertionError | Exception e) {
	                System.out.println("Test Case 3: Scroll Down to the Slider section - FAILED");
	            }

	            // Test Case 4: Adjust the Slider
	            try {
	                adjustSlider(driver, 820);
	                validateSliderValue(driver, 820);
	                System.out.println("Test Case 4: Adjust the Slider to 820 - PASSED");
	            } catch (AssertionError | Exception e) {
	                System.out.println("Test Case 4: Adjust the Slider to 820 - FAILED");
	            }

	            // Test Case 5: Update the Text Field
	            try {
	                updateSliderByTextField(driver, 560);
	                System.out.println("Test Case 5: Update the Text Field to 560 - PASSED");
	            } catch (AssertionError | Exception e) {
	                System.out.println("Test Case 5: Update the Text Field to 560 - FAILED");
	            }

	            // Test Case 6: Validate Slider Value after updating to 560
	            try {
	                validateSliderValue(driver, 560);
	                System.out.println("Test Case 6: Validate Slider Value to 560 - PASSED");
	            } catch (AssertionError | Exception e) {
	                System.out.println("Test Case 6: Validate Slider Value to 560 - FAILED");
	            }
	            	
	            // Updating slider value agin to 820 to get reimbursement amount 110700
	                updateSliderByTextField(driver, 820);
	            
	            // Test Case 7: Select CPT Codes
	            try {
	                selectCPTCodes(driver);
	                System.out.println("Test Case 7: Select CPT Codes - PASSED");
	            } catch (AssertionError | Exception e) {
	                System.out.println("Test Case 7: Select CPT Codes - FAILED");
	            }

	            // Test Case 8: Validate Total Recurring Reimbursement
	            try {
	                validateReimbursementAmount(driver, 110700);
	                System.out.println("Test Case 8: Validate Reimbursement Amount - PASSED");
	            } catch (AssertionError | Exception e) {
	                System.out.println("Test Case 8: Validate Reimbursement Amount - FAILED");
	            }

	        } finally {
	            Thread.sleep(3000);
	            driver.quit();
	        }
	    }

	    // Setting up WebDriver and initializing ChromeDriver
	    public static WebDriver setUpDriver() {
	        WebDriverManager.chromedriver().setup();
	        WebDriver driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        return driver;
	    }

	    // Test Case 1: Navigating to FitPeo Homepage
	    public static void navigateToHomepage(WebDriver driver) throws InterruptedException {
	        driver.get("https://www.fitpeo.com/");
	        Assert.assertTrue(driver.getTitle().contains("Remote Patient Monitoring (RPM) - fitpeo.com"), "Home page not loaded correctly");
	    }

	    // Test Case 2: Navigating to the Revenue Calculator page
	    public static void navigateToRevenueCalculator(WebDriver driver) throws InterruptedException {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Revenue Calculator')]"))).click();
	        Thread.sleep(5000);
	        Assert.assertTrue(driver.getCurrentUrl().contains("calculator"), "Failed to navigate to Revenue Calculator page");
	    }

	    // Test Case 3: Scroll down to slider section
	    public static void scrollToSliderSection(WebDriver driver) throws InterruptedException {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("window.scrollBy(0,450)", ""); // Scroll down to the slider
	        Thread.sleep(2000);
	    }

	    // Test Case 4: Adjusting the slider to a specified value
	    public static void adjustSlider(WebDriver driver, int value) throws InterruptedException {
	        WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
	        Actions actions = new Actions(driver);
	            actions.dragAndDropBy(slider, 93, 0).build().perform(); // Example adjustment
	            for (int i = 0; i < 3; i++) {
	                slider.sendKeys(Keys.ARROW_RIGHT);
	            }
	            Thread.sleep(2000);
	    }
	    
	        
	    

	    // Test Case 5: Updating the slider by entering a value into the text field
	    public static void updateSliderByTextField(WebDriver driver, int newValue) throws InterruptedException {
	        WebElement inputField = driver.findElement(By.xpath("//input[@min='0' and @max='2000' and @type='number']"));
	        inputField.click();
	        Actions actions = new Actions(driver);
	        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).keyDown(Keys.DELETE)
	                .sendKeys(String.valueOf(newValue)).build().perform();
	        Thread.sleep(2000);
	    }

	    // Test Case 6: Validate the slider value
	    public static void validateSliderValue(WebDriver driver, int expectedValue) {
	        WebElement valueDisplayed = driver.findElement(By.xpath("//input[@type='number' and @min='0' and @max='2000']"));
	        int actualValue = Integer.parseInt(valueDisplayed.getAttribute("value"));
	        Assert.assertEquals(actualValue, expectedValue, "The slider value does not match.");
	    }

	    // Test Case 7: Select specific CPT codes
	    public static void selectCPTCodes(WebDriver driver) throws InterruptedException {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("window.scrollBy(0,350)", ""); // Scroll to CPT codes section
	        Thread.sleep(2000);

	        List<WebElement> cptProgramsList = driver.findElements(By.xpath("//div[@class='MuiBox-root css-1p19z09']/div/p[contains(text(),'CPT-')]"));

	        for (WebElement x : cptProgramsList) {
	            String cptCode = x.getText().trim();

	            switch (cptCode.toUpperCase()) {
	                case "CPT-99091":
	                    driver.findElement(By.xpath("(//input[@class='PrivateSwitchBase-input css-1m9pwf3'])[1]")).click();
	                    break;
	                case "CPT-99453":
	                    driver.findElement(By.xpath("(//input[@class='PrivateSwitchBase-input css-1m9pwf3'])[2]")).click();
	                    break;
	                case "CPT-99454":
	                    driver.findElement(By.xpath("(//input[@class='PrivateSwitchBase-input css-1m9pwf3'])[3]")).click();
	                    break;
	                case "CPT-99474":
	                    driver.findElement(By.xpath("(//input[@class='PrivateSwitchBase-input css-1m9pwf3'])[8]")).click();
	                    break;
	            }
	        }
	    }

	    // Test Case 8: Validate the reimbursement amount is correct
	    public static void validateReimbursementAmount(WebDriver driver, int expectedAmount) {
	        WebElement reimbursementElement = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/header[1]/div[1]/p[4]/p[1]"));
	        String amountText = reimbursementElement.getText().split("\\$")[1].trim();
	        int actualAmount = Integer.parseInt(amountText);
	        Assert.assertEquals(actualAmount, expectedAmount, "Reimbursement amount does not match.");
	    }
	}


