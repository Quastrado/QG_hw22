package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.RealDeviceConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RealDeviceDriver implements WebDriverProvider {

    private static final RealDeviceConfig config = ConfigFactory.create(
            RealDeviceConfig.class, System.getProperties());

    public WebDriver createDriver(Capabilities capabilities) {

        try {
            Runtime.getRuntime().exec("adb shell pm clear org.wikipedia.alpha").waitFor();
            Runtime.getRuntime().exec("adb shell am start -n org.wikipedia.alpha/org.wikipedia.main.MainActivity").waitFor();
            System.out.println("=== Данные приложения Википедии успешно сброшены через ADB ===");
        } catch (Exception e) {
            System.err.println("Не удалось автоматически сбросить данные: " + e.getMessage());
        }

        UiAutomator2Options options = new UiAutomator2Options();

        options
                .setPlatformName(config.platformName())
                .setAutomationName(config.automationName())
                .setDeviceName(config.deviceName())
                .setAppPackage(config.appPackage())
                .setAppActivity(config.appActivity())
//                .setApp(config.appPath()) // Путь к APK, чтобы Appium сам устанавливал его на телефон
                .setAppWaitActivity("*")  // Маска для ожидания любого экрана при старте
                .setNoReset(true)
                .setCapability("appium:dontStopAppOnReset", true);

        try {
            return new AndroidDriver(new URL(config.appiumUrl()), options);
        } catch (MalformedURLException e) {
            System.err.println("!!! КРИТИЧЕСКАЯ ОШИБКА СОЗДАНИЯ ДРАЙВЕРА РЕАЛЬНОГО УСТРОЙСТВА !!!");
            throw new RuntimeException(e);
        }
    }

}
