package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {
    private final SelenideElement heading = $$(".heading").find(exactText("Оплата по карте"));
    private final SelenideElement cardNumberField = $(".input [placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $(".input [placeholder='08']");
    private final SelenideElement yearField  = $(".input [placeholder='22']");
    private final SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cvcField = $(".input [placeholder='999']");
    private final SelenideElement proceedButton = $(".form-field button");
    private final SelenideElement approvedNotification = $(".notification_status_ok");
    private final SelenideElement declinedNotification = $(".notification_status_error");
    private final SelenideElement wrongMonthError = $(withText("Неверно указан срок действия карты"));
    private final SelenideElement wrongYearError = $(withText("Истёк срок действия карты"));
    private final SelenideElement wrongNameError = $(withText("Неверно указано имя владельца карты"));
    private final SelenideElement emptyFieldError = $(withText("Поле обязательно для заполнения"));
    private final SelenideElement wrongCardNumberError = $(withText("Неверный формат"));
    private final SelenideElement wrongCVCError = $(withText("Неверный формат"));

    private final SelenideElement request = $(withText("Отправляем запрос"));


    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public void payment(DataHelper.CardInfo info) {
        cardNumberField.setValue(info.getCardNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwnerName());
        cvcField.setValue(info.getCvc());
        proceedButton.click();
    }

    public void approved() {
        approvedNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void declined() {
        declinedNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void wrongMonth() {
        request.shouldNotBe(visible, Duration.ofSeconds(4));
        wrongMonthError.shouldBe(visible, Duration.ofSeconds(3));
    }

    public void wrongYear() {
        request.shouldNotBe(visible, Duration.ofSeconds(4));
        wrongYearError.shouldBe(visible, Duration.ofSeconds(3));
    }

    public void wrongName() {
        request.shouldNotBe(visible, Duration.ofSeconds(4));
        wrongNameError.shouldBe(visible, Duration.ofSeconds(3));
    }

    public void wrongCVC() {
        request.shouldNotBe(visible, Duration.ofSeconds(4));
        wrongCVCError.shouldBe(visible, Duration.ofSeconds(3));
    }

    public void wrongNumberCard() {
        request.shouldNotBe(visible, Duration.ofSeconds(4));
        wrongCardNumberError.shouldBe(visible, Duration.ofSeconds(3));
    }


    public void emptyField() {
        request.shouldNotBe(visible, Duration.ofSeconds(4));
        emptyFieldError.shouldBe(visible, Duration.ofSeconds(3));
    }


}
