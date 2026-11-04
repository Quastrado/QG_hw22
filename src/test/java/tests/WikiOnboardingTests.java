package tests;

import config.EmulationConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.xpath;

public class WikiOnboardingTests extends TestBase {

    private final EmulationConfig config = ConfigFactory.create(
            EmulationConfig.class, System.getProperties());

    @Test
    void successfulOnboardingTest() {
        step("Verify the first screen header and navigate to the next screen", () -> {
            $(xpath("//android.view.View[@index='1']")).shouldBe(visible);
            $(xpath("//android.widget.Button")).click();
        });
        step("Verify the second screen header and navigate to the next screen", () -> {
            $(xpath("//android.view.View[@index='1']")).shouldBe(visible);
            $(xpath("//android.widget.Button")).click();
        });
        step("Verify the third screen header and navigate to the next screen", () -> {
            $(xpath("//android.view.View[@index='1']")).shouldBe(visible);
            $(xpath("//android.widget.Button")).click();
        });
        step("Verify the fourth screen header and navigate to the landing page", () -> {
            $(xpath("//android.view.View[@index='1']")).shouldBe(visible);
            $(xpath("//android.widget.Button")).click();
        });
        step("Verify the presence of the search field on the landing page", () -> {
            $(xpath("//androidx.compose.ui.platform.ComposeView//android.view.View[1]")).shouldBe(visible);
        });
    }
}

// https://telemost.yandex.ru/j/96450006437030
