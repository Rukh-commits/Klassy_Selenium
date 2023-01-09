package com.example.klassy;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPageTest {
    MainPage mainPage = new MainPage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.headless = true;
        Configuration.timeout = 60;
    }

    @Test
    public void RegisterUser(){
        //Create Random Email
        Random r = new Random();
        int low = 10;
        int high = 100;
        int result = r.nextInt(high-low) + low;
        String email="test_user" + result+"@gmail.com";

        //Testing
        open("http://ec2-35-77-97-46.ap-northeast-1.compute.amazonaws.com:8181/register");
        $(By.id("name")).sendKeys(new CharSequence[]{"User"});
        $(By.id("email")).sendKeys(new CharSequence[]{email});
        $(By.id("password")).sendKeys(new CharSequence[]{"user12345"});
        $(By.id("password_confirmation")).sendKeys(new CharSequence[]{"user12345"});
        $(By.xpath("/html/body/div/div/div[2]/form/div[5]/button")).click();

        String Url = webdriver().driver().getCurrentFrameUrl();
        assertEquals("http://ec2-35-77-97-46.ap-northeast-1.compute.amazonaws.com:8181/redirects",Url);

        webdriver().driver().clearCookies();
    }

    @Test
    public void RegisterPasswordMismatch(){
        //If the password doesn't match, displays error
        open("http://ec2-35-77-97-46.ap-northeast-1.compute.amazonaws.com:8181/register");
        $(By.id("name")).sendKeys(new CharSequence[]{"User"});
        $(By.id("email")).sendKeys(new CharSequence[]{"user20@gmail.com"});
        $(By.id("password")).sendKeys(new CharSequence[]{"user123456"});
        $(By.id("password_confirmation")).sendKeys(new CharSequence[]{"user123"});
        $(By.xpath("/html/body/div/div/div[2]/form/div[5]/button")).click();

        String message= $(By.xpath("/html/body/div/div/div[2]/div/ul/li")).getText();
        Assertions.assertEquals("The password confirmation does not match.",message);
        webdriver().driver().clearCookies();
    }
    @Test
    public void LoginUser(){
        //Login with valid credentials
        open("http://ec2-35-77-97-46.ap-northeast-1.compute.amazonaws.com:8181/login");
        $(By.id("email")).sendKeys(new CharSequence[]{"user@gmail.com"});
        $(By.id("password")).sendKeys(new CharSequence[]{"user1234"});
        $(By.xpath("/html/body/div/div/div[2]/form/div[4]/button")).click();
        String Url = webdriver().driver().getCurrentFrameUrl();
        assertEquals("http://ec2-35-77-97-46.ap-northeast-1.compute.amazonaws.com:8181/redirects",Url);
        webdriver().driver().clearCookies();

    }
    @Test
    public void LoginWrongCredentials(){
        //Login with Invalid Password
        open("http://ec2-35-77-97-46.ap-northeast-1.compute.amazonaws.com:8181/login");
        $(By.id("email")).sendKeys(new CharSequence[]{"user@gmail.com"});
        $(By.id("password")).sendKeys(new CharSequence[]{"user12346789"});
        $(By.xpath("/html/body/div/div/div[2]/form/div[4]/button")).click();
        String message= $(By.xpath("/html/body/div/div/div[2]/div/ul/li")).getText();
        Assertions.assertEquals("These credentials do not match our records.",message);
        webdriver().driver().clearCookies();
    }

    @Test
    public void LogoutUser(){
        //Login with valid credentials
        open("http://ec2-35-77-97-46.ap-northeast-1.compute.amazonaws.com:8181/login");
        $(By.id("email")).sendKeys(new CharSequence[]{"user@gmail.com"});
        $(By.id("password")).sendKeys(new CharSequence[]{"user1234"});
        $(By.xpath("/html/body/div/div/div[2]/form/div[4]/button")).click();
        $(By.xpath("/html/body/header/div/div/div/nav/ul/li[8]/div[2]/nav/div[1]/div/div[2]/div/div/div[1]/span/button")).click();
        $(By.xpath("/html/body/header/div/div/div/nav/ul/li[8]/div[2]/nav/div[1]/div/div[2]/div/div/div[2]/div/form/a")).click();
        String Url = webdriver().driver().getCurrentFrameUrl();
        assertEquals("http://ec2-35-77-97-46.ap-northeast-1.compute.amazonaws.com:8181/",Url);
        webdriver().driver().clearCookies();

    }




}
