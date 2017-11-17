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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
public class Company {

    private final Client client;
    private final JavaDeveloper javaDeveloper;
    public Company(final JavaDeveloper javaDeveloper) {
        this.client = Client.create();
        this.javaDeveloper = javaDeveloper;
    }

    public String send(final Contractory contractor) {
        
        final WebResource webResource = this.client.resource("http://localhost:8888/"+contractor.getName());
        
        final String inputData = String.format("{\"name\":\"%s\"}", this.javaDeveloper.getName());
        
        final ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputData);
        if (response.getStatus() != 200) {
            throw new RuntimeException("HTTP Response: " + response.getStatus());
        }
        System.out.println("======================> Response received : " + response.toString());
        return response.getEntity(String.class);

    }

}
