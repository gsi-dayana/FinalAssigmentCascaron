package goheavy.driver.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import general.PageObject;
import general.Setup;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@SuppressWarnings("unused")
public class DriverPage extends PageObject {
	private String menuDriversLink;
	private String addDriverButton;
	private String addDriverTitle;
	private String driverListTitle;
	private String driverImageUploadButtonXpath;
	private String driverLicenceFrontImageUploadButtonXpath;
	private String driverLicenceBackImageUploadButtonXpath;
	private By searchFieldLocator = By.xpath("//input[@placeholder='Search...']");
	private String driverForm;
	private String fullName;

	public DriverPage() {
		super();
		this.urlpath = "/driver";
		setMenuDriversLink("//span[text()='Drivers']/ancestor::span[@class='ant-menu-title-content']");
		setAddDriverButton("//span[text()='Add Driver']/ancestor::button[@class='ant-btn ant-btn-primary']");
		setAddDriverTitle("//span[text()='Add Driver']/ancestor::div[@class='ant-row ant-row-space-between ant-row-middle']");
		setDriverListTitle("//span[text() ='Drivers List']");
		setDriverImageUploadButtonXpath("//label[@class='ant-form-item-required' and @title='Driver Photo " +
				"(including shoulders)']/ancestor::div[@class='ant-row ant-form-item']/descendant::input[@type='file']");
		setDriverLicenceFrontImageUploadButtonXpath("//label[contains(@title, 'shoulders')]/" +
				"ancestor::div[contains(@class,'ant-row ant-form-item')]/descendant::input[@type='file']");
		setDriverLicenceBackImageUploadButtonXpath("//label[contains(@title, 'Back')]/" +
				"ancestor::div[contains(@class,'ant-row ant-form-item')]/descendant::input[@type='file']");
		setDriverForm("//form[@id='driver-form']");
	}

	public String getDriverListTitle() {
		return driverListTitle;
	}

	public void setDriverListTitle(String driverListTitle) {
		this.driverListTitle = driverListTitle;
	}

	public By getSearchFieldLocator() {
		return searchFieldLocator;
	}

	public void setSearchFieldLocator(By searchFieldLocator) {
		this.searchFieldLocator = searchFieldLocator;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDriverLicenceBackImageUploadButtonXpath() {
		return driverLicenceBackImageUploadButtonXpath;
	}

	public void setDriverLicenceBackImageUploadButtonXpath(String driverLicenceBackImageUploadButtonXpath) {
		this.driverLicenceBackImageUploadButtonXpath = driverLicenceBackImageUploadButtonXpath;
	}

	public String getDriverLicenceFrontImageUploadButtonXpath() {
		return driverLicenceFrontImageUploadButtonXpath;
	}

	public void setDriverLicenceFrontImageUploadButtonXpath(String driverLicenceFrontImageUploadButtonXpath) {
		this.driverLicenceFrontImageUploadButtonXpath = driverLicenceFrontImageUploadButtonXpath;
	}

	public String getDriverForm() {
		return driverForm;
	}

	public void setDriverForm(String driverForm) {
		this.driverForm = driverForm;
	}

	public String getDriverImageUploadButtonXpath() {
		return driverImageUploadButtonXpath;
	}

	public void setDriverImageUploadButtonXpath(String driverImageUploadButtonXpath) {
		this.driverImageUploadButtonXpath = driverImageUploadButtonXpath;
	}

	private String getAddDriverTitle() {
		return addDriverTitle;
	}

	private void setAddDriverTitle(String addDriverTitle) {
		this.addDriverTitle = addDriverTitle;
	}

	private String getAddDriverButton() {
		return addDriverButton;
	}

	private void setAddDriverButton(String addDriverButton) {
		this.addDriverButton = addDriverButton;
	}

	private String getMenuDriversLink() {
		return menuDriversLink;
	}

	private void setMenuDriversLink(String menuDriversLink) {
		this.menuDriversLink = menuDriversLink;
	}

	public boolean goToView() {
		try {
			waitForSpinningElementDisappear();
			waitAdditionalTime();
			clickOnElement(getWebElement(By.xpath(getMenuDriversLink())), true);
			return true;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public void waitAdditionalTime() {
		Setup.getWait().thread(5000);
	}
	
	public void waitAdditionalShortTime() {
		Setup.getWait().thread(1000);
	}
	
	public void clickOnElement(WebElement element, boolean waitForSpinner) {
		if (waitForSpinner)
			waitForSpinningElementDisappear();
		Setup.getActions().moveToElement(element).build().perform();
		Setup.getActions().click(element).build().perform();
		if (waitForSpinner)
			waitForSpinningElementDisappear();
	}

    public boolean clickOnAddVehicleButton() {
		try {
			waitForSpinningElementDisappear();
			Setup.getWait().thread(3000);
			WebElement element = getWebElement(By.xpath(getAddDriverButton()));
			clickOn(element);
			return true;
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			return false;
		}
    }

	public void systemDisplaysMessage(String message) {

		waitForSpinningElementDisappear();
		String xpath = "//div[@class='ant-notification ant-notification-topRight']";

		WebElement alert = getWebElement(By.xpath(xpath));

		//Espera de tipo Fluent Wait, para chequear la pagina cada 2seg por un intervalo de 10seg en busca del mensaje de fallo del upload file
		Wait<WebDriver> mywait = new FluentWait<WebDriver>(Setup.getDriver())
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebDriver driver = Setup.getDriver();
		WebElement msg = mywait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				Assert.assertNotNull(alert.getText());
				return alert;
			}
		});
		Assert.assertEquals(alert.getText(), message);
	}

