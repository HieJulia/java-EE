

import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;


/*
 * This demonstartes use of ResourceConfig to configure resources during deploymnen
 */
@Provider
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends ResourceConfig {

    // Logger 
    private static final Logger logger = Logger.getLogger(ApplicationConfig.class.getName());

    // Application config 
    public ApplicationConfig(@Context ServletContext context) {
        // @Context : Servelt 

        //Registers specifc components
        // Register specific components 
        register(MultiPartFeature.class);// multipart 
        register(LoggingFilter.class);// logging filter 
        register(DeclarativeLinkingFeature.class);// declarative linking feature 
        //Register components in a pcackage
        packages("com.packtpub.rest.ch5.hateoas.jersey");
        packages("com.packtpub.rest.ch5.hateoas.jaxrs");
  
    }

}
