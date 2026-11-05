package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/real_device.properties"
})

public interface RealDeviceConfig extends Config {

    @Key("platform.name")
    String platformName();

    @Key("automation.name")
    String automationName();

    @Key("device.name")
    String deviceName();

    @Key("app.url")
    @DefaultValue("https://github.com/wikimedia/apps-android-wikipedia")
    String appUrl();

    @Key("app.name")
    @DefaultValue("app-alpha-universal-release.apk")
    String appName();

    @Key("app.package")
    String appPackage();

    @Key("app.activity")
    String appActivity();

    @Key("appium.url")
    @DefaultValue("http://127.0.0.1:4723/wd/hub")
    String appiumUrl();

    @Key("app.path")
    String appPath();
}
