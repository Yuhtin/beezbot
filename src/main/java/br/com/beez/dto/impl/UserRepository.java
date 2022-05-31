package br.com.beez.dto.impl;

import br.com.beez.dto.MongoRepository;
import br.com.beez.dto.RepositoryCollection;
import br.com.beez.model.profile.UserProfile;
import br.com.beez.model.profile.SimpleWorker;
import br.com.beez.model.workarea.ExtendedWorkArea;
import com.google.common.collect.Lists;
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
public class UserRepository implements MongoRepository<UserProfile, SimpleWorker> {

    @Getter private static final UserRepository instance = new UserRepository();
    private static final Gson GSON = new GsonBuilder().create();

    @RepositoryCollection(collectionName = "users")
    private MongoCollection<Document> userTable;

    @Override
    public UserProfile find(long id) {
        val document = userTable.find(Filters.eq("_id", id)).first();
        if (document == null) {
            val userProfile = UserProfile.builder()._id(id).build();
            insert(userProfile);

            return userProfile;
        }

        return GSON.fromJson(document.toJson(), UserProfile.class);
    }

    public UserProfile findOnlyExists(long id) {
        val document = userTable.find(Filters.eq("_id", id)).first();
        if (document == null) return null;

        return GSON.fromJson(document.toJson(), UserProfile.class);
    }

    @Override
    public void insert(UserProfile data) {
        userTable.insertOne(Document.parse(GSON.toJson(data)));
    }

    @Override
    public void replace(UserProfile data) {
        userTable.replaceOne(Filters.eq("_id", data._id()), Document.parse(GSON.toJson(data)));
    }

    @Override
    public void delete(long id) {
        userTable.deleteOne(Filters.eq("_id", id));
    }

    @Override
    public LinkedList<SimpleWorker> query(int maxValues) {
        LinkedList<SimpleWorker> result = Lists.newLinkedList();
        for (val document : userTable.find()) {
            val userProfile = GSON.fromJson(document.toJson(), UserProfile.class);

            SimpleWorker worker = null;
            for (ExtendedWorkArea workArea : userProfile.pointsPerWorkArea()) {
                if (worker == null || worker.getCurrentPoints() <= workArea.getCurrentPoints()) {
                    worker = new SimpleWorker(userProfile.userId(), workArea.getCurrentPoints(), workArea);
                }
            }

            if (worker == null) continue;
            result.add(worker);
        }

        return result;
    }
}
