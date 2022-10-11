import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class SharelaneTest {
    /* 1. Открыть браузер https://www.sharelane.com/cgi-bin/register.py
     2. Ввести ZIP code
     3. Нажать кнопку Продолжить
     4. Нажать кнопку Регистрация
     5. Проверить сообщение
     */
    WebDriver driver;

    @Test
    public void verifyRegistrationPageTest() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.xpath("//input[@name='zip_code']")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@value='Continue']")).click();
        driver.findElement(By.xpath("//input[@value='Register']")).click();
        String actualText = driver.findElement(By.xpath("//span[@class='error_message']")).getText();
        Assert.assertEquals(actualText, "Oops, error on page. Some of your fields have invalid data or" +
                " email was previously used");
    }

    // negative: test without required First Name field
    @Test
    public void verifyRegistrationWithoutRequiredFirstNameFieldTest() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.xpath("//input[@name='zip_code']")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@value='Continue']")).click();

        driver.findElement(By.xpath("//input[@name='last_name']")).sendKeys("Fox");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("Lo.fox@gmail.com");
        driver.findElement(By.xpath("//input[@name='password1']")).sendKeys("fox2121");
        driver.findElement(By.xpath("//input[@name='password2']")).sendKeys("fox2121");
        driver.findElement(By.xpath("//input[@value='Register']")).click();
        String actualText = driver.findElement(By.xpath("//span[@class='error_message']")).getText();
        Assert.assertEquals(actualText, "Oops, error on page. Some of your fields have invalid data" +
                " or email was previously used");
    }

    // negative: test without passwords in the Password and Confirm password fields
    @Test
    public void verifyRegistrationWithoutPasswordAndConfirmPasswordFieldsTest() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.xpath("//input[@name='zip_code']")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@value='Continue']")).click();

        driver.findElement(By.xpath("//input[@name='first_name']")).sendKeys("Mari");
        driver.findElement(By.xpath("//input[@name='last_name']")).sendKeys("Fox");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("Lo.fox@gmail.com");
        driver.findElement(By.xpath("//input[@value='Register']")).click();
        String actualText = driver.findElement(By.xpath("//span[@class='error_message']")).getText();
        Assert.assertEquals(actualText, "Oops, error on page. Some of your fields have invalid data" +
                " or email was previously used");
    }

    // positive: test with valid data in all fields
    @Test
    public void verifySuccessfulRegistrationTest() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.xpath("//input[@name='zip_code']")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@value='Continue']")).click();

        driver.findElement(By.xpath("//input[@name='first_name']")).sendKeys("Mari");
        driver.findElement(By.xpath("//input[@name='last_name']")).sendKeys("Fox");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("Lo.fox@gmail.com");
        driver.findElement(By.xpath("//input[@name='password1']")).sendKeys("fox2121");
        driver.findElement(By.xpath("//input[@name='password2']")).sendKeys("fox2121");
        driver.findElement(By.xpath("//input[@value='Register']")).click();
        String actualText = driver.findElement(By.xpath("//span[@class='confirmation_message']")).getText();
        Assert.assertEquals(actualText, "Account is created!");
    }

    @AfterClass
    public void closeBrowser(){
        driver.quit();
    }
}
