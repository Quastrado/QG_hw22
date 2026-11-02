package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.BrowserStackDriver;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;


public class TestBase {

    @BeforeAll
    static void beforeAll() {
        Configuration.browser = BrowserStackDriver.class.getName();
        Configuration.browserSize = null;
        Configuration.timeout = 30000;
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }

    @AfterEach
    void addAttachments() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            try {
                // Безопасно получаем sessionId
                String sessionId = Selenide.sessionId().toString();

                Attach.pageSource();
                // Закрываем драйвер ДО добавления видео, или после — зависит от логики Attach.addVideo
                closeWebDriver();
                Attach.addVideo(sessionId);
            } catch (Exception e) {
                System.err.println("Ошибка при сборе вложений: " + e.getMessage());
                closeWebDriver(); // На всякий случай закрываем, если он повис
            }
        } else {
            System.out.println("Драйвер не был запущен, пропускаем сбор вложений.");
        }
    }
}
