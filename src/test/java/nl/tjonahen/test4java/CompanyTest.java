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
import org.junit.Test;

/**
 *
 * @author Philippe Tjon - A - Hen philippe@tjonahen.nl
 */
public class CompanyTest {
    private static final String BIG_COMPANY = "BigCompany";

    @Test
    public void testSend() {
        WireMock wiremock = new WireMock(8888);
        wiremock.register(post(urlEqualTo("/"+BIG_COMPANY))
                    .withRequestBody(containing("me"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withBody("0")));

        Company ordina = new Company(new JavaDeveloper("me"));
        ordina.send(new Contractor(BIG_COMPANY));
        
        wiremock.verifyThat(WireMock.postRequestedFor(urlEqualTo("/"+BIG_COMPANY)));
        
    }
}
