package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.core.Page;
import com.livejournal.uisteps.core.UIBlock;
import com.livejournal.uisteps.core.Url;
import com.livejournal.uisteps.thucydides.Databases.BaseConnect;
import com.livejournal.uisteps.thucydides.Verifications.That;
import com.livejournal.uisteps.utils.ClassEnumerator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.jbehave.ThucydidesJUnitStory;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 *
 * @author ASolyankin
 */
public class WebTest extends ThucydidesJUnitStory {

    @Managed
    WebDriver driver;
    @Steps
    ThucydidesBrowser browser;
    @Steps
    Verifications verifications;
    @Steps
    Databases databases;

    private ClassEnumerator classEnumerator = new ClassEnumerator("com.livejournal.uitests.pages");

    private void openBrowser() {
        if (!browser.isOpened()) {
            browser.setDriver(driver);
            browser.setStepLibraryFactory(new StepLibraryFactory());
            browser.setInitializer(new Initializer());
            browser.setWindowList(browser.windowList);
            browser.setCache(browser.cache);
            ThucydidesUtils.putToSession("#BROWSER#", browser);
            browser.isOpened(true);
            verifications.init(browser);
            ThucydidesStepListener listener = new ThucydidesStepListener(browser);
            ThucydidesUtils.registerListener(listener);
        }
    }

    public ThucydidesBrowser getCurrentBrowser() {
        return browser;
    }

    public <T extends Page> T open(Class<T> pageClass) {
        openBrowser();
        return browser.open(pageClass);
    }

    public <T extends Page> T open(Class<T> rootClass, String pageName) {
        openBrowser();
        return browser.open(rootClass, pageName);
    }

    public <T extends Page> T open(Class<T> pageClass, Url url) {
        openBrowser();
        return browser.open(pageClass, url);
    }

    public <T extends Page> T onOpened(Class<T> pageClass) {
        openBrowser();
        return browser.onOpened(pageClass);
    }

    public <T extends Page> T onOpened(Class<T> rootClass, String pageName) {
        openBrowser();
        return browser.onOpened(rootClass, pageName);
    }

    public <T extends UIBlock> T onDisplayed(Class<T> block) {
        openBrowser();
        return browser.onDisplayed(block);
    }

    public void openUrl(String url) {
        openBrowser();
        browser.openUrl(url);
    }

    public String getCurrentUrl() {
        return browser.getCurrentUrl();
    }

    public String getCurrentTitle() {
        return browser.getCurrentTitle();
    }

    public void switchToNextWindow() {
        browser.switchToNextWindow();
    }

    public void switchToPreviousWindow() {
        browser.switchToPreviousWindow();
    }

    public void switchToDefaultWindow() {
        browser.switchToWindowByIndex(0);
    }

    public void switchToWindowByIndex(int index) {
        browser.switchToWindowByIndex(index);
    }

    public void refreshCurrentPage() {
        browser.refreshCurrentPage();
    }

    public void waitUntil(ExpectedCondition<Boolean> condition) {
        browser.waitUntil(condition);
    }

    public That verify() {
        return verifications.verify();
    }

    public BaseConnect workWithDB() {
        return databases.workWithDB();
    }

    public Object startScript(String script) {
        return browser.startScript(script);
    }

    public ArrayList<String> baseConnect(String select, String column) {
        return browser.baseConnect(select, column);
    }

    public String getUserPassword(String user) {
        return browser.getUserPassword(user);
    }

    public void addCookie(String cookie, String value) {
        browser.addCookie(cookie, value);
    }

    public Class<? extends Page> getPageClassByName(String pageClassName) {
        Class<?> klass = classEnumerator.getClassBySimpleName(pageClassName);
        if (!browser.isPage(klass)) {
            throw new AssertionError("Object by name " + pageClassName + " is not a page!");
        }
        return (Class<? extends Page>) klass;
    }

}
