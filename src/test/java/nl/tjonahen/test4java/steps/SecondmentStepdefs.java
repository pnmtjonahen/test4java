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
package nl.tjonahen.test4java.steps;

import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import cucumber.api.java8.En;
import nl.tjonahen.test4java.Company;
import nl.tjonahen.test4java.Contractory;
import nl.tjonahen.test4java.JavaDeveloper;
import org.junit.Assert;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
public class SecondmentStepdefs implements En {

    private final WireMock wiremock;
    private String devName;
    private String result;

    public SecondmentStepdefs() {
        wiremock = new WireMock(8888);

        Given("^A company with a java developer \"([^\"]*)\"$", (String devName) -> {
            this.devName = devName;
        });

        When("^We send the developer on a job at \"([^\"]*)\"$", (String jobName) -> {
            wiremock.register(post(urlEqualTo("/"+jobName))
                    .withRequestBody(containing(devName))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withBody("0")));

            Company ordina = new Company(new JavaDeveloper(devName));

            result = ordina.send(new Contractory(jobName));
        });

        Then("^We recieve money$", () -> {
            Assert.assertEquals("0", result);
        });

    }

}
