package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.generate.Generate;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.api.Api.signUp;
import static ru.netology.generate.Generate.*;

public class TestsApiServices {

    Generate.UserInfo generator = getUserInfo();

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

        registration(generator.getLogin(), generator.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    public void shouldNotSignIfInvalValueLogin() {
        signUp(generator);
        registration(getInvalidLogin(), generator.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    public void shouldNotSignBlocUser() {

        generator.setStatus("blocked");
        signUp(generator);
        registration(generator.getLogin(), generator.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    public void shouldSignExistentUser() {

        signUp(generator);
        registration(generator.getLogin(), generator.getPassword());
        $(byText("Личный кабинет")).shouldBe(visible);
    }


}
