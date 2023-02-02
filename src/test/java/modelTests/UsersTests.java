import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import models.Users;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.swing.text.html.parser.Parser;

import static io.restassured.RestAssured.given;

public class GoRest {

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = "https://gorest.co.in/";
    }

    @Test
    public void get(){
        Response response = given().when().log().all().get("public/v2/users")
                .then().log().all().extract().response();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void post() throws ParseException {
        Users users = Users.builder().name("john").email("emai123@gmail.com").gender("male").status("inactive").build();

        Response response = given().header(new Header("Accept", "application/json"))
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Authorization", "Bearer a2a1162b3a6a3dd6bbcd1c9971fcc9afe56d3b394c6ed6332630c642495b31e7"))
                .body(users).log().all().post("public/v2/users")
                .then().log().all().extract().response();
        Assert.assertEquals(response.statusCode(), 201);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject1 =  (JSONObject) parser.parse(response.getBody().asString());


        response = given().header(new Header("Accept", "application/json"))
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Authorization", "Bearer a2a1162b3a6a3dd6bbcd1c9971fcc9afe56d3b394c6ed6332630c642495b31e7"))
                .log().all()
                .when().delete("public/v2/users/" + jsonObject1.get("id").toString())
                .then().log().all().extract().response();
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test
    public void put() throws ParseException {
        Users users = Users.builder().name("john").email("emai1767xfdasd4675@gmail.com").gender("male").status("inactive").build();

        //add the user to the system,
        Response response = given().header(new Header("Accept", "application/json"))
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Authorization", "Bearer a2a1162b3a6a3dd6bbcd1c9971fcc9afe56d3b394c6ed6332630c642495b31e7"))
                .body(users).log().all().post("public/v2/users")
                .then().log().all().extract().response();
        Assert.assertEquals(response.statusCode(), 201);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject1 =  (JSONObject) parser.parse(response.getBody().asString());

        users.setId(Integer.parseInt(jsonObject1.get("id").toString()));
        users.setStatus("active");
        users.setEmail("newemai5544676@gmail.com");


        System.out.println("========================================= " + users);

        response = given().body(users).header(new Header("Accept", "application/json"))
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Authorization", "Bearer a2a1162b3a6a3dd6bbcd1c9971fcc9afe56d3b394c6ed6332630c642495b31e7"))
                .log().all()
                .when().put("public/v2/users/" + jsonObject1.get("id").toString());

        Assert.assertEquals(response.statusCode(), 200);

        response = given().header(new Header("Accept", "application/json"))
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Authorization", "Bearer a2a1162b3a6a3dd6bbcd1c9971fcc9afe56d3b394c6ed6332630c642495b31e7"))
                .log().all()
                .when().delete("public/v2/users/" + jsonObject1.get("id").toString())
                .then().log().all().extract().response();
        Assert.assertEquals(response.statusCode(), 204);

        users.setStatus("active");
    }


    @Test
    public void delete() throws ParseException {
        Users users = Users.builder().name("john").email("emai1767xfdasd4675@gmail.com").gender("male").status("inactive").build();

        //add the user to the system,
        Response response = given().header(new Header("Accept", "application/json"))
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Authorization", "Bearer a2a1162b3a6a3dd6bbcd1c9971fcc9afe56d3b394c6ed6332630c642495b31e7"))
                .body(users).log().all().post("public/v2/users")
                .then().log().all().extract().response();
        Assert.assertEquals(response.statusCode(), 201);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject1 =  (JSONObject) parser.parse(response.getBody().asString());

        response = given().header(new Header("Accept", "application/json"))
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Authorization", "Bearer a2a1162b3a6a3dd6bbcd1c9971fcc9afe56d3b394c6ed6332630c642495b31e7"))
                .log().all()
                .when().delete("public/v2/users/" + jsonObject1.get("id").toString())
                .then().log().all().extract().response();
        Assert.assertEquals(response.statusCode(), 204);

        users.setStatus("active");
    }

}
