import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import com.mongodb.client.model.Filters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class Test {
    public static void main(String[] args) throws IOException {
        try (MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://127.0.0.1:27017/?gssapiServiceName=mongodb"))) {
            File file = new File("xyz.txt");
            FileWriter f = new FileWriter(file);
            for (String d : mongoClient.listDatabaseNames()) {
                f.write(d + "\n");
            }
            MongoDatabase database = mongoClient.getDatabase("userdb");
            try {
                database.createCollection("users");

            }
            catch(Exception e){
                database.getCollection("users").drop();
            }
            ArrayList<Document> docs = new ArrayList<>();
            MongoCollection<Document> collection = database.getCollection("users");
            Document d1 = new Document("_id",1);
            d1.append("name","RP");
            d1.append("age",17);
            docs.add(d1);
            Document d2 = new Document("_id",2);
            d2.append("name","User1");
            d2.append("age",23);
            docs.add(d2);
            Document d3 = new Document("_id",3);
            d3.append("name","User2");
            d3.append("age",47);
            docs.add(d3);
            collection.insertMany(docs);
            for (MongoCursor<Document> it = collection.find().iterator(); it.hasNext(); ) {
                Document d = it.next();
                f.write(d.toString()+"\n");
            }
            f.write("Found: "+Objects.requireNonNull(collection.find(Filters.eq("name", "RP")).first()).toString());
            f.close();
        }
    }
}
