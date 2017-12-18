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
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.arquillian.CukeSpace;
import cucumber.runtime.arquillian.api.Features;
import java.io.File;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
@RunWith(CukeSpace.class)
@Features({"src/test/resources/nl/tjonahen/test4java/secondment.feature"})
public class SecondmentTest {

    private static final String A_COMPANY = "ACompany";
    private static final String WEBAPP_SRC = "src/main/webapp";

    private String devName;
    private final WireMock wiremock = new WireMock(8888);    

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SecondmentBoundry.class.getPackage())
                .addAsManifestResource(new File(WEBAPP_SRC, "WEB-INF/beans.xml"));
    }

    @Inject
    private SecondmentBoundry boundry;

    @Given("^A company with a java developer \"([^\"]*)\"$")
    public void a_company_with_a_java_developer(String devName) throws Throwable {
        boundry.addCompany(A_COMPANY);
        boundry.addDeveloper(A_COMPANY, devName);
        this.devName = devName;
    }

    @When("^We send the developer on a job at \"([^\"]*)\"$")
    public void we_send_the_developer_on_a_job_at(String jobName) throws Throwable {
            wiremock.register(post(urlEqualTo("/"+jobName))
                    .withRequestBody(containing(devName))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withBody("0")));

        boundry.sendDeveloperOnJob(A_COMPANY, this.devName, jobName);
    }

    @Then("^We recieve money$")
    public void we_recieve_money() throws Throwable {
        assertEquals("0", boundry.getEarnings(A_COMPANY));
    }
}
