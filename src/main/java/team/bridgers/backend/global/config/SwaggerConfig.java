package team.bridgers.backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "JWT";

    @Bean
    public OpenAPI openAPI(@Value("${security.server-url}") String serverUrl) {
        Map<String, String> profileServerUrlMap = Map.of(
                "local", "http://localhost:8080",
                "dev", serverUrl
        );

        List<Server> servers = profileServerUrlMap.entrySet().stream()
                .map(entry -> new Server()
                        .url(entry.getValue())
                        .description("Bridgers API " + entry.getKey().toUpperCase()))
                .toList();

        return new OpenAPI()
                .servers(servers)
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .info(new Info().title("Bridgers API").description("엑세스 토큰 값을 넣어주세요."));
    }

}
