package goheavy.driver;

import io.cucumber.java.en.*;
import general.*;
import org.junit.Assert;

@SuppressWarnings("unused")
public class DriverStepDefinition {
	private GeneralSteps generalSteps;
	private DriverStep driverSteps;

	public DriverStepDefinition() {
		generalSteps = new GeneralSteps();
		driverSteps = new DriverStep();
	}

	@When("User clicks on {string} button.")
	public void userClicksOnButton(String arg0) {
		try {
			driverSteps.userClicksOnAddDriverButton();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	/*@And("The user inserts valid driver's data")
	public void theUserInsertsValidDriverSData() {
	}*/

	@When("The user inserts valid driver's data")
	public void the_user_inserts_valid_data_and_clicks_done_button() {
		try {
			driverSteps.userInsertsValidDataAndClicksDone();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@And("User clicks on the {string} button.")
	public void userClicksOnTheButton(String arg0) {
		try {
			driverSteps.userClicksOnTheAddButton();
		}catch (Exception e){
			Assert.fail(e.getMessage());
		}
	}

	@Then("System add a new driver in \"On-boarding\" status.")
	public void systemAddANewDriver() {
		try {
			driverSteps.checkNewDriver();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Then("System displays message {string}")
	public void system_displays_message(String message) {
		driverSteps.systemDisplaysMessage(message);
	}

	@And("System returns to the {string} view")
	public void systemReturnsToTheView(String arg0) {
		try {
			driverSteps.returnToDriversListView();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
