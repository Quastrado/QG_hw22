package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.BrowserStackDriver;
import drivers.EmulationDriver;
import drivers.RealDeviceDriver;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;


public class TestBase {

    public static String deviceHost = System.getProperty("deviceHost", "browserstack");

    @BeforeAll
    static void beforeAll() {
        // Динамически выбираем провайдер драйвера
        if ("browserstack".equalsIgnoreCase(deviceHost)) {
            Configuration.browser = BrowserStackDriver.class.getName();
            System.setProperty("selenide.browser", drivers.BrowserStackDriver.class.getName());
        } else if ("emulation".equalsIgnoreCase(deviceHost)) {
            Configuration.browser = EmulationDriver.class.getName();
            System.setProperty("selenide.browser", drivers.EmulationDriver.class.getName());
        } else if ("real".equalsIgnoreCase(deviceHost)) {
            Configuration.browser = RealDeviceDriver.class.getName();
            System.setProperty("selenide.browser", drivers.RealDeviceDriver.class.getName());
        }
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
        if (WebDriverRunner.hasWebDriverStarted() && Selenide.sessionId() != null) {
            try {
                String sessionId = Selenide.sessionId().toString();

                // Снимаем исходный код страницы только если сессия активна
                Attach.pageSource();

                // 2. Видео прикрепляем СТРОГО только для BrowserStack!
                // Локальный эмулятор не умеет отдавать видео по sessionId через API BrowserStack,
                // из-за чего этот метод локально всегда будет падать с ошибкой 404!
                if ("browserstack".equalsIgnoreCase(deviceHost)) {
                    Attach.addVideo(sessionId);
                }
            } catch (Exception e) {
                System.err.println("Ошибка при сборе Allure-вложений: " + e.getMessage());
            } finally {
                // 3. Закрываем драйвер в любом случае, даже если вложения упали
                closeWebDriver();
            }
        } else {
            System.out.println("Сессия не была создана. Принудительно очищаем контекст Selenide.");
            closeWebDriver(); // Очищаем потоки, чтобы не было ошибки "No webdriver is bound" в следующем тесте
        }
    }
}