	/*
	 * Creditos a Ibelise
	 * */
	//Metodo utilizado para la captura y tratamiento del pop-up que se muestra cuando el fichero a subir con cumple las condiciones de tipo de extension y formato
	public String systemDisplaysMessage_FailedUploadFiles(File file) {

		waitForSpinningElementDisappear();
		String xpath = "//div[@class='ant-notification ant-notification-topRight']";

		WebElement alert = getWebElement(By.xpath(xpath));

		//Espera de tipo Fluent Wait, para chequear la pagina cada 2seg por un intervalo de 10seg en busca del mensaje de fallo del upload file
		Wait<WebDriver> mywait = new FluentWait<WebDriver>(Setup.getDriver())
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebDriver driver = Setup.getDriver();
		WebElement msg = mywait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				//Assert.assertEquals(alert.getText(), message);
				Assert.assertNotNull(alert.getText());
				return alert;
			}
		});
		//Assert.assertEquals(msg.getText(), message);
		return msg.getText();
	}
/*
	public boolean hoverOverImageComponent() {
		waitForSpinningElementDisappear();
		try {
			setImage(getWebElement(By.xpath(getDriverImageUploadItemXpath())), null);
			Setup.getWait().thread(500);
			Setup.getActions().moveToElement(getWebElement(By.xpath("//div[contains(@class, 'kxeirt')]/descendant::img")))
					.build().perform();
			Setup.getWait().thread(500);

			Assert.assertTrue(hoverElement(By.xpath("//span[@role='img' and @class='anticon anticon-eye' and @cursor='pointer']")
					, null));
			clickOn(getWebElement(By.xpath("//span[@role='img' and @class='anticon anticon-eye' and @cursor='pointer']")));
			clickOn(getWebElement(By.xpath("//span[@class='anticon anticon-close ant-modal-close-icon' and @role='img']")));
			Assert.assertTrue(hoverElement(By.xpath("//span[@role='img' and @class='anticon anticon-check' and @cursor='pointer']")
					, null));
			clickOn(getWebElement(By.xpath("//span[@role='img' and @class='anticon anticon-check' and @cursor='pointer']")));
			Assert.assertTrue(hoverElement(By.xpath("//span[@role='img' and @class='anticon anticon-close' and @cursor='pointer']")
					, null));

			return true;
		} catch (Exception e) {
			Assert.fail("Expected Image Over element not found");
			return false;
		}
	}
*/

	/*
	* Creditos a Ibelise
	* */
	//Metodo implementado para la validacion de las imagenes antes de cargarlas al DOM, aplicable a todos los input con Type=file (mismo comportamiento en todas las clases hijas)
	public void CheckUploadImageComponent(String fileInputButton, String nextBtn) {
		waitForSpinningElementDisappear();
		//Defini 3 ficheros principales para hacer la prueba, donde los dos primeros fallaran y el 3ro se podra subir satisfactoriamente,
		// aunque para agilizar la prueba se puede solo usar el 3er file
		File invalid_file = new File("src/test/resources/pl.xlsx");
		File exceeds_max = new File("src/test/resources/huge.png");
		File valid_file_PNG = new File("src/test/resources/avatar.jpg");
		List<File> paths = new ArrayList<File>();
		paths.add(invalid_file);
		paths.add(exceeds_max);
		paths.add(valid_file_PNG);
		for (File file : paths) {
			//Pasando el path absoluto del fichero al input no valida, solo captura el fichero a subir, por lo que es necesario simular
			// el click en el botton Next para realizar la validacion
			Setup.getDriver().findElement(By.xpath(fileInputButton)).sendKeys(file.getAbsolutePath());
			Setup.getDriver().findElement(By.xpath(nextBtn)).click();

			try {
				if (file.getName().contains("JPG") && file.getName().contains("JPEG") && file.getName().contains("PNG")) {
					Assert.assertEquals(systemDisplaysMessage_FailedUploadFiles(file), "You can only upload JPG/JPEG/PNG files");
					if (file.length() >= 5242880) {
						Assert.assertEquals(systemDisplaysMessage_FailedUploadFiles(file), "The image must be smaller than 5 MB");
					} else {
						Assert.assertTrue(Setup.getDriver().findElement(By.xpath("//div[@class='styles__ImagePreviewActionsStyled-sc-1qjgkf9-12 kxeirt']")).isDisplayed());
						Assert.assertEquals(Setup.getDriver().findElement(By.xpath("//span[@class='styles__ItemStatusStyled-sc-1qjgkf9-14 gDWxff item-status']")).getText(),"Assessing");
					}
				}
			} catch (Exception e) {
				Assert.fail(e.getMessage());
			}
		}
	}

	public boolean hoverElement(By by, WebElement element) {
		try {
			Setup.getWait().thread(500);
			if (element != null)
				Setup.getActions().moveToElement(element).build().perform();
			else
				Setup.getActions().moveToElement(getWebElement(by)).build().perform();
			Setup.getWait().thread(500);
			return true;
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			return false;
		}
	}

	public boolean systemOpensAddDriverView() {
		Setup.getWait().thread(5000);
		waitForSpinningElementDisappear();

		try {
			Assert.assertNotNull("Page Title not found", getPageElementBy(By.xpath(getAddDriverTitle())));
			//Assert.assertNotNull("Page Icon not Found", getPageElementBy(By.xpath(getCarIconXpath())));
			//Assert.assertTrue("Step 1 not found", checkStep(getWebElement(By.xpath(getStepBaseXpath())), "1", "Vehicle Info"));
			//Assert.assertNotNull("Driver Image Upload not found", getPageElementBy(By.xpath(getDriverImageUploadItemXpath())));
			/*Assert.assertNotNull("Driver Input not found or do not match Expected Criteria",
					getPageElementBy(By.xpath(getVINInputXpath())));
			Assert.assertNotNull("Vehicle Type Input not found or do not match Expected Criteria",
					getPageElementBy(By.xpath(getVehicleTypeXpath())));
			checkVehicleTypeComponentBehaviour();
			Assert.assertNotNull("Vehicle Make Input not found or do not match Expected Criteria",
					getPageElementBy(By.xpath(getVehicleMakeXpath())));
			Assert.assertNotNull("Vehicle Year Make Input not found or do not match Expected Criteria",
					getPageElementBy(By.xpath(getVehicleYearMakepath())));
			Assert.assertNotNull("Vehicle Sub Section not found",
					getPageElementBy(By.xpath(getVehicleCapacitySubSectionXpath())));*/
			//TODO: Work in progress check for every element on the page to be Expected
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<WebElement> getPageElementBy(By by) {
		try {
			return getWebElements(by);
		} catch (Exception e) {
			Assert.fail("Expected Title Page element not found");
			return null;
		}
	}

	public void insertValidData() {
		//Wait to finish the load
		waitForSpinningElementDisappear();

		//Load Image and check it
		setImageImproved("Driver Photo (including shoulders)", null);

		//Check Driver's First Name placeholder
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("firstName").getAttribute("placeholder"),"Enter First Name");

		//Check Driver's First Name messages
		//Use numbers
		sendDataToInput(getElementByID("firstName"), getFaker().random().nextInt(1,99999).toString(),
				null, getDriverForm());
		Setup.getWait().thread(1000);
		Assert.assertEquals("Wrong message for invalid First Name",
				getWebElement(By.xpath("//input[@id='firstName']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Only letters and the special characters (' -) are allowed. 50 characters maximum");
		waitAdditionalShortTime();
		getElementByID("firstName").clear();

		//Use long text
		sendDataToInput(getElementByID("firstName"), getFaker().shakespeare().hamletQuote(), null, getDriverForm());
		Setup.getWait().thread(1000);
		Assert.assertEquals("Wrong message for invalid First Name",
				getWebElement(By.xpath("//input[@id='firstName']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Only letters and the special characters (' -) are allowed. 50 characters maximum");
		waitAdditionalShortTime();
		getElementByID("firstName").clear();

		//Set Driver's First Name
		String firstName = getFaker().name().firstName();
		Setup.setKeyValueStore("firstName", firstName);
		sendDataToInput(getElementByID("firstName"), Setup.getValueStore("firstName"), null, getDriverForm());

		//Check Driver's Last Name placeholder
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("lastName").getAttribute("placeholder"),"Enter Last Name");

		//Check Driver's Last Name messages
		//Use numbers
		sendDataToInput(getElementByID("lastName"), getFaker().random().nextInt(1,99999).toString(),
				null, getDriverForm());
		Setup.getWait().thread(1000);
		Assert.assertEquals("Wrong message for invalid Last Name",
				getWebElement(By.xpath("//input[@id='lastName']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Only letters and the special characters (' -) are allowed. 50 characters maximum");
		waitAdditionalShortTime();
		getElementByID("lastName").clear();

		//Use long text
		sendDataToInput(getElementByID("lastName"), getFaker().shakespeare().kingRichardIIIQuote(), null, getDriverForm());
		Setup.getWait().thread(1000);
		Assert.assertEquals("Wrong message for invalid Last Name",
				getWebElement(By.xpath("//input[@id='lastName']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Only letters and the special characters (' -) are allowed. 50 characters maximum");
		waitAdditionalShortTime();
		getElementByID("lastName").clear();

		//Set Driver's Last Name
		String lastName = getFaker().name().lastName();
		Setup.setKeyValueStore("lastName", lastName);
		sendDataToInput(getElementByID("lastName"), Setup.getValueStore("lastName"), null, getDriverForm());

		//Check Driver's Birthday placeholder
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("birthAt").getAttribute("placeholder"),"Select Date");

		//Set Driver's Birthday
		String birthDay = new SimpleDateFormat("MM/dd/yyyy").format(getFaker().date().birthday(21, 80));
		Setup.setKeyValueStore("birthAt", birthDay);
		sendDataToInput(getElementByID("birthAt"), birthDay, null, getDriverForm());
		sendDataToInput(getElementByID("birthAt"), null, Keys.RETURN, getDriverForm());

		//Check Driver's Experience placeholder
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("experienceYear").getAttribute("placeholder"),"Enter Years of experience");

		//Check Driver's Experience messages
		//Use wrong numbers
		sendDataToInput(getElementByID("experienceYear"), getFaker().random().nextInt(64,80).toString(),
				null, getDriverForm());
		Setup.getWait().thread(1000);
		Assert.assertTrue(getWebElement(By.xpath("//input[@id='experienceYear']/ancestor::div[contains(@class," +
				"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText().contains(
				"Please enter a value between 3 and"));
		waitAdditionalShortTime();
		getElementByID("experienceYear").clear();

		//Use text
		sendDataToInput(getElementByID("experienceYear"), getFaker().witcher().quote(), null, getDriverForm());
		Setup.getWait().thread(1000);
		Assert.assertEquals("Wrong message for invalid experience",
				getWebElement(By.xpath("//input[@id='experienceYear']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Please, enter a valid number");
		waitAdditionalShortTime();
		getElementByID("experienceYear").clear();

		//Set Driver's Experience
		String experienceYear = "3";
		Setup.setKeyValueStore("experienceYear", experienceYear);
		sendDataToInput(getElementByID("experienceYear"), Setup.getValueStore("experienceYear"), null, getDriverForm());

		//Check Driver's Mobile placeholder
		scrollToWebElement(getElementByID("mobilePhone"), getDriverForm());
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("mobilePhone").getAttribute("placeholder"),"Enter Mobile Number");

		//Check Driver's Mobile messages
		//Use less numbers
		scrollToWebElement(getElementByID("mobilePhone"), getDriverForm());
		sendDataToInput(getElementByID("mobilePhone"), getFaker().random().nextInt(1,999999).toString(),
				null, getDriverForm());
		Setup.getWait().thread(1000);
		scrollToWebElement(getWebElement(By.xpath("//input[@id='mobilePhone']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")),
				"//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Wrong message for invalid mobile",
				getWebElement(By.xpath("//input[@id='mobilePhone']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Please, enter a valid phone number");
		waitAdditionalShortTime();
		getElementByID("mobilePhone").clear();

		//Set Driver's Mobile
		//scrollToWebElement(getElementByID("mobilePhone"), "//div[contains(@class, 'ContentDiv')]");
		String mobilePhone = getFaker().phoneNumber().cellPhone();
		Setup.setKeyValueStore("mobilePhone", mobilePhone);
		sendDataToInput(getElementByID("mobilePhone"), Setup.getValueStore("mobilePhone"), null, getDriverForm());

		//Check Driver's Email placeholder
		scrollToWebElement(getElementByID("email"), "//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("email").getAttribute("placeholder"),"Enter Email Address");

		//Check Driver's Email messages
		//Use less numbers
		scrollToWebElement(getElementByID("email"), "//div[contains(@class, 'ContentDiv')]");
		sendDataToInput(getElementByID("email"), getFaker().random().nextInt(1,99999).toString(),
				null, getDriverForm());
		Setup.getWait().thread(1000);
		scrollToWebElement(getWebElement(By.xpath("//input[@id='email']/ancestor::div[contains(@class," +
				"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")),
				"//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Wrong message for invalid email",
				getWebElement(By.xpath("//input[@id='email']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Please, enter a valid email address");
		waitAdditionalShortTime();
		getElementByID("email").clear();

		//Set Driver's Email
		scrollToWebElement(getElementByID("email"), "//div[contains(@class, 'ContentDiv')]");
		String email = getFaker().internet().safeEmailAddress();
		Setup.setKeyValueStore("email", email);
		sendDataToInput(getElementByID("email"), Setup.getValueStore("email"), null, getDriverForm());

		//TODO
		//Check Driver's T-shirt placeholder
		//It does not have a placeholder attribute

		//Set Driver's T-shirt
		scrollToWebElement(getWebElement(By.id("tShirtSize")), "//div[contains(@class, 'ContentDiv')]");
		interactAndRandomSelectFromDropDown("tShirtSize", "tShirtSize_list");

		//Check Driver's Address placeholder
		//Changed the condition from "Enter the address" as is in the US to "Address" as is currently used in the app
		scrollToWebElement(getWebElement(By.id("address")), "//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("address").getAttribute("placeholder"),"Address");

		//Check Driver's Address messages
		//Use invalid characters
		scrollToWebElement(getWebElement(By.id("address")), "//div[contains(@class, 'ContentDiv')]");
		sendDataToInput(getElementByID("address"), getFaker().internet().safeEmailAddress(), null, getDriverForm());
		Setup.getWait().thread(1000);
		scrollToWebElement(getWebElement(By.xpath("//input[@id='address']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")),
				"//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Wrong message for invalid Address",
				getWebElement(By.xpath("//input[@id='address']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Only letters, numbers, and the special characters (.-,'#) are allowed. 50 characters maximum");
		waitAdditionalShortTime();
		getElementByID("address").clear();

		//Use long text
		scrollToWebElement(getWebElement(By.id("address")), "//div[contains(@class, 'ContentDiv')]");
		sendDataToInput(getElementByID("address"), getFaker().shakespeare().romeoAndJulietQuote(), null, getDriverForm());
		Setup.getWait().thread(1000);
		scrollToWebElement(getWebElement(By.xpath("//input[@id='address']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")),
				"//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Wrong message for invalid Address",
				getWebElement(By.xpath("//input[@id='address']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Only letters, numbers, and the special characters (.-,'#) are allowed. 50 characters maximum");
		waitAdditionalShortTime();
		getElementByID("address").clear();

		//Set Driver's Address
		scrollToWebElement(getWebElement(By.id("address")), "//div[contains(@class, 'ContentDiv')]");
		String address = getFaker().address().fullAddress();
		Setup.setKeyValueStore("address", address);
		sendDataToInput(getElementByID("address"), Setup.getValueStore("address"), null, getDriverForm());

		//Check Driver's City placeholder
		//Changed the condition from "Enter the city name" as is in the US to "Enter City" as is currently used in the app
		scrollToWebElement(getWebElement(By.id("addressCity")), "//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("addressCity").getAttribute("placeholder"),"Enter City");

		//TODO
		//Commented implementation, the error message are not shown
		/*//Check Driver's City messages
		//Use invalid characters
		sendDataToInput(getElementByID("addressCity"), getFaker().internet().safeEmailAddress(), null, getDriverForm());
		Setup.getWait().thread(1000);
		Assert.assertEquals("Wrong message for invalid Address",
				getWebElement(By.xpath("//input[@id='addressCity']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Only letters, numbers, and the special characters (.-,'#) are allowed. 50 characters maximum");
		//Use long text
		sendDataToInput(getElementByID("addressCity"), getFaker().shakespeare().romeoAndJulietQuote(), null, getDriverForm());
		Setup.getWait().thread(1000);
		Assert.assertEquals("Wrong message for invalid City",
				getWebElement(By.xpath("//input[@id='addressCity']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Only letters, numbers, and the special characters (.-,'#) are allowed. 50 characters maximum");*/

		//Set Driver's City
		scrollToWebElement(getWebElement(By.id("addressCity")), "//div[contains(@class, 'ContentDiv')]");
		String addressCity = getFaker().address().fullAddress();
		Setup.setKeyValueStore("addressCity", addressCity);
		sendDataToInput(getElementByID("addressCity"), Setup.getValueStore("addressCity"), null, getDriverForm());

		//Check Driver's ZIP Code placeholder
		scrollToWebElement(getWebElement(By.id("addressZipCode")), "//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("addressZipCode").getAttribute("placeholder"),"Enter ZIP Code");

		//Check Driver's ZIP Code messages
		//Not use enough digits
		scrollToWebElement(getWebElement(By.id("addressZipCode")), "//div[contains(@class, 'ContentDiv')]");
		sendDataToInput(getElementByID("addressZipCode"), getFaker().random().nextInt(1,999999).toString(),
				null, getDriverForm());
		Setup.getWait().thread(1000);
		scrollToWebElement(getWebElement(By.xpath("//input[@id='addressZipCode']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")),
				"//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Wrong message for invalid ZIP Code",
				getWebElement(By.xpath("//input[@id='addressZipCode']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Please, enter a valid ZIP code");
		waitAdditionalShortTime();
		getElementByID("addressZipCode").clear();

		//Set Driver's ZIP Code
		scrollToWebElement(getWebElement(By.id("addressZipCode")), "//div[contains(@class, 'ContentDiv')]");
		String addressZipCode = getFaker().address().zipCode();
		Setup.setKeyValueStore("addressZipCode", addressZipCode);
		sendDataToInput(getElementByID("addressZipCode"), Setup.getValueStore("addressZipCode"), null, getDriverForm());

		//Check Driver's State placeholder
		//It does not have a placeholder attribute
		/*scrollToWebElement(getWebElement(By.id("addressStateId")), "//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("addressStateId").getAttribute("placeholder"),"Select the state");*/

		//Set Driver's State
		scrollToWebElement(getElementByID("addressStateId"), "//div[contains(@class, 'ContentDiv')]");
		interactAndRandomSelectFromDropDown("addressStateId", "addressStateId_list");

		//Set Driver's Licence Photo (Front)
		scrollToWebElement(getWebElement(By.xpath(driverLicenceFrontImageUploadButtonXpath)),
				"//div[contains(@class, 'ContentDiv')]");
		setImageImproved("Driver's License Photo (Front)", null);

		//Set Driver's Licence Photo (Front)
		scrollToWebElement(getWebElement(By.xpath(driverLicenceBackImageUploadButtonXpath)),
				"//div[contains(@class, 'ContentDiv')]");
		setImageImproved("Driver's License Photo (Back)", null);

		//Check Driver's License (DL) Number placeholder
		scrollToWebElement(getWebElement(By.id("dlNumber")), "//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("dlNumber").getAttribute("placeholder"),"Enter DL number");

		//Check Driver's License (DL) Number message
		scrollToWebElement(getWebElement(By.id("dlNumber")), "//div[contains(@class, 'ContentDiv')]");
		sendDataToInput(getElementByID("dlNumber"), getFaker().random().nextInt(1,6).toString(), null, getDriverForm());
		Setup.getWait().thread(1000);
		scrollToWebElement(getWebElement(By.xpath("//input[@id='dlNumber']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")),
				"//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Wrong message for invalid Address",
				getWebElement(By.xpath("//input[@id='dlNumber']/ancestor::div[contains(@class," +
						"'ant-col ant-form-item-control')]/descendant::div[@role='alert']")).getText(),
				"Please, enter a valid DL number");
		waitAdditionalShortTime();
		getElementByID("dlNumber").clear();

		//Set Driver's License (DL) Number
		scrollToWebElement(getWebElement(By.id("dlNumber")), "//div[contains(@class, 'ContentDiv')]");
		String dlNumber = getFaker().random().nextInt(1,9999999).toString();
		Setup.setKeyValueStore("dlNumber", dlNumber);
		sendDataToInput(getElementByID("dlNumber"), Setup.getValueStore("dlNumber"), null, getDriverForm());

		//Check DL Issued Date placeholder
		scrollToWebElement(getWebElement(By.id("dlIssuedDate")), "//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("dlIssuedDate").getAttribute("placeholder"),"Select Date");

		//Set DL Issued Date
		scrollToWebElement(getWebElement(By.id("dlIssuedDate")), "//div[contains(@class, 'ContentDiv')]");
		String dlIssuedDate = new SimpleDateFormat("MM/dd/yyyy").format(getFaker().date().birthday(21, 80));
		Setup.setKeyValueStore("dlIssuedDate", dlIssuedDate);
		sendDataToInput(getElementByID("dlIssuedDate"), dlIssuedDate, null, getDriverForm());
		sendDataToInput(getElementByID("dlIssuedDate"), null, Keys.RETURN, getDriverForm());

		//Check DL Expiration Date placeholder
		scrollToWebElement(getWebElement(By.id("dlExpirationDate")), "//div[contains(@class, 'ContentDiv')]");
		Assert.assertEquals("Place holder does not match with the expected",
				getElementByID("dlExpirationDate").getAttribute("placeholder"),"Select Date");

		//Set DL Expiration Date
		scrollToWebElement(getWebElement(By.id("dlExpirationDate")), "//div[contains(@class, 'ContentDiv')]");
		String dlExpirationDate = new SimpleDateFormat("MM/dd/yyyy").format(getFaker().date().birthday(21, 80));
		Setup.setKeyValueStore("dlExpirationDate", dlExpirationDate);
		sendDataToInput(getElementByID("dlExpirationDate"), dlIssuedDate, null, getDriverForm());
		sendDataToInput(getElementByID("dlExpirationDate"), null, Keys.RETURN, getDriverForm());

		//Set Driver's State
		scrollToWebElement(getElementByID("dlClassType"), "//div[contains(@class, 'ContentDiv')]");
		interactAndRandomSelectFromDropDown("dlClassType", "dlClassType_list");

	}

	public boolean userClicksOnTheAddButton()  {
		try {
			clickOn(getWebElement(By.xpath("//span[text()='Add']/ancestor::button")));
			return true;
		}catch (Exception e){
			Assert.fail(e.getMessage());
			return false;
		}

	}

	/*
	* Creditos a Dayana
	* */
	public boolean searchNewDriver() {
		try {
			waitForSpinningElementDisappear();
			setFullName(Setup.getValueStore("driverName") + " " + Setup.getValueStore("driverLastName"));
			Setup.getActions().sendKeys(getWebElement(searchFieldLocator), getFullName()).build().perform();
			Setup.getActions().sendKeys(getWebElement(searchFieldLocator), Keys.RETURN).build().perform();
			Assert.assertNotNull(getWebElement(By.xpath("//td[text()='" + getFullName() +  "']")));
			//Get x position of the new Driver
			int xPosition = getWebElement(By.xpath("//td[text()='" + getFullName() +  "']")).getLocation().getX();
			List<WebElement> list =new ArrayList<>();
			/*Get list of drivers on "On-boarding"
			It should be a better way to do this...
			Probably using getWebElement(By.cssSelector()) but I'm not sure how to get it the right WebElement
			So I get all the elements on "On-boarding" and compare if the x position are the same*/
			list = getWebElements(By.xpath("//span[text()='On-boarding']"));
			int i = 0;
			boolean existStatus = false;
			while (list.iterator().hasNext() || !existStatus){
				if (list.iterator().next().getLocation().getX() == xPosition){
					existStatus = true;
				}
			}
			Assert.assertTrue("There is a problem. The new driver Status is not on On-boarding or does not exist"
					,existStatus);
			return true;
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			return false;
		}
	}

	public boolean isDriverListView() {
		return getWebElement(By.xpath(driverListTitle)).isDisplayed();
	}
}
