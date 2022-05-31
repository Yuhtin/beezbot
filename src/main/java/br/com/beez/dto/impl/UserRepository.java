package br.com.beez.dto.impl;
import br.com.beez.dto.MongoRepository;
import br.com.beez.dto.RepositoryCollection;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.bson.Document;

import java.util.LinkedList;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
@Accessors(fluent = true)
public class UserRepository implements MongoRepository<Boolean> {

    @Getter private static final UserRepository instance = new UserRepository();
    private static final Gson GSON = new GsonBuilder().create();

    @RepositoryCollection(collectionName = "users")
    private MongoCollection<Document> userTable;

    @Override
    public Boolean find(long id) {
        val document = userTable.find(Filters.eq("_id", id)).first();
        if (document == null) {
            //val userProfile = UserProfile.builder()._id(id).build();
            //insert(userProfile);

            return false;
        }

        //return GSON.fromJson(document.toJson(), UserProfile.class);
        return true;
    }

    public Boolean findOnlyExists(long id) {
        val document = userTable.find(Filters.eq("_id", id)).first();
        if (document == null) return null;

        //return GSON.fromJson(document.toJson(), UserProfile.class);
        return true;
    }

    @Override
    public void insert(Boolean data) {
        userTable.insertOne(Document.parse(GSON.toJson(data)));
    }

    @Override
    public void replace(Boolean data) {
        //userTable.replaceOne(Filters.eq("_id", data._id()), Document.parse(GSON.toJson(data)));
    }

    @Override
    public void delete(long id) {
        userTable.deleteOne(Filters.eq("_id", id));
    }

    @Override
    public LinkedList<Boolean> query(int maxValues) {
        val documents = Lists.newArrayList(userTable.find().sort(new BasicDBObject("recomendations", -1)).limit(maxValues).iterator());
        if (documents.isEmpty()) return Lists.newLinkedList();

        LinkedList<Boolean> users = Lists.newLinkedList();
        for (Document document : documents) {
            val json = document.toJson();
            //users.add(GSON.fromJson(json, UserProfile.class));
            users.add(true);
        }

        return users;
    }
}
