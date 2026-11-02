package tests;

import config.BrowserStackConfig;
import io.appium.java_client.AppiumBy;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.appium.java_client.AppiumBy.*;
import static io.qameta.allure.Allure.step;

public class SearchTests extends TestBase{

    private final BrowserStackConfig config = ConfigFactory.create(
            BrowserStackConfig.class, System.getProperties());

    @Test
    void successfullSearchTest() {
        step("Type search", () -> {
            $(accessibilityId(config.getSearchButton())).click();
            $(id(config.getSearchInput())).sendKeys("Appium");
        });
        step("Verify content found", () ->
                $$(id(config.getItemTitle()))
                        .shouldHave(sizeGreaterThan(0)));
    }

    @Test
    void anotherSearchTest() {
        step("Type search", () -> {
            $(accessibilityId(config.getSearchButton())).click();
            $(id(config.getSearchInput())).sendKeys("FN Five-seven");
        });
        step("Verify header text", () ->
                $(id(config.getItemTitle()))
                        .shouldHave(text("FN Five-seven")));
    }
}

// //android.widget.TextView[@text="All the world's knowledge"] - заголовок 1 экрана
// //android.widget.Button - кнопка стрелки внизу
// //android.widget.TextView[@text="Data & Privacy"] - заголовок 2 экрана
// //android.widget.TextView[@text="Read in more than 300 languages"] - заголовок 3 экрана
// //android.widget.TextView[@text="Follow your curiosity"] - заголовок 4 экрана
// //android.widget.TextView[@text='Search Wikipedia']
