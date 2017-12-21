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


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
public class Company {

    private final Client client;
    private final JavaDeveloper javaDeveloper;
    public Company(final JavaDeveloper javaDeveloper) {
        this.client = ClientBuilder.newClient();
        this.javaDeveloper = javaDeveloper;
    }

    public String send(final Contractor contractor) {
        
        final WebTarget wegTarget = this.client
                                            .target("http://localhost:8888/{contractor}")
                                            .resolveTemplate("contractor", contractor.getName());
        
        
        final Response response = wegTarget.request("application/json").post(Entity.json(this.javaDeveloper), Response.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("HTTP Response: " + response.getStatus());
        }
        return response.readEntity(String.class);

    }

}
