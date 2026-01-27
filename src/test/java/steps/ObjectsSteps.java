package steps;

import clients.ObjectsClient;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import support.JSonAssertions;
import support.TestContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ObjectsSteps {

    private final ObjectsClient client;
    private final TestContext testContext;
    private String name;
    private String updatedName;
    private final Map<String, Object> createdData = new HashMap<>();
    private final Map<String, Object> updatedData = new HashMap<>();

    @Autowired
    public ObjectsSteps(ObjectsClient client, TestContext testContext) {
        this.client = client;
        this.testContext = testContext;
    }

    @Given("a {string} item is created")
    public void aItemIsCreate(String itemName) {
        this.name = itemName;
    }

    @And("is a {string} CPU model")
    public void isACpuModel(String cpu) {
        createdData.put("CPU model", cpu);
    }

    @And("has a price of {string}")
    public void hasAPriceOf(String price) {
        createdData.put("price", price);
    }

    @Given("the user updated item name to {string}")
    public void updatedItemTo(String itemName) {
        this.updatedName = itemName;
    }

    @And("the user updated cpu model to {string}")
    public void updatedModelTo(String cpu) {
        updatedData.put("CPU model", cpu);
    }

    @And("the user updated price to {string}")
    public void updatedPriceTo(String price) {
        updatedData.put("price", price);
    }

    @When("the request to add the item is made")
    public void theRequestToAddTheItemIsMade() {
        Response response = client.createObject(name, createdData);
        testContext.setLastResponse(response);
        String id = response.jsonPath().getString("id");
        if (id != null && !id.isBlank()) {
            testContext.setLastCreatedId(id);
        }
    }

    @When("the request to update the item is made")
    public void theRequestToUpdateTheItemIsMade() {
        Response response = client.updateObject(testContext.getLastCreatedId(), updatedName, updatedData);
        testContext.setLastResponse(response);
    }

    @Then("a {int} response code is returned")
    public void aResponseCodeIsReturned(int status) {
        JSonAssertions.assertStatus(testContext.getLastResponse(), status);
    }

    @And("a {string} is created")
    public void AnItemIsCreated(String expectedName) {
        JSonAssertions.assertJsonNotBlank(testContext.getLastResponse(), "id");
        JSonAssertions.assertJsonEquals(testContext.getLastResponse(), "name", expectedName);
        JSonAssertions.assertJsonNotBlank(testContext.getLastResponse(), "createdAt");
        JSonAssertions.assertJsonEquals(testContext.getLastResponse(), "data.price", createdData.get("price"));
        JSonAssertions.assertJsonEquals(testContext.getLastResponse(), "data.'CPU model'", createdData.get("CPU model"));
    }

    @When("the created item is retrieved")
    public void theCreatedItemIsRetrieved() {
        String id = testContext.getLastCreatedId();
        assertNotNull(id,
                "No created id stored in scenario test context");
        testContext.setLastResponse(client.getObject(id));
    }

    @And("the retrieved item name is {string}")
    public void theRetrievedItemNameIs(String expectedName) {
        JSonAssertions.assertJsonEquals(testContext.getLastResponse(), "name", expectedName);
    }

    @When("the created item is deleted")
    public void theCreatedItemIsDeleted() {
        String id = testContext.getLastCreatedId();
        assertNotNull(id,
                "No created id stored in scenario test context");
        testContext.setLastResponse(client.deleteObject(id));
    }

    @And("the item is updated with name {string}")
    public void theItemIsUpdatedWithName(String expectedName) {
        Response response = testContext.getLastResponse();
        JSonAssertions.assertJsonEquals(response, "name", expectedName);
        JSonAssertions.assertJsonEquals(response, "data.'CPU model'", updatedData.get("CPU model"));
        JSonAssertions.assertJsonEquals(response, "data.price", updatedData.get("price"));
    }

    @When("the created items are retrieved")
    public void theCreatedItemsAreRetrieved() {
        Response response = client.getObjectsForIds(testContext.getCreatedIds());
        testContext.setLastResponse(response);
    }

    @And("the retrieved items are as expected")
    public void theRetrievedItemsAreAsExpected() {
        Response response = testContext.getLastResponse();
        List<String> expectedIds = testContext.getCreatedIds();
        List<String> actualIds = response.jsonPath().getList("id");
        assertTrue(actualIds.containsAll(expectedIds));
    }

    @When("I retrieve an item with invalid id {string}")
    public void iRetrieveAnItemWithInvalidId(String id) {
        Response response = client.getObject(id);
        testContext.setLastResponse(response);
    }
}
