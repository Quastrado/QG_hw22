package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:${platform}.properties"})

public interface BrowserStackConfig extends Config {
    @Key("platformName")
    String getPlatformName();

    @Key("username")
    String getUsername();

    @Key("key")
    String getKey();

    @Key("remoteUrl")
    String getRemoteUrl();

    @Key("app")
    String getApp();

    @Key("device")
    String getDevice();

    @Key("os_version")
    String getVersion();

    @Key("project")
    String getProject();

    @Key("build")
    String getBuild();

    @Key("name")
    String getName();
    // Локаторы для использования на разных платформах
    @Key("locator.searchButton")
    String getSearchButton();

    @Key("locator.searchInput")
    String getSearchInput();

    @Key("locator.itemTitle")
    String getItemTitle();
}