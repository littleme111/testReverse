package fr.afpa.balthazar.logic.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;



//Defines this file as spring configuration
@Configuration
//Sets component scan locaiton, all beans found will be added to context.
@ComponentScan(basePackages = "fr.afpa.balthazar")
public class AppConfig {

    //Defines message sourrce location for customized messages with internalization.
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

}
