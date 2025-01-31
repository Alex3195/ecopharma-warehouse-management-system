package uz.duol.ecopharmwarehouse.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {


    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("info@duol.uz");
        contact.setName("DUOL llc");
        contact.setUrl("https://www.duol.uz");

        Info info = new Info()
                .title("EcoPharmaWarehouse API")
                .version("1.0")
                .contact(contact)
                .description("EcoPharmaWarehouse API description.")
                .termsOfService("https://www.google.com/terms")
                .license(new License().name("MIT License").url("https://choosealicense.com/licenses/mit/"));

        return new OpenAPI().info(info)
                .components(new Components());
    }
}