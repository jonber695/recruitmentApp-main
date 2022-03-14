package iv1201.group7.recruitmentApp.config;

/**
 * @author Group 7
 */
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Loads properties from application.properties.
 */
@ConfigurationProperties(prefix = "iv1201.group7.recruitment-app.server")
public class ServerProperties {
    private String contextRoot;

    /**
     * @return The website's context root.
     */
    public String getContextRoot() {
        return contextRoot;
    }

    /**
     * @param contextRoot Sets a new context root for the website.
     */
    public void setContextRoot(String contextRoot) {
        this.contextRoot = contextRoot;
    }
}