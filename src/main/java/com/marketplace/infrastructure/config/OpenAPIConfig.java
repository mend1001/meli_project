package com.marketplace.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Marketplace API",
                version = "1.0.0",
                description = "API REST para un marketplace con arquitectura hexagonal. Permite consultar productos y sus detalles.",
                contact = @Contact(
                        name = "Miguel Angel Mendigaño A",
                        email = "tmend1001mend1001@gmail.com",
                        url = "https://www.linkedin.com/in/miguel-angel-mendigano-a-476b7a227/"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Servidor de Desarrollo Local",
                        url = "http://localhost:8080"
                )
        },
        tags = {
                @Tag(name = "Productos", description = "Endpoints para gestión de productos"),
                @Tag(name = "Vendedores", description = "Endpoints para gestión de vendedores")
        }
)
public class OpenAPIConfig {

}