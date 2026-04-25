import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginTest {

    @Test
    void test_login_with_incorrect_credentials() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.navigate().to("http://103.139.122.250:4000/login");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement emailField = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("email"))
            );

            emailField.sendKeys("wrong@email.com");
            driver.findElement(By.id("password")).sendKeys("wrongpassword");
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // Check we are still on login page (failed login)
            String currentUrl = driver.getCurrentUrl();
            assert currentUrl.contains("login")
                : "Expected to stay on login page but got: " + currentUrl;

        } finally {
            driver.quit();
        }
    }
}
