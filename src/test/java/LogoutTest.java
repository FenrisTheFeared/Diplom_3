import client.UserClient;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.junit4.DisplayName;
import client.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobj.*;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static client.User.getRandomUser;
import static org.junit.Assert.assertEquals;
import static pageobj.LoginPage.LOGIN_PAGE_URL;
import static pageobj.MainPage.MAIN_PAGE_URL;

public class LogoutTest {
    MainPage mainPage;

    LoginPage loginPage;

    ProfilePage profilePage;

    User user;

    UserClient userClient;

    @Before
    public void init() {
        user = getRandomUser();
        userClient = new UserClient();
        userClient.createUser(user);
        Configuration.startMaximized = true;
        mainPage = open(MAIN_PAGE_URL, MainPage.class);
    }

    @After
    public void deleteUser() {
        userClient.deleteUser(user);
    }

    @Test
    @DisplayName("Проверка выхода из профиля после авторизации")
    public void shouldBeLogout() {
        loginPage = mainPage.clickLoginButton();
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        mainPage = loginPage.clickLogin();
        profilePage = mainPage.clickPersonalAccountButton();
        profilePage.clickLogout();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(LOGIN_PAGE_URL, currentUrl);
        closeWindow();
    }
}