package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import utilities.ConfigReader;

import static io.restassured.RestAssured.given;

public class JsonPlaceHolderStepdefinitions {
    String endpoint;
    Response response;
    JsonPath responseJP;
    JSONObject requestBody;

    @Given("Kullanici {string} base URL'ini kullanir")
    public void kullaniciBaseURLIniKullanir(String string) {
        endpoint = ConfigReader.getProperty("jPHBaseUrl");
    }

    @Then("Path parametreleri icin {string} kullanir")
    public void pathParametreleriIcinKullanir(String pathparams) {
        endpoint = endpoint + "/" + pathparams;
    }

    @And("jPH server a GET request gonderir ve testleri yapmak icin response degerini kaydeder")
    public void jphServerAGETRequestGonderirVeTestleriYapmakIcinResponseDegeriniKaydeder() {
        response = given().when().get(endpoint);
        response.prettyPrint();
    }

    @Then("jPH respons'da status degerinin {int}")
    public void jphResponsDaStatusDegerinin(Integer statusCode) {
        Assert.assertEquals(statusCode, (Integer) response.statusCode());
    }

    @And("jPH respons'da content type degerinin {string}")
    public void jphResponsDaContentTypeDegerinin(String contentType) {
        Assert.assertEquals(contentType,response.contentType());
    }

    @Then("jPH GET respons body'sinde {string} degerinin Integer {int}")
    public void jphGETResponsBodySindeDegerininInteger(String attribute, Integer expectedValue) {
        responseJP = response.jsonPath();
        Assert.assertEquals(expectedValue,(Integer) responseJP.getInt(attribute));
    }

    @And("jPH GET respons body'sinde {string} degerinin String {string}")
    public void jphGETResponsBodySindeDegerininString(String attribute, String expectedValue) {
        responseJP = response.jsonPath();
        Assert.assertEquals(expectedValue,responseJP.getString(attribute));
    }

    @And("POST request icin {string},{string},{int} {int} bilgileri ile request body olusturur")
    public void postRequestIcinBilgileriIleRequestBodyOlusturur(String title, String body, int userId, int id) {
        requestBody = new JSONObject();
        requestBody.put("title",title);
        requestBody.put("body",body);
        requestBody.put("userId",userId);
        requestBody.put("id",id);
    }

    @And("jPH server a POST request gonderir ve testleri yapmak icin response degerini kaydeder")
    public void jphServerAPOSTRequestGonderirVeTestleriYapmakIcinResponseDegeriniKaydeder() {
        response = given()
                        .when().body(requestBody.toString()).contentType(ContentType.JSON)
                        .put(endpoint);
        response.prettyPrint();
    }

    @And("jPH respons daki {string} header degerinin {string}")
    public void jphResponsDakiHeaderDegerinin(String headerAttribute, String attributeValue) {
        Assert.assertEquals(attributeValue,response.header(headerAttribute));
    }

    @Then("response attributelerindeki degerlerin {string},{string},{int} ve {int}")
    public void responseAttributelerindekiDegerlerinVe(String title, String body, int userId, int id) {
        responseJP = response.jsonPath();
        Assert.assertEquals(title,responseJP.getString("title"));
        Assert.assertEquals(body,responseJP.getString("body"));
        Assert.assertEquals(userId,responseJP.getInt("userId"));
        Assert.assertEquals(id,responseJP.getInt("id"));
    }

}
