package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/emulation.properties" // путь к файлу с настройками в src/test/resources
})

public interface EmulationConfig extends Config {

    @Key("platform.name")
    @DefaultValue("Android")
    String platformName();

    @Key("automation.name")
    @DefaultValue("UiAutomator2")
    String automationName();

    @Key("device.name")
    @DefaultValue("emulator-5554")
    String deviceName();

    @Key("app.package")
    @DefaultValue("org.wikipedia.alpha")
    String appPackage();

    @Key("app.activity")
    @DefaultValue("org.wikipedia.onboarding.InitialOnboardingActivity")
    String appActivity();

    @Key("appium.url")
    @DefaultValue("http://127.0.0.1:4723/wd/hub")
    String appiumUrl();

}
