package br.com.beez.dto.impl;

import br.com.beez.dto.MongoRepository;
import br.com.beez.dto.RepositoryCollection;
import br.com.beez.model.user.Worker;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
@Accessors(fluent = true)
public class WorkerRepository implements MongoRepository<Worker> {

    @Getter private static final WorkerRepository instance = new WorkerRepository();
    private static final Gson GSON = new GsonBuilder().create();

    @RepositoryCollection(collectionName = "workers")
    private MongoCollection<Document> workerTable;

    @Override
    public Worker find(long id) {
        val document = workerTable.find(Filters.eq("_id", id)).first();
        if (document == null) {
            Worker worker = new Worker(id, new ArrayList<>());
            insert(worker);

            return worker;
        }

        return GSON.fromJson(document.toJson(), Worker.class);
    }

    public Worker findOnlyExists(long id) {
        val document = workerTable.find(Filters.eq("_id", id)).first();
        if (document == null) return new Worker(id, new ArrayList<>());

        return GSON.fromJson(document.toJson(), Worker.class);
    }

    @Override
    public void insert(Worker data) {
        workerTable.insertOne(Document.parse(GSON.toJson(data)));
    }

    @Override
    public void replace(Worker data) {
        workerTable.replaceOne(Filters.eq("_id", data._id()), Document.parse(GSON.toJson(data)));
    }

    @Override
    public void delete(long id) {
        workerTable.deleteOne(Filters.eq("_id", id));
    }

    @Override
    public LinkedList<Worker> query(int maxValues) {
        LinkedList<Worker> result = Lists.newLinkedList();
        for (val document : workerTable.find()) {
            val worker = GSON.fromJson(document.toJson(), Worker.class);
            result.add(worker);
        }

        result.sort((w1, w2) -> {
            ExtendedWorkArea w1Area = null;
            for (ExtendedWorkArea workArea : w1.workAreas()) {
                if (w1Area == null || w1Area.getCurrentPoints() < workArea.getCurrentPoints()) {
                    w1Area = workArea;
                }
            }

            ExtendedWorkArea w2Area = null;
            for (ExtendedWorkArea workArea : w2.workAreas()) {
                if (w2Area == null || w2Area.getCurrentPoints() < workArea.getCurrentPoints()) {
                    w2Area = workArea;
                }
            }

            if (w1Area == null && w2Area == null) return 0;
            else if (w2Area == null) return -1;
            else if (w1Area == null) return 1;
            else return Integer.compare(w1Area.getCurrentPoints(), w2Area.getCurrentPoints());
        });

        return result;
    }
}
