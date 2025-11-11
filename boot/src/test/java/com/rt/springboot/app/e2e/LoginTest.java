package com.rt.springboot.app.e2e;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
public class LoginTest extends E2ETestBase {
    @Test
    public void shouldLoginWithValidCredentials() throws InterruptedException {
        driver.get(baseUrl() + "/login");

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        username.sendKeys("admin");
        password.sendKeys("12345");

        WebElement submit = driver.findElement(By.cssSelector("button[type=submit]"));
        submit.click();

        // Wait briefly for redirect
        Thread.sleep(500);

        // After login, the Sign Out button should be present
        boolean signedIn = driver.getPageSource().contains("Sign Out") || driver.getPageSource().contains("Cerrar Sesión");
        boolean userLoggedIn = (driver.getPageSource().contains("Usuario logueado:") || driver.getPageSource().contains("Logged user:")) && driver.getPageSource().contains("admin");
        assertTrue(signedIn, "Expected to find Sign Out on page after login");
        assertTrue(userLoggedIn, "Expected to find logged user info on page after login");
    }
}
