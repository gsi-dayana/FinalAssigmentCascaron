package general;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

public class PageObject {
    private WebDriver driver;
    protected String urlpath = "";
    private WebDriverWait wait;
    private int waitTime;
    private String spinningElement = "//div[contains(@class,'ant-spin-spinning')]";
    public Faker faker;

    public PageObject() {
        this.setDriver(Setup.getDriver());
        this.setFaker(new Faker());
        PageFactory.initElements(this.getDriver(), this);
        setWaitTime(20);
        setWait(new WebDriverWait(this.getDriver(), this.getWaitTime()));
    }

    public void setImage(WebElement element, Object object) {
        WebElement image_element = element;
        String avatar = (String) Setup.getValueStore("avatar");
        image_element.sendKeys(avatar);
    }

    public void setImageImproved(String title, Object object) {
        String xpath = "//label[@title=\"" + title + "\"]/ancestor::div[@class='ant-row ant-form-item']"
                + "/descendant::input[@type='file']";
        WebElement image_element = getWebElement(By.xpath(xpath));
        String avatar = (String) Setup.getValueStore("avatar");
        image_element.sendKeys(avatar);
    }

    public void sendDataToInputImproved(String labelText, String data, Keys key, InputType type, boolean scroll, String form,
                                        int y_pos) {
        String xpath = "//label[text()=\"" + labelText + "\"]/ancestor::div[@class='ant-row ant-form-item']/"
                + "descendant::" + type + "[@placeholder]";
        WebElement element = getWebElement(By.xpath(xpath));
        Setup.getActions().moveToElement(element).build().perform();
        if (scroll)
            formScrollImproved(form, y_pos);
        if (key != null)
            Setup.getActions().sendKeys(element, key).build().perform();
        else
            Setup.getActions().sendKeys(element, data).build().perform();
    }

    public void print(Object string) {
        System.out.println(string);
    }

