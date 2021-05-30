package pl.investadvisor.datascraper.service;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.openqa.selenium.support.ui.Sleeper.SYSTEM_SLEEPER;

@Service
public class WebDriverService {

    @Value("${selenium.url}")
    private String SELENIUM_URL;

    public RemoteWebDriver create() throws MalformedURLException {
        return new RemoteWebDriver(new URL(SELENIUM_URL), new MutableCapabilities(new ChromeOptions()));
    }

    public boolean isElementExisting(RemoteWebDriver webDriver, By selector) {
        try {
            webDriver.findElement(selector);
        } catch (NoSuchElementException exception) {
            return false;
        }

        return true;
    }

    public void sleep(Integer seconds) throws InterruptedException {
        SYSTEM_SLEEPER.sleep(Duration.ofSeconds(seconds));
    }
}