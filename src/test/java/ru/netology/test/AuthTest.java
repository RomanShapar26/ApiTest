package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.api.Api;
import ru.netology.generate.Generate;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.api.Api.signUp;
import static ru.netology.generate.Generate.*;

public class AuthTest {


    Generate.UserInfo generator0 = Generate.getUserInfoActiv();
    Generate.UserInfo generator1 = Generate.getUserInfoBlocked();


    @BeforeEach
    public void openPage() {
        open("http://localhost:9999");
    }

    public void registration(String login, String password) {
        $("[name=login]").setValue(login);
        $("[name=password]").setValue(password);
        $(".button").click();
    }

    @Test
    public void shouldNotSignNotExistentUser() {

        registration(generator1.getLogin(), generator1.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    public void shouldNotSignIfInvalValueLogin() {
        signUp(generator1);
        registration(Generate.getInvalidLogin(), generator1.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    public void shouldNotSignBlocUser() {
        Api.signUp(generator1);
        registration(generator1.getLogin(), generator1.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    public void shouldSignExistentUser() {

        signUp(generator0);
        registration(generator0.getLogin(), generator0.getPassword());
        $(byText("Личный кабинет")).shouldBe(visible);
    }


}
