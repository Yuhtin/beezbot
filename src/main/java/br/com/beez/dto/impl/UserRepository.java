package br.com.beez.dto.impl;

import br.com.beez.dto.MongoRepository;
import br.com.beez.dto.RepositoryCollection;
import br.com.beez.model.user.Profile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class UserRepository implements MongoRepository<Profile> {

    @Getter private static final UserRepository instance = new UserRepository();
    private static final Gson GSON = new GsonBuilder().create();

    @RepositoryCollection(collectionName = "users")
    private MongoCollection<Document> userTable;

    @Override
    public Profile find(long id) {
        val document = userTable.find(Filters.eq("_id", id)).first();
        if (document == null) {
            val userProfile = Profile.builder()._id(id).build();
            insert(userProfile);

            return userProfile;
        }

        return GSON.fromJson(document.toJson(), Profile.class);
    }

    public Profile findOnlyExists(long id) {
        val document = userTable.find(Filters.eq("_id", id)).first();
        if (document == null) return Profile.builder()._id(id).userId(id).build();

        return GSON.fromJson(document.toJson(), Profile.class);
    }

    @Override
    public void insert(Profile data) {
        userTable.insertOne(Document.parse(GSON.toJson(data)));
    }

    @Override
    public void replace(Profile data) {
        userTable.replaceOne(Filters.eq("_id", data._id()), Document.parse(GSON.toJson(data)));
    }

    @Override
    public void delete(long id) {
        userTable.deleteOne(Filters.eq("_id", id));
    }

    @Override
    public LinkedList<Profile> query(int maxValues) {
        return null;
    }
}
