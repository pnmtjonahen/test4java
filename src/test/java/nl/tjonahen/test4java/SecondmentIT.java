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
import java.io.File;
import java.net.URL;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import static org.junit.Assert.assertEquals;
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
                .addPackage(SecondmentBoundry.class.getPackage())
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                .addAsLibraries(files);
    }

    @ArquillianResource
    private URL contextPath;

    @Test
    @InSequence(1)
    @RunAsClient
    public void testAddCompany(@ArquillianResteasyResource final WebTarget webTarget) {
        final Response response = webTarget
                .path("/api/secondment/company")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json("{\"name\":\"aCompany\"}"));
        assertEquals(204, response.getStatus());
    }

    @Test
    @InSequence(2)
    public void testAddJavaDeveloper() {
//        given()
//                .contentType(ContentType.JSON)
//                .body("{\"name\":\"me\"}")
//                .when()
//                .post(contextPath.toString() + "api/secondment/company/aCompany")
//                .then()
//                .statusCode(204);

    }

    @Test
    @InSequence(3)
    public void testSendJavaDeveloperOnAJob() {
//        given()
//                .contentType(ContentType.JSON)
//                .body("{\"name\":\"BigJob\"}")
//                .when()
//                .post(contextPath.toString() + "api/secondment/job/aCompany/developer/me")
//                .then()
//                .statusCode(204);

    }

}
