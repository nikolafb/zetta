package zetta.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
public class Properties {

    private final ApiDocs apiDocs = new ApiDocs();

    public ApiDocs getApiDocs() {
        return apiDocs;
    }

    @Component
    @ConfigurationProperties(prefix = "config.api-docs")
    public static class ApiDocs {

        private String title = Defaults.ApiDocs.title;

        private String description = Defaults.ApiDocs.description;

        private String version = Defaults.ApiDocs.version;

        private String termsOfServiceUrl = Defaults.ApiDocs.termsOfServiceUrl;

        private String contactName = Defaults.ApiDocs.contactName;

        private String contactUrl = Defaults.ApiDocs.contactUrl;

        private String contactEmail = Defaults.ApiDocs.contactEmail;

        private String license = Defaults.ApiDocs.license;

        private String licenseUrl = Defaults.ApiDocs.licenseUrl;

        private String[] defaultIncludePattern = Defaults.ApiDocs.defaultIncludePattern;

        private String[] managementIncludePattern =
                Defaults.ApiDocs.managementIncludePattern;

        private Server[] servers = {};

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactUrl() {
            return contactUrl;
        }

        public void setContactUrl(String contactUrl) {
            this.contactUrl = contactUrl;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public String[] getDefaultIncludePattern() {
            return defaultIncludePattern;
        }

        public void setDefaultIncludePattern(String[] defaultIncludePattern) {
            this.defaultIncludePattern = defaultIncludePattern;
        }

        public String[] getManagementIncludePattern() {
            return managementIncludePattern;
        }

        public void setManagementIncludePattern(String[] managementIncludePattern) {
            this.managementIncludePattern = managementIncludePattern;
        }

        public Server[] getServers() {
            return servers;
        }

        public void setServers(Server[] servers) {
            this.servers = servers;
        }

        public static class Server {
            private String url;
            private String description;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }

}
