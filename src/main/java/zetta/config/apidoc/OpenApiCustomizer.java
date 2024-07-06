package zetta.config.apidoc;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import zetta.config.properties.Properties;

@Configuration
public class OpenApiCustomizer implements org.springdoc.core.customizers.OpenApiCustomizer, Ordered {

    /**
     * The default order for the customizer.
     */
    public static final int DEFAULT_ORDER = 0;

    private int order = DEFAULT_ORDER;

    private final Properties.ApiDocs properties;

    public OpenApiCustomizer(Properties.ApiDocs properties) {
        this.properties = properties;
    }

    @Override
    public void customise(OpenAPI openAPI) {
        Contact contact = new Contact().name(properties.getContactName())
                .url(properties.getContactUrl()).email(properties.getContactEmail());

        openAPI.info(new Info().contact(contact).title(properties.getTitle())
                .description(properties.getDescription()).version(properties.getVersion())
                .termsOfService(properties.getTermsOfServiceUrl()).license(new License()
                        .name(properties.getLicense()).url(properties.getLicenseUrl())));

        for (Properties.ApiDocs.Server server : properties.getServers()) {
            openAPI.addServersItem(
                    new Server().url(server.getUrl()).description(server.getDescription()));
        }
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
