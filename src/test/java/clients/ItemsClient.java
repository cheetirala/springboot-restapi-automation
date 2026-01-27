package clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ItemsClient {

    private final String baseUrl;

    public ItemsClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response createItem(String name, Map<String, Object> data) {
        Map<String, Object> body = Map.of("name", name, "data", data);

        return given().baseUri(baseUrl).contentType(ContentType.JSON).body(body).when().post("/objects").then().extract().response();
    }

    public Response updateItem(String id, String name, Map<String, Object> data) {
        Map<String, Object> body = Map.of("name", name, "data", data);

        return given().baseUri(baseUrl).contentType(ContentType.JSON).body(body).when().put("/objects/{id}", id).then().extract().response();
    }

    public Response getItem(String id) {
        return given().baseUri(baseUrl).when().get("/objects/{id}", id).then().extract().response();
    }

    public Response deleteItem(String id) {
        return given().baseUri(baseUrl).when().delete("/objects/{id}", id).then().extract().response();
    }

    public Response getItemsForIds(List<String> ids ) {
        return given().baseUri(baseUrl).queryParam("id", ids).when().get("/objects").then().extract().response();
    }
}
