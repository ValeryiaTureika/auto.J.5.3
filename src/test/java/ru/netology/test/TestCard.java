package ru.netology.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.data.DataGenerator;
import ru.netology.page.LoginPage;
import ru.netology.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCard {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataGenerator.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void transactionFromSecondToFirstCard() {
        val dashboardPage = new DashboardPage();
        int amountValue = 5000;
        val expectedResultSecondCard = dashboardPage.getSecondCardBalance() - amountValue;
        val expectedResultFirstCard = dashboardPage.getFirstCardBalance() + amountValue;
        val cardReplenishment = dashboardPage.firstCardDeposit();
        cardReplenishment.replenishment(amountValue, DataGenerator.getSecondCardInf());
        val actualResultsFirstCard = dashboardPage.getFirstCardBalance();
        val actualResultsSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(expectedResultFirstCard, actualResultsFirstCard);
        assertEquals(expectedResultSecondCard, actualResultsSecondCard);
    }

    @Test
    void transactionFromFirstToSecondCard() {
        val dashboardPage = new DashboardPage();
        int amountValue = 3000;
        val expectedResultFirstCard = dashboardPage.getFirstCardBalance() - amountValue;
        val expectedResultSecondCard = dashboardPage.getSecondCardBalance() + amountValue;
        val cardReplenishment = dashboardPage.secondCardDeposit();
        cardReplenishment.replenishment(amountValue, DataGenerator.getFirstCardInf());
        val actualResultsFirstCard = dashboardPage.getFirstCardBalance();
        val actualResultsSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(expectedResultFirstCard, actualResultsFirstCard);
        assertEquals(expectedResultSecondCard, actualResultsSecondCard);
    }
}
