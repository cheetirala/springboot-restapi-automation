package clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ObjectsClient {

    private final String baseUrl;

    public ObjectsClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response createObject(String name, Map<String, Object> data) {
        Map<String, Object> body = Map.of("name", name, "data", data);

        return given().baseUri(baseUrl).contentType(ContentType.JSON).body(body).when().post("/objects").then().extract().response();
    }

    public Response updateObject(String id, String name, Map<String, Object> data) {
        Map<String, Object> body = Map.of("name", name, "data", data);

        return given().baseUri(baseUrl).contentType(ContentType.JSON).body(body).when().put("/objects/{id}", id).then().extract().response();
    }

    public Response getObject(String id) {
        return given().baseUri(baseUrl).when().get("/objects/{id}", id).then().extract().response();
    }

    public Response deleteObject(String id) {
        return given().baseUri(baseUrl).when().delete("/objects/{id}", id).then().extract().response();
    }

    public Response getObjectsForIds(List<String> ids ) {
        return given().baseUri(baseUrl).queryParam("id", ids).when().get("/objects").then().extract().response();
    }
}
