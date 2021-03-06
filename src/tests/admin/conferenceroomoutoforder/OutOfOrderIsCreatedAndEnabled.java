package tests.admin.conferenceroomoutoforder;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC01: Verify an Out Of Order can be created and enabled in Out Of Order Planning
 * TC32: Verify that when an Out Of Order is created and enabled a message that says 
 * "Out of order was created successfully." is displayed
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsCreatedAndEnabled {
	
	//Getting Out Of Order data from an excel file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	private String roomName = testData.get(4).get("Room Name");
	private String title = testData.get(4).get("Title");
		
	@Test(groups = {"ACCEPTANCE", "UI"})
	public void testOutOfOrderIsCreatedAndEnabled() throws JSONException, MalformedURLException, IOException {
		String startDate = testData.get(4).get("Start date (days to add)");
		String endDate = testData.get(4).get("End date (days to add)");
		String startTime = testData.get(4).get("Start time (minutes to add)");
		String endTime = testData.get(4).get("End time (minutes to add)");
		String description = testData.get(4).get("Description");
		
		//Out Of Oder Creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomOutOfOrderPlanningPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, 
						description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		
		//Assertion for TC01
		Assert.assertTrue(roomsPage.isOutOfOrderIconDisplayed(roomName));
		Assert.assertTrue(RootRestMethods.isOutOfOrderCreated(roomName, title));
		Assert.assertTrue(RootRestMethods.isOutOfOrderEnable(roomName, title));
		
		//Assertion for TC32
		Assert.assertTrue(roomsPage.isMessagePresent());
		Assert.assertTrue(roomsPage.isOutOfOrderSuccessfullyCreatedMessageDisplayed());
	}
	
	@AfterMethod(groups = {"ACCEPTANCE", "UI"})
	public void deleteOutOfOrder() throws MalformedURLException, IOException {
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
