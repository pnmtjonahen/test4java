#Testing for java developers
###Philippe Tjon-A-Hen
<!-- .slide: data-background="#e98300" -->
---
###Why
```
Feature: Testing4JavaDevelopers

  Scenario: Test my stuff
    Given I am a java developer
    When I write code
    Then I want it tested
```
---
#Maven
&quot;Its Maven Jim, just as we know it, just as we know it.&quot;
---
#Maven
No frills maven.

```xml
<dependency>
    <groupId>info.cukes</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>1.2.5</version>
    <scope>test</scope>    
</dependency>
<dependency>
    <groupId>info.cukes</groupId>
    <artifactId>cucumber-java8</artifactId>
    <version>1.2.5</version>
    <scope>test</scope>    
</dependency>
```
---
#jUnit
&quot;Its jUnit Jim, just as we know it, just as we know it.&quot;
---
#jUnit
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```
---
#jUnit
```java
@RunWith(Cucumber.class)
public class TomatoTest {
}
```
---
#Gherkin
&quot;Its gherkin Jim, bud not as we know it, not as we know it.&quot;
Geven-when-then
```
Feature: Testing4JavaDevelopers

  Scenario: Test my stuff
    Given I am a java developer
    When I write code
    Then I want it tested
```
---
#Gherkin
Friendly? Cucumber helps.

```
You can implement missing steps with the snippets below:

Given("^I am a java developer$", () -> {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
});

When("^I write code$", () -> {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
});

Then("^I want it tested$", () -> {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
});

```
---
#Glue code, aka Stepdefs
```java
public class DeveloperStepdefs implements En {

    private Developer sut;
    private String result;

    public DeveloperStepdefs() {
        Given("^I am a java developer$", () -> {
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
```
---
<!-- .slide: data-background="#e98300" -->
# The End
