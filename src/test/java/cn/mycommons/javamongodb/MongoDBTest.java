package cn.mycommons.javamongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * MongoDBTest <br/>
 * Created by Leon on 2017-09-18.
 */
public class MongoDBTest {

    private MongoClient client;

    @Before
    public void before() {
        String url = "mongodb://127.0.0.1:27017/abc123";
        MongoClientURI uri = new MongoClientURI(url);
        client = new MongoClient(uri);
    }

    @Test
    public void testFind() {
        MongoDatabase database = client.getDatabase("abc123");
        MongoCollection<Document> collection = database.getCollection("user");

        for (Document document : collection.find()) {
            for (Map.Entry<String, Object> entry : document.entrySet()) {
                System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");
            }
            System.out.println();
        }
    }

    @Test
    public void testInsert() {
        testFind();

        MongoDatabase database = client.getDatabase("abc123");
        MongoCollection<Document> collection = database.getCollection("user");
        Document document = new Document();
        document.put("name", "World");
        document.put("age", 33);
        collection.insertOne(document);

        testFind();
    }

    @Test
    public void testUpdate() {
        System.out.println("before update");
        testFind();

        MongoDatabase database = client.getDatabase("abc123");
        MongoCollection<Document> collection = database.getCollection("user");
        collection.updateMany(
                Filters.eq("name", "World"),
                Updates.set("age", 44)
        );

        System.out.println();
        System.out.println();
        System.out.println("after update");
        testFind();
    }

    @Test
    public void testDelete() {
        System.out.println("before delete");
        testFind();

        MongoDatabase database = client.getDatabase("abc123");
        MongoCollection<Document> collection = database.getCollection("user");
        collection.deleteMany(
                Filters.eq("name", "World")
        );

        System.out.println();
        System.out.println();
        System.out.println("after delete");
        testFind();
    }
}