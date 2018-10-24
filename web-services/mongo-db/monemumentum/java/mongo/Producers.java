

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.bson.Document;


// RequestScoped 
@RequestScoped
public class Producers {

    // MongoClient - MongoDatabase
    private MongoClient mongoClient;
    private MongoDatabase database;
    // RequestSciped : mongoclinet; database ; 
    
    private MongoClient getMongoClient() {
        if (mongoClient == null) {
            String host = System.getProperty("mongo.host", "localhost");
            String port = System.getProperty("mongo.port", "27017");
            mongoClient = new MongoClient(host, Integer.parseInt(port));
            // host - port - init mongoClient
        }
        return mongoClient;
    }
    
    // Producer : mongoDatabase : 
    @Produces
    private MongoDatabase getDatabase() {
        if (database == null) {
            database = getMongoClient().getDatabase("monumentum");
        }
        
        return database;
    }
    
    @Produces
    @Collection
    public MongoCollection<Document> getCollection(InjectionPoint injectionPoint) {
        Collection mc = injectionPoint.getAnnotated().getAnnotation(Collection.class);
        return getDatabase().getCollection(mc.value());
    }
}
