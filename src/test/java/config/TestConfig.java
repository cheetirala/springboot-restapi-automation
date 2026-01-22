package config;

import clients.ObjectsClient;
import io.cucumber.spring.ScenarioScope;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import support.ScenarioState;

@Configuration
@EnableConfigurationProperties(ApiProperties.class)
public class TestConfig {

    @Bean
    public ObjectsClient objectsClient(ApiProperties properties){
        return new ObjectsClient(properties.getBaseUrl());
    }

    @Bean
    @ScenarioScope
    public ScenarioState scenarioState(){
        return new ScenarioState();
    }
}
