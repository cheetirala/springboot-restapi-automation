package runners;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "steps,hooks,config")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty, json:target/cucumber.json, html:target/cucumber.html")
public class TestRunner {}

