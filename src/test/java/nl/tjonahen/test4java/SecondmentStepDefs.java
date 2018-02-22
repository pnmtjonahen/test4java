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
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
public class SecondmentStepDefs {

    private static final String A_COMPANY = "ACompany";

    private String devName;
    private final WireMock wiremock = new WireMock(8888);    
    private SecondmentEndPoint endpoint = new SecondmentEndPoint();

    @Given("^A company with a java developer \"([^\"]*)\"$")
    public void a_company_with_a_java_developer(String devName) throws Throwable {
        endpoint.addCompany(A_COMPANY);
        endpoint.addDeveloper(A_COMPANY, devName);
        this.devName = devName;
    }

    @When("^We send the developer on a job at \"([^\"]*)\"$")
    public void we_send_the_developer_on_a_job_at(String jobName) throws Throwable {
        wiremock.register(post(urlEqualTo("/"+jobName))
                    .withRequestBody(containing(devName))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withBody("0")));

        endpoint.sendDeveloperOnJob(A_COMPANY, this.devName, jobName);
        
        wiremock.verifyThat(WireMock.postRequestedFor(urlEqualTo("/"+jobName)));
    }

    @Then("^We recieve money$")
    public void we_recieve_money() throws Throwable {
        assertEquals("0", endpoint.getEarnings(A_COMPANY));
    }
}
