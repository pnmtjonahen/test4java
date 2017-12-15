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

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
public class Company {
    private final String name;
    private final Map<String, JavaDeveloper> developers = new TreeMap<>();
    private String earnings;
    
    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void addDeveloper(JavaDeveloper developer) {
        developers.put(developer.getName(), developer);
    }
    
    public JavaDeveloper getDeveloper(String name) {
        return developers.get(name);
    }

    JavaDeveloper getDeveloper(JavaDeveloper developer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setEarnings(String earnings) {
        this.earnings = earnings;
    }

    public String getEarnings() {
        return earnings;
    }
    
}
