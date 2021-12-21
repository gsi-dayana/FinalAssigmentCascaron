package goheavy.driver;

import goheavy.driver.pages.TabsPage;
import org.junit.Assert;

import general.Steps;
import goheavy.driver.pages.DriverPage;

@SuppressWarnings("unused")
public class DriverStep extends Steps{
	private DriverPage driverPage;
	private TabsPage tp;

	public DriverStep() {
		driverPage = new DriverPage();
	}

	public void checkPage() {
		String path = driverPage.getPagePath().toLowerCase();
		Assert.assertTrue(" The path provided is not correct in the url. path: " + path,
				driverPage.getCurrentUrl().toLowerCase().contains(path));
	}

	public void goToView() {
		Assert.assertTrue(driverPage.goToView());
	}

    public void userClicksOnAddDriverButton() {
		try {
			Assert.assertTrue(driverPage.clickOnAddVehicleButton());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
    }

	public void userInsertsValidDataAndClicksDone() {
		try {
			DriverPage dp = new DriverPage();
			dp.insertValidData();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void userClicksOnTheAddButton() {
		try {
			Assert.assertTrue(driverPage.userClicksOnTheAddButton());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void checkNewDriver() {
		Assert.assertTrue(driverPage.searchNewDriver());
	}

	public void systemDisplaysMessage(String message) {
		driverPage.systemDisplaysMessage(message);
	}

	public void returnToDriversListView() {
		Assert.assertTrue(driverPage.isDriverListView());
	}
}
