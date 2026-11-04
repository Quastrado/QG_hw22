package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:browserstack.properties"})

public interface BrowserStackConfig extends Config {

    @Key("browserstack.username")
    @DefaultValue("testjenkins_KRVqaP")
    String getUsername();

    @Key("browserstack.key")
    @DefaultValue("EpTDjQ1so96MX2ufkvnu")
    String getKey();

    @Key("browserstack.remote.url")
    @DefaultValue("https://hub.browserstack.com/wd/hub")
    String getRemoteUrl();

    @Key("device.name")
    @DefaultValue("Google Pixel 7 Pro")
    String getDevice();

    @Key("os.version")
    @DefaultValue("13.0")
    String getVersion();

    @Key("app.path")
    @DefaultValue("bs://8ebb863148fde1a05da33c6c065b26a8146ae59d")
    String getApp();

    @Key("project.name")
    @DefaultValue("BrowserStack Sample")
    String getProject();

    @Key("build.name")
    @DefaultValue("browserstack-build-1")
    String getBuild();

    @Key("session.name")
    @DefaultValue("first_test")
    String getName();

    @Key("webdriver.http.factory")
    @DefaultValue("jdk-http-client")
    String getHttpFactory();

}