package br.com.beez.dto.impl;

import br.com.beez.dto.MongoRepository;
import br.com.beez.dto.RepositoryCollection;
import br.com.beez.model.user.PolenData;
import br.com.beez.model.user.Profile;
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
@Getter
@Accessors(fluent = true)
public class EconomyRepository implements MongoRepository<PolenData> {

    @Getter private static final EconomyRepository instance = new EconomyRepository();
    private static final Gson GSON = new GsonBuilder().create();

    @RepositoryCollection(collectionName = "economy")
    private MongoCollection<Document> economyTable;

    @Override
    public PolenData find(long id) {

        val document = economyTable.find(Filters.eq("_id", id)).first();
        if (document == null) {

            val economyUser = new PolenData(id);
            insert(economyUser);

            return economyUser;

        }

        return GSON.fromJson(document.toJson(), PolenData.class);

    }

    public PolenData findOnlyExists(long id) {
        val document = economyTable.find(Filters.eq("_id", id)).first();
        if (document == null) return new PolenData(id);

        return GSON.fromJson(document.toJson(), PolenData.class);
    }

    @Override
    public void insert(PolenData data) {
        economyTable.insertOne(Document.parse(GSON.toJson(data)));
    }

    @Override
    public void replace(PolenData data) {
        economyTable.replaceOne(Filters.eq("_id", data._id()), Document.parse(GSON.toJson(data)));
    }

    @Override
    public void delete(long id) {
        economyTable.deleteOne(Filters.eq("_id", id));
    }

    @Override
    public LinkedList<PolenData> query(int maxValues) {
        val documents = Lists.newArrayList(economyTable.find().sort(new BasicDBObject("polens", -1)).limit(maxValues).iterator());
        if (documents.isEmpty()) return Lists.newLinkedList();

        LinkedList<PolenData> users = Lists.newLinkedList();
        for (Document document : documents) {
            val json = document.toJson();
            users.add(GSON.fromJson(json, PolenData.class));
        }

        return users;
    }

}