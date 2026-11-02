package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.EmulationConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class EmulationDriver implements WebDriverProvider {

    private static final EmulationConfig config = ConfigFactory.create(
            EmulationConfig.class, System.getProperties());

    public WebDriver createDriver(Capabilities capabilities) {
        UiAutomator2Options options = new UiAutomator2Options();

        options
                .setPlatformName(config.platformName())
                .setAutomationName(config.automationName())
                .setDeviceName(config.deviceName())
                .setAppPackage(config.appPackage())
                .setAppWaitActivity("*") // Передали маску напрямую текстом
                .setNoReset(false)       // Передали boolean напрямую
                .setFullReset(false);    // Передали boolean напрямую

        // 3. Настройки сброса данных, чтобы онбординг запускался КАЖДЫЙ РАЗ заново
        options.setNoReset(false);
        options.setFullReset(false);

        try{
            return new AndroidDriver(new URL(config.appiumUrl()), options);
        } catch (MalformedURLException e) {
            System.err.println("!!! КРИТИЧЕСКАЯ ОШИБКА СОЗДАНИЯ ДРАЙВЕРА ЭМУЛЯТОРА !!!");
            throw new RuntimeException(e);
        }
    }
}