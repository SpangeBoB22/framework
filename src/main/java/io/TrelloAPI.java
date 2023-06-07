package io;
import io.model.Cart;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class TrelloAPI {

    public Response createNewCart(Cart cart) {
        return RestAssured.given()
                .log().all()
                .queryParam("key", Constants.API_KEY)
                .queryParam("token", Constants.API_TOKEN)
                .queryParam("idList", Constants.ID_LIST_FIRST)
                .body("{\"name\":\"" + cart.getName() +"\",\"desc\":\"description\"}")
                .when()
                .contentType(ContentType.JSON)
                .post("/cards")
                .then()
                .extract().response();
    }
    public Response updateDescCart(Cart cart) {
        return RestAssured.given()
                .log().all()
                .queryParam("key", Constants.API_KEY)
                .queryParam("token", Constants.API_TOKEN)
                .queryParam("idList", Constants.ID_LIST_FIRST)
                .body("{\"desc\":\"" + cart.getDescription() + "\"}")
                .when()
                .contentType(ContentType.JSON)
                .put("/cards/" + cart.getIdCart())
                .then()
                .extract().response();
    }
    public Response updateMemberCart(Cart cart) {
        return RestAssured.given()
                .log().all()
                .queryParam("key", Constants.API_KEY)
                .queryParam("token", Constants.API_TOKEN)
                .queryParam("idList", Constants.ID_LIST_FIRST)
                .body("{\"idMembers\":[\"" + cart.getIdMember() + "\"]}")
                .when()
                .contentType(ContentType.JSON)
                .put("/cards/" + cart.getIdCart())
                .then()
                .log().all()
                .extract().response();
    }
    public Response updateIdListCart(Cart cart, String idList) {
        return RestAssured.given()
                .log().all()
                .queryParam("key", Constants.API_KEY)
                .queryParam("token", Constants.API_TOKEN)
                .queryParam("idList", idList)
                .when()
                .contentType(ContentType.JSON)
                .put("/cards/" + cart.getIdCart())
                .then()
                .log().all()
                .extract().response();
    }
}
