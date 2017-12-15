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
package nl.tjonahen.test4java.stepdefs;

import cucumber.api.java8.En;

import nl.tjonahen.test4java.Developer;
import org.junit.Assert;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
public class DeveloperStepdefs implements En {

    private Developer sut;
    private String result;
    
    public DeveloperStepdefs() {
        Given("^I am a java developeer$", () -> {
            sut = new Developer();
        });

        When("^I write code$", () -> {
            result = sut.writeCode();
        });

        Then("^I want it tested$", () -> {
            Assert.assertEquals("Code, code, code, code, code, code, code", result);
        });
    }

}
