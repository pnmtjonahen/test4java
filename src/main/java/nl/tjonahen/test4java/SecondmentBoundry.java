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

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
@Path("secondment")
public class SecondmentBoundry {

    @Inject
    private SecondmentService secondmentService;
    /**
     * add new company to the system
     *
     * @param companyName the company name
     */
    @POST
    @Path("/company")
    public void addCompany(String companyName) {
        secondmentService.addCompany(companyName);
    }

    /**
     * Add a developer to a company
     * @param company the company
     * @param name the developer
     */
    @POST
    @Path("/company/{company}")
    public void addDeveloper(@PathParam("company") String company, String name) {
        final JavaDeveloper javaDeveloper = new JavaDeveloper();
        javaDeveloper.setName(name);
        secondmentService.getCompany(company).addDeveloper(javaDeveloper); 
    }

    /**
     * Get the earnings of a company
     * @param company the company
     * @return the earnings
     */
    @GET
    @Path("/company/{company}/earnings")
    public String getEarnings(@PathParam("company") String company) {
        return secondmentService.getEarnings(company);
    }

    /**
     * Send a developer from a company on a job to a contractor
     * @param company the company
     * @param developer the developer 
     * @param contractor the contractor
     */
    @POST
    @Path("/job/{company}/developer/{developer}")
    public void sendDeveloperOnJob(@PathParam("company") String company, @PathParam("developer") String developer, String contractor) {
        final JavaDeveloper javaDeveloper = secondmentService.getCompany(company).getDeveloper(developer);
        final Job job = new Job();
        secondmentService.getCompany(company).setEarnings(job.send(javaDeveloper, new Contractor(contractor)));
        
    }

}
