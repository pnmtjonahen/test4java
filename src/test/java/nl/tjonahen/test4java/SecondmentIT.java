/*
 * Copyright (C) 2017 Philippe Tjon - A - Hen philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.tjonahen.test4java;

import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import java.io.File;
import java.net.URL;
import nl.tjonahen.test4java.config.ApplicationConfig;
import org.hamcrest.Matchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
@RunWith(Arquillian.class)
public class SecondmentIT {

    private final WireMock wiremock = new WireMock(8888);

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .resolve()
                .withTransitivity()
                .asFile();
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "nl.tjonahen.test4java")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                .addAsLibraries(files);
    }

    @ArquillianResource
    private URL contextPath;

    @Test
    @InSequence(1)
    public void testAddCompany() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"aCompany\"}")
                .when()
                .post(contextPath.toString() + "api/secondment/company")
                .then()
                .statusCode(204);
    }

    @Test
    @InSequence(2)
    public void testAddJavaDeveloper() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"me\"}")
                .when()
                .post(contextPath.toString() + "api/secondment/company/aCompany")
                .then()
                .statusCode(204);

    }

    @Test
    @InSequence(3)
    public void testSendJavaDeveloperOnAJob() {
        wiremock.register(post(urlEqualTo("/BigJob"))
                .withRequestBody(containing("me"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("100")));

        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"BigJob\"}")
                .when()
                .post(contextPath.toString() + "api/secondment/job/aCompany/developer/me")
                .then()
                .statusCode(204);
    }

    @Test
    @InSequence(4)
    public void testGetEarnings() {
        RestAssured.registerParser("text/plain", Parser.TEXT);        
        given()
                .when()
                .get(contextPath.toString() + "api/secondment/company/aCompany/earnings")
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(Matchers.equalTo("100"));

    }
}
