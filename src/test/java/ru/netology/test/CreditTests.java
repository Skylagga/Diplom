package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;
import ru.netology.page.OrderPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTests {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldSuccessfullySendFormWithApprovedCard() {
        var cardInfo = DataHelper.getValidCardInfo("approved");
        var paymentPage = OrderPage.goToCredit();
        paymentPage.credit(cardInfo);
        paymentPage.approved();
        assertEquals("APPROVED", DbHelper.getCreditEntity());
    }

    @Test
    void shouldUnsuccessfullySendFormWithDeclinedCard() {
        var cardInfo = DataHelper.getValidCardInfo("declined");
        var paymentPage = OrderPage.goToCredit();
        paymentPage.credit(cardInfo);
        paymentPage.declined();
        assertEquals("DECLINED", DbHelper.getCreditEntity());
    }

    @Test
    void shouldGetNoticeWithWrongCardNumber() {
        var cardInfo = DataHelper.getInvalidFormatCardInfo("4444 4444 4444 4443");
        var paymentPage = OrderPage.goToCredit();
        paymentPage.credit(cardInfo);
        paymentPage.declined();
    }

    @Test
    void shouldGetNoticeWithInvalidName() {
        var cardInfo = DataHelper.getInvalidNameCardInfo("approved");
        var paymentPage = OrderPage.goToCredit();
        paymentPage.credit(cardInfo);
        paymentPage.wrongName();
    }

    @Test
    void shouldGetNoticeWithInvalidMonth() {
        var cardInfo = DataHelper.getInvalidMonthCardInfo("approved");
        var paymentPage = OrderPage.goToCredit();
        paymentPage.credit(cardInfo);
        paymentPage.wrongMonth();
    }

    @Test
    void shouldGetNoticeWithInvalidYear() {
        var cardInfo = DataHelper.getInvalidYearCardInfo("approved");
        var paymentPage = OrderPage.goToCredit();
        paymentPage.credit(cardInfo);
        paymentPage.wrongYear();
    }

    @Test
    void shouldGetNoticeWithInvalidCVC() {
        var cardInfo = DataHelper.getInvalidCVCCardInfo("approved");
        var paymentPage = OrderPage.goToCredit();
        paymentPage.credit(cardInfo);
        paymentPage.wrongCVC();
    }

    @Test
    void shouldGetNoticeWithEmptyYear() {
        var cardInfo = DataHelper.getInvalidYearEmptyCardInfo("approved");
        var paymentPage = OrderPage.goToCredit();
        paymentPage.credit(cardInfo);
        paymentPage.emptyField();
    }

    @Test
    void shouldGetNoticeWithEmptyCVC() {
        var cardInfo = DataHelper.getInvalidCVCEmptyCardInfo("approved");
        var paymentPage = OrderPage.goToCredit();
        paymentPage.credit(cardInfo);
        paymentPage.emptyField();
    }

    @Test
    void shouldGetNoticeWithEmptyNumberCard() {
        var cardInfo = DataHelper.getInvalidFormatCardInfo("    ");
        var paymentPage = OrderPage.goToCredit();
        paymentPage.credit(cardInfo);
        paymentPage.emptyField();
    }

    @Test
    void shouldGetNoticeWithEmptyMonth() {
        var cardInfo = DataHelper.getInvalidMonthEmptyCardInfo("approved");
        var paymentPage = OrderPage.goToCredit();
        paymentPage.credit(cardInfo);
        paymentPage.emptyField();
    }

}
