Jetty Gradle Hello World, TDD Style
=====

I want to make a simple web app with Jetty, using Gradle as the build
tool.  But I'm big on Test Driven Development (TDD), so I want to do it
TDD style.  I'll be checking in each step to my git repository at 
https://github.com/ziroby/jetty-gradle-hello-world

My first task is to get a simple Gradle build file in place.  Looking at
http://stackoverflow.com/questions/7864521/gradle-jettyrun-how-does-this-thing-work
, I get a good starter for a build.gradle file.  I add boiler-plate
java build/test stuff, and my build.gradle looks like:

```groovy
apply plugin: 'java'
apply plugin: 'jetty'

repositories {
    mavenCentral()
}
dependencies {
    testCompile 'junit:junit:4.11'
    testCompile 'org.hamcrest:hamcrest-all:1.3'

}
test {
    exclude '**/*IntegrationTest*'
}

task integrationTest(type: Test) {
    include '**/*IntegrationTest*'
    doFirst {
        jettyRun.httpPort = 8080    // Port for test
        jettyRun.daemon = true
        jettyRun.execute()
    }
    doLast {
        jettyStop.stopPort = 8091   // Port for stop signal
        jettyStop.stopKey = 'stopKey'
        jettyStop.execute()
    }
}
```

I run `gradle build` and get success.  Ready for my first test.

I want to work from the outside in, so my first test is a test for the
web service.  I'm doing "Hello World", so I want a RESTful server that
provides "Hello World" when I do a GET to the top level.  But I want
to arrange our server correctly, so I'll have a separate engine that is
called by the server classes.  I'll start with a test for the server.

```java
public class HelloIntegrationTest {
    private static String HELLO_URL = "http://localhost:8080/hello";
    
    @Test
    public void testHello() throws Exception {
        Client client = Client.create();
        WebResource webResource = client.resource(HELLO_URL);
        String response = webResource.get(String.class);
        
        assertThat(response, is("Hello, World!"));
    }
}
```

I also pull in Jersey for the web classes.  (I might be being redundant
by having both Jetty and Jersey, but I couldn't find a good way to start
a Jersey server in Gradle, and I couldn't find the JAX-RS stuff in Jetty)

```groovy
dependencies {
    testCompile 'junit:junit:4.11'
    testCompile 'org.hamcrest:hamcrest-all:1.3'
    testCompile 'com.sun.jersey:jersey-client:1.17.1'
    testCompile 'com.sun.jersey:jersey-core:1.17.1'
}
```

`gradle integrationTest` gets a 404, so the test is written and I can
now write code to make the test pass.
