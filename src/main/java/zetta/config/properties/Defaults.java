package zetta.config.properties;

@SuppressWarnings("java:S2386")
public interface Defaults {

    interface ApiDocs {

        String title = "Application API";
        String description = "API documentation";
        String version = "0.0.1";
        String termsOfServiceUrl = null;
        String contactName = "Nikola Todorov";
        String contactUrl = null;
        String contactEmail = null;
        String license = null;
        String licenseUrl = null;
        String[] defaultIncludePattern = {"/**"};
        String[] managementIncludePattern = {"/management/**"};
        String host = null;
        String[] protocols = {};
        boolean useDefaultResponseMessages = true;
    }

}
