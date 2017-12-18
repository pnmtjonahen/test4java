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
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import java.io.File;
import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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

    private static final String A_COMPANY = "ACompany";
    private static final String WEBAPP_SRC = "src/main/webapp";

    private String devName;
    private final WireMock wiremock = new WireMock(8888);

    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .resolve()
                .withTransitivity()
                .asFile();
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(SecondmentBoundry.class.getPackage())
                .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/beans.xml"))
                .addAsLibraries(files);
    }

    @ArquillianResource
    private URL contextPath;

    @Test
    public void testAddCompany() {
        given()
                .body("{\"companyName\":\"aCompany\" }")
                .contentType(ContentType.JSON)
                .post(contextPath.toExternalForm() + "/company")
                .then()
                .statusCode(201);

    }

}
