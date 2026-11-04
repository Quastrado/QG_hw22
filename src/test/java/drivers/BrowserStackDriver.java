package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.BrowserStackConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.aeonbits.owner.ConfigFactory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class BrowserStackDriver implements WebDriverProvider {

    private static final String platform = System.getProperty("platform", "android").toLowerCase();

    protected static BrowserStackConfig browserStackConfig = ConfigFactory.create(BrowserStackConfig.class,
            System.getProperties());

    @NonNull
    @Override
    public WebDriver createDriver(@NonNull Capabilities capabilities) {
        // Использованием MutableCapabilities вместо UiAutomator2Options решает проблему локального парсинга Selenide
        MutableCapabilities caps = new MutableCapabilities();

        // Основные настройки устройства
        caps.setCapability("platformName", "android");
        caps.setCapability("appium:platformVersion", browserStackConfig.getVersion());
        caps.setCapability("appium:deviceName", browserStackConfig.getDevice());
        caps.setCapability("appium:app", browserStackConfig.getApp());
        caps.setCapability("appium:automationName", "UiAutomator2");

        // Настройки для дашборда и авторизации BrowserStack
        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("userName", browserStackConfig.getUsername());
        browserstackOptions.put("accessKey", browserStackConfig.getKey());
        browserstackOptions.put("projectName", browserStackConfig.getProject());
        browserstackOptions.put("buildName", browserStackConfig.getBuild());
        browserstackOptions.put("sessionName", browserStackConfig.getName());
        browserstackOptions.put("appiumVersion", "2.6.0");

        caps.setCapability("bstack:options", browserstackOptions);

        try {
            URL remoteUrl = new URL(browserStackConfig.getRemoteUrl());
            System.out.println("--- ПОПЫТКА ЗАПУСКА ДРАЙВЕРА НА BROWSERSTACK ---");
            return new AndroidDriver(remoteUrl, caps);
        } catch (MalformedURLException e) {
            System.err.println("!!! КРИТИЧЕСКАЯ ОШИБКА ФОРМАТА URL BROWSERSTACK !!!");
            throw new RuntimeException(e);
        } catch (org.openqa.selenium.WebDriverException e) {
            System.err.println("!!! ОШИБКА БЕЗОПАСНОСТИ ИЛИ ПОДКЛЮЧЕНИЯ К BROWSERSTACK !!!");
            String fullMessage = e.getMessage() != null ? e.getMessage() : "Unknown error";
            if (fullMessage.contains("<!doctype")) {
                fullMessage = fullMessage.substring(0, fullMessage.indexOf("<!doctype"));
            }
            System.err.println("Детали ошибки: " + fullMessage.trim());
            throw new RuntimeException("Сессия BrowserStack не создана: " + fullMessage.trim());
        }
    }
}

