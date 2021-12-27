package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CardReplenishment {

    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement topUpButton = $("[data-test-id=action-transfer]");

    public CardReplenishment() {
        amountField.shouldBe(visible);
    }

    public DashboardPage replenishment(int amountValue, DataGenerator.CardInfo cardInfo) {
        amountField.setValue(String.valueOf(amountValue));
        fromField.setValue(cardInfo.getCardNumber());
        topUpButton.click();
        return new DashboardPage();
    }
}
