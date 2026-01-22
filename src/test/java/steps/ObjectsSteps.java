package steps;

import clients.ObjectsClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import support.JSonAssertions;
import support.ScenarioState;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ObjectsSteps {

    private final ObjectsClient client;
    private final ScenarioState state;
    private String name;
    private String updatedName;
    private final Map<String, Object> createdData = new HashMap<>();
    private final Map<String, Object> updatedData = new HashMap<>();

    @Autowired
    public ObjectsSteps(ObjectsClient client, ScenarioState state){
        this.client=client;
        this.state=state;
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
        state.setLastResponse(response);
        String id = response.jsonPath().getString("id");
        if (id != null && !id.isBlank()) {
            state.setLastCreatedId(id);
        }
    }

    @When("the request to update the item is made")
    public void theRequestToUpdateTheItemIsMade() {
        Response response = client.updateObject(state.getLastCreatedId(), updatedName, updatedData);
        state.setLastResponse(response);
    }

    @Then("a {int} response code is returned")
    public void aResponseCodeIsReturned(int status) {
        JSonAssertions.assertStatus(state.getLastResponse(), status);
    }

    @And("a {string} is created")
    public void AnItemIsCreated(String expectedName) {
        JSonAssertions.assertJsonNotBlank(state.getLastResponse(), "id");
        JSonAssertions.assertJsonEquals(state.getLastResponse(), "name", expectedName);
        JSonAssertions.assertJsonNotBlank(state.getLastResponse(), "createdAt");
        JSonAssertions.assertJsonEquals(state.getLastResponse(), "data.price", createdData.get("price"));
        JSonAssertions.assertJsonEquals(state.getLastResponse(), "data.'CPU model'", createdData.get("CPU model"));
    }

    @When("the created item is retrieved")
    public void theCreatedItemIsRetrieved() {
        String id = state.getLastCreatedId();
        assertNotNull(id,
                "No created id stored in scenario state");
        state.setLastResponse(client.getObject(id));
    }

    @And("the retrieved item name is {string}")
    public void theRetrievedItemNameIs(String expectedName) {
        JSonAssertions.assertJsonEquals(state.getLastResponse(), "name", expectedName);
    }

    @When("the created item is deleted")
    public void theCreatedItemIsDeleted() {
        String id = state.getLastCreatedId();
        assertNotNull(id,
                "No created id stored in scenario state");
        state.setLastResponse(client.deleteObject(id));
    }

    @And("the item is updated with name {string}")
    public void theItemIsUpdatedWithName(String expectedName) {
        Response response = state.getLastResponse();
        JSonAssertions.assertJsonEquals(response, "name", expectedName);
        JSonAssertions.assertJsonEquals(response, "data.'CPU model'", updatedData.get("CPU model"));
        JSonAssertions.assertJsonEquals(response, "data.price", updatedData.get("price"));
    }
}
