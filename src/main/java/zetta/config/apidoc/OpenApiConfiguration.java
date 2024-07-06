package zetta.config.apidoc;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zetta.config.properties.Properties;

@Configuration
public class OpenApiConfiguration {

    public static final String OPEN_API_GROUP = "OpenApi";
    public static final String API_FIRST_PACKAGE_SCAN_API = "zetta.api";
    public static final String API_FIRST_PACKAGE_SCAN_DTO = "zetta.dto";

    public static final String GENERATED_CONTROLLERS = "Generated CRUD Api";
    public static final String GENERATED_API_SCAN = "zetta.controller";

    @Bean
    GroupedOpenApi generatedApi(OpenApiCustomizer openApiCustomizer,
                                Properties properties) {

        return GroupedOpenApi.builder().group(GENERATED_CONTROLLERS)
                .addOpenApiCustomizer(openApiCustomizer)
                .packagesToScan(GENERATED_API_SCAN)
                .displayName(GENERATED_CONTROLLERS).build();
    }

    @Bean
    GroupedOpenApi apiFirstGroupedOpenAPI(
            OpenApiCustomizer openApiCustomizer,
            Properties properties) {

        return GroupedOpenApi.builder().group(OPEN_API_GROUP)
                .addOpenApiCustomizer(openApiCustomizer)
                .packagesToScan(API_FIRST_PACKAGE_SCAN_API, API_FIRST_PACKAGE_SCAN_DTO)
                .displayName(OPEN_API_GROUP).build();
    }

}