    public void submitForm(String formId) {
        try {
            String formXpath = "//*[@id='" + formId + "']/ancestor::div"
                    + "[@class='templateStyles__ContentDiv-sc-144t9h2-1 bcVeZj']";
            getWebElement(By.xpath(formXpath)).submit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void formScrollImproved(String form, int yScrollBy) {
        String formXpath = "//*[@id='" + form + "']/ancestor::div[@class='templateStyles__ContentDiv-sc-144t9h2-1 bcVeZj']";
        String script = "arguments[0].scrollBy(0, " + yScrollBy + ");";
        Setup.getJsExecutor().executeScript(script, getWebElement(By.xpath(formXpath)));
    }

    public void killEvent() {
        System.exit(0);
    }

    public void acceptImage(String title) {
        String xpath = "//label[@title=\"" + title + "\"]/ancestor::div[contains(@class, 'ant-col')]/"
                + "descendant::span[@class='anticon anticon-check']";

        Setup.getActions().moveToElement(getWebElement(By.xpath(xpath))).build().perform();
        Setup.getWait().thread(250);
        Setup.getActions().click(getWebElement(By.xpath(xpath))).build().perform();
        Setup.getWait().thread(200);
    }

    public Faker getFaker() {
        return faker;
    }

    void setFaker(Faker faker) {
        this.faker = faker;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void openURL() {
        Setup.openUrl(System.getProperty("defaultURL").concat("/").concat(urlpath));
    }

    public WebElement getWebElement(By by) {
        return this.getDriver().findElement(by);
    }

    protected List<WebElement> getWebElements(By by) {
        return this.getDriver().findElements(by);
    }

    protected void cliksOnButton(By by) {
        getWebElement(by).click();
        waitForSpinningElementDisappear();
        Setup.getWait().waitForLoading((Integer) Setup.getTimeouts().get("implicit"));
    }

    public String getCurrentUrl() {
        return this.getDriver().getCurrentUrl();
    }

    public String getPagePath() {
        return this.urlpath;
    }

    public void checkSpinningAppears() {
        //Setup.getWait().waitForLoading((Integer) Setup.getTimeouts().get("implicit"));
        Setup.getWait().waitUntilElementAppear(By.xpath(spinningElement), (Integer) Setup.getTimeouts().get("pageLoad"));
        Setup.getWait().waitForLoading((Integer) Setup.getTimeouts().get("implicit"));
    }

    public void waitForSpinningElementDisappear() {
        try {
            //Setup.getWait().waitForLoading((Long) (Setup.getTimeouts().get("implicit")));
            checkSpinningAppears();
            Setup.getWait().waitUntilElementDisappear(By.xpath(spinningElement), 10);
            Setup.getWait().waitForLoading((Long) (Setup.getTimeouts().get("implicit")));
            Setup.getWait().thread(500);
        } catch (Exception e) {
        }
    }

    public void scroll(String scrollElementxpath, By targetElementxpath) {

        WebElement el = this.getWebElement(targetElementxpath);
        int desired_y = el.getSize().height / 2 + el.getLocation().y;

        int current_y = (Integer.parseInt(String.valueOf(Setup.executeScript("return window.innerHeight"))) / 2)
                + Integer.parseInt(String.valueOf(Setup.executeScript("return window.pageYOffset")));
        int scroll_y_by = (desired_y + 150) + (current_y + 150);

        Setup.executeScript("var el=" + "document.evaluate('" + scrollElementxpath + "',"
                + " document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
                + " el.scrollTo(0, arguments[0]);", scroll_y_by);

        Setup.getWait().thread(500);
    }

    public WebElement getElementByID(String id) {
        return getWebElement(By.id(id));
    }

    public void clickOn(WebElement element) {
        waitForSpinningElementDisappear();
        Setup.getWait().thread(150);
        Setup.getActions().moveToElement(element).build().perform();
        Setup.getActions().click(element).build().perform();
        waitForSpinningElementDisappear();
        Setup.getWait().thread(150);
    }

    public void sendDataToInput(WebElement element, Object data, Keys key, String form) {
        try {
            if (element.getAttribute("value").length() > 0)
                clear_element_text(element);
        } catch (Exception e) {
        }
        scrollToWebElement(element, form);
        if (data != null)
            Setup.getActions().sendKeys(element, (CharSequence) data).build().perform();
        else
            Setup.getActions().sendKeys(element, key).build().perform();
    }

    public  void scrollToWebElement(WebElement element, String form) {
        Setup.getWait().thread(500);
        int y_pos = Integer.valueOf(Setup.timeouts.get("script").toString());
        if (element != null)
            y_pos = element.getLocation().y;

        String script = "arguments[0].scrollTo(0, " + y_pos + ");";
        Setup.getJsExecutor().executeScript(script, getWebElement(By.xpath(form)));
        Setup.getWait().thread(500);
    }

    public void clear_element_text(WebElement element) {
        int length = element.getAttribute("value").length();
        for (int i = 0; i <= length; i++) {
            Setup.getActions().sendKeys(element, Keys.BACK_SPACE).perform();
        }
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public HashMap<String, WebElement> getMenu(By by) {
        waitForSpinningElementDisappear();
        Setup.getWait().thread(4000);
        HashMap<String, WebElement> list = new HashMap<String, WebElement>();

        if (!Objects.isNull(by)) {
            WebElement e = this.getWebElement(by);

            WebElement el2 = e.findElement(By.xpath("span[2]"));
            list.put(el2.getText(), e);
        } else {
            // return an array of all Menu value
        }
        return list;
    }

    public void interactAndRandomSelectFromDropDown(String id_dropdown, String id_options) {
        try {
            WebElement element = getWebElement(By.xpath("//input[@id='" + id_dropdown +"' and @role='combobox']"));
            Setup.getActions().moveToElement(element).build().perform();
            Setup.getActions().click(element).build().perform();
            String xpath = "//div[@role='listbox' and @id='" + id_options + "']/ancestor::div[contains(@class, "
                    + "'ant-select-dropdown')]/descendant::div[@class='ant-select-item-option-content']";
            List<WebElement> select_elements = getWebElements(By.xpath(xpath));
            WebElement option_element = select_elements.get(
                    getFaker().number().numberBetween(3, 5));
            Setup.getActions().moveToElement(option_element).build().perform();
            Setup.getWait().thread(750);
            Setup.getActions().click(option_element).build().perform();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
