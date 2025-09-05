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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Value("${security.server-url}")
    private static String serverUrl;

    private static final String SECURITY_SCHEME_NAME = "JWT";

    private static final Map<String, String> PROFILE_SERVER_URL_MAP = Map.of(
            "local", "http://localhost:8080",
            "dev", serverUrl
    );

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(securityRequirement())
                .servers(initializeServers())
                .components(components());
    }

    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList(SECURITY_SCHEME_NAME);
    }

    private Info apiInfo() {
        return new Info()
                .title("Bridgers API")
                .description(getDescription());
    }

    private List<Server> initializeServers() {
        return PROFILE_SERVER_URL_MAP.entrySet().stream()
                .map(entry -> openApiServer(entry.getValue(), "Bridgers API " + entry.getKey().toUpperCase()))
                .collect(Collectors.toList());
    }

    private Server openApiServer(String url, String description) {
        return new Server().url(url).description(description);
    }

    private Components components() {
        return new Components().addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()

                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

    }

    private String getDescription() {
        return """
            Bridgers API 입니다.

            엑세스 토큰 값을 넣어주세요.
            """;
    }

}
