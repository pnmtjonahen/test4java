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

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.config.CitrusSpringConfig;
import com.consol.citrus.dsl.design.TestDesigner;
import nl.tjonahen.test4java.JavaDeveloper;
import cucumber.api.java8.En;
import nl.tjonahen.test4java.Contractory;
import nl.tjonahen.test4java.Company;
import nl.tjonahen.test4java.TestConfiguration;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
@ContextConfiguration(classes = {TestConfiguration.class, CitrusSpringConfig.class})
public class SecondmentStepdefs implements En {

    private Company ordina;
    private String result;

    @CitrusResource
    private TestDesigner designer;

    public SecondmentStepdefs() {
        Given("^A company with a java developer \"([^\"]*)\"$", (String developer) -> {
            designer.variable("name", developer);
            ordina = new Company(new JavaDeveloper(developer));

        });

        When("^We send the developer on a job at \"([^\"]*)\"$", (String company) -> {
            designer.http()
                    .server("jobHttpServer")
                    .receive()
                    .post("/" + company)
                    .payload("{\"name\":\"${name}\"")
                    .contentType("application/json");
            designer.http().server("jobHttpServer")
                    .send()
                    .response(HttpStatus.OK)
                    .payload("0")
                    .version("HTTP/1.1")
                    .contentType("application/text");

            result = ordina.send(new Contractory(company));
        });

        Then("^We recieve money$", () -> {
            Assert.assertEquals("0", result);
        });

    }

}
