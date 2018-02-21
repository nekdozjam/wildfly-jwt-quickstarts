package org.jboss.quickstarts.jaxrsjwt.client;

import org.jboss.quickstarts.jaxrsjwt.model.Jwt;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class JwtRestClient {
    private static final String AUTHZ_HEADER = "Authorization";
    private static final String REST_TARGET_URL = "http://localhost:8080/example-jaxrs-jwt/rest";
    private static final String ADMIN_NAME = "admin";
    private static final String ADMIN_PW = "adminpw";
    private static final String CUSTOMER_NAME = "customer";
    private static final String CUSTOMER_PW = "customerpw";

    private String username;
    private String password;

    public JwtRestClient(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static void main(String[] args) {
        JwtRestClient adminClient = new JwtRestClient(ADMIN_NAME, ADMIN_PW);
        adminClient.test();
        JwtRestClient customerClient = new JwtRestClient(CUSTOMER_NAME, CUSTOMER_PW);
        customerClient.test();
    }

    public void test() {
        System.out.println("-----------------------");
        System.out.println("Testing "+username+" ");
        System.out.println("-----------------------");
        System.out.println("Obtaining JWT...");
        Form form = new Form();
        form.param("username", username);
        form.param("password", password);
        Jwt jwt = ClientBuilder.newClient().target(REST_TARGET_URL).path("/token").request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Jwt.class);
        if (jwt == null) {
            System.out.println("Failed to obtain JWT.");
            return;
        }
        String authzHeaderValue = "Bearer " + jwt.getToken();
        getUsingJwt("/protected", authzHeaderValue);
        getUsingJwt("/public", authzHeaderValue);
        getUsingJwt("/customer", authzHeaderValue);
        getUsingJwt("/claims", authzHeaderValue);

    }

    private void getUsingJwt(String path, String authzHeaderValue) {
        System.out.println("Accessing " + path + "...");
        Response response = ClientBuilder.newClient().target(REST_TARGET_URL).path(path).request().header(AUTHZ_HEADER, authzHeaderValue).get();
        System.out.println("Status: " + response.getStatus()+"\n"+response.readEntity(String.class));
        response.close();
    }
}