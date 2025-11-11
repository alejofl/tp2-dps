package com.rt.springboot.app.boot.e2e;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
public class CreateClientE2ETest extends E2ETestBase {

    @Test
    public void shouldCreateClient() throws InterruptedException {
        // Log in first
        driver.get(baseUrl() + "/login");
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.cssSelector("button[type=submit]")).click();

        // Wait for list page
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> d.getPageSource().contains("Listado de Clientes") || d.getPageSource().contains("Customer List"));

        // Navigate to create form
        WebElement createBtn = driver.findElement(By.cssSelector("a[href='/form']"));
        createBtn.click();

        // Wait for form
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("firstName")));

        // Fill form
        driver.findElement(By.name("firstName")).sendKeys("E2EFirst");
        driver.findElement(By.name("lastName")).sendKeys("E2ELast");
        driver.findElement(By.name("email")).sendKeys("e2e@example.com");
        WebElement dateInput = driver.findElement(By.name("createdAt"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '2020-01-01';", dateInput);

        driver.findElement(By.cssSelector("form[action='/form'] button[type=submit]")).click();
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);

        // Wait for list and presence of new email
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> d.getPageSource().contains("e2e@example.com"));

        assertTrue(driver.getPageSource().contains("e2e@example.com"));
    }
}
