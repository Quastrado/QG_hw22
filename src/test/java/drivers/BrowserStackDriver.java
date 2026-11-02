package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.BrowserStackConfig;
import io.appium.java_client.AppiumDriver;
import org.aeonbits.owner.ConfigFactory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.util.HashMap;

public class BrowserStackDriver implements WebDriverProvider {

    private static final String platform = System.getProperty("platform", "android").toLowerCase();

    protected static BrowserStackConfig browserStackConfig = ConfigFactory.create(BrowserStackConfig.class,
            System.getProperties());


    @NonNull
    @Override
    public WebDriver createDriver(@NonNull Capabilities capabilities) {
        MutableCapabilities caps = new MutableCapabilities();

        if (platform.equals("ios")) {
            caps.setCapability("appium:automationName", "XCUITest");
        } else {
            caps.setCapability("appium:automationName", "UiAutomator2");
        }

        caps.setCapability("platformName", platform);
        caps.setCapability("appium:deviceName", browserStackConfig.getDevice());
        caps.setCapability("appium:platformVersion", browserStackConfig.getVersion());
        caps.setCapability("appium:app", browserStackConfig.getApp());

        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("userName", browserStackConfig.getUsername());
        browserstackOptions.put("accessKey", browserStackConfig.getKey());
        browserstackOptions.put("projectName", browserStackConfig.getProject());
        browserstackOptions.put("buildName", browserStackConfig.getBuild());
        browserstackOptions.put("sessionName", browserStackConfig.getName());

        browserstackOptions.put("appiumVersion", "2.6.0");
        caps.setCapability("bstack:options", browserstackOptions);

        try{
            URL remoteUrl = new URL(browserStackConfig.getRemoteUrl());
            System.out.println("--- ПОПЫТКА ЗАПУСКА ДРАЙВЕРА НА BROWSERSTACK ---");
            AppiumDriver driver = new AppiumDriver(remoteUrl, caps);
            System.out.println("--- ДРАЙВЕР УСПЕШНО СОЗДАН ---");
            return driver;
        } catch (Throwable t) {
            System.err.println("!!! КРИТИЧЕСКАЯ ОШИБКА СОЗДАНИЯ ДРАЙВЕРА BROWSERSTACK !!!");
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }
}
