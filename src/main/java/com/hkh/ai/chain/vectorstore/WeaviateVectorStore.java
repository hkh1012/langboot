package com.hkh.ai.chain.vectorstore;

import cn.hutool.core.lang.UUID;
import com.google.gson.internal.LinkedTreeMap;
import io.weaviate.client.Config;
import io.weaviate.client.WeaviateClient;
import io.weaviate.client.base.Result;
import io.weaviate.client.v1.data.model.WeaviateObject;
import io.weaviate.client.v1.data.replication.model.ConsistencyLevel;
import io.weaviate.client.v1.filters.Operator;
import io.weaviate.client.v1.filters.WhereFilter;
import io.weaviate.client.v1.graphql.model.GraphQLResponse;
import io.weaviate.client.v1.graphql.query.argument.NearObjectArgument;
import io.weaviate.client.v1.graphql.query.argument.NearTextArgument;
import io.weaviate.client.v1.graphql.query.argument.NearVectorArgument;
import io.weaviate.client.v1.graphql.query.fields.Field;
import io.weaviate.client.v1.misc.model.*;
import io.weaviate.client.v1.schema.model.DataType;
import io.weaviate.client.v1.schema.model.Property;
import io.weaviate.client.v1.schema.model.Schema;
import io.weaviate.client.v1.schema.model.WeaviateClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeaviateVectorStore implements VectorStore{

    @Value("${chain.vector.store.weaviate.protocol}")
    private String protocol;
    @Value("${chain.vector.store.weaviate.host}")
    private String host;

    @Value("${chain.vector.store.weaviate.classname}")
    private String className;

    public WeaviateClient getClient(){
        Config config = new Config(protocol, host);
        WeaviateClient client = new WeaviateClient(config);
        return client;
    }

    public Result<Meta> getMeta(){
        WeaviateClient client = getClient();
        Result<Meta> meta = client.misc().metaGetter().run();
        if (meta.getError() == null) {
            System.out.printf("meta.hostname: %s\n", meta.getResult().getHostname());
            System.out.printf("meta.version: %s\n", meta.getResult().getVersion());
            System.out.printf("meta.modules: %s\n", meta.getResult().getModules());
        } else {
            System.out.printf("Error: %s\n", meta.getError().getMessages());
        }
        return meta;
    }

    public Result<Schema> getSchemas(){
        WeaviateClient client = getClient();
        Result<Schema> result = client.schema().getter().run();
        if (result.hasErrors()) {
            System.out.println(result.getError());
        }else {
            System.out.println(result.getResult());
        }
        return result;
    }


    public Result<Boolean> createSchema(String kid){
        WeaviateClient client = getClient();

        VectorIndexConfig vectorIndexConfig = VectorIndexConfig.builder()
            .distance("cosine")
            .cleanupIntervalSeconds(300)
            .efConstruction(128)
            .maxConnections(64)
            .vectorCacheMaxObjects(500000L)
            .ef(-1)
            .skip(false)
            .dynamicEfFactor(8)
            .dynamicEfMax(500)
            .dynamicEfMin(100)
            .flatSearchCutoff(40000)
            .build();

        ShardingConfig shardingConfig = ShardingConfig.builder()
            .desiredCount(3)
            .desiredVirtualCount(128)
            .function("murmur3")
            .key("_id")
            .strategy("hash")
            .virtualPerPhysical(128)
            .build();

        ReplicationConfig replicationConfig = ReplicationConfig.builder()
            .factor(1)
            .build();


        WeaviateClass clazz = WeaviateClass.builder()
            .className(className + kid)
            .description("local knowledge")
            .vectorIndexType("hnsw")
            .vectorizer("text2vec-transformers")
            .shardingConfig(shardingConfig)
            .vectorIndexConfig(vectorIndexConfig)
            .replicationConfig(replicationConfig)
            .properties(new ArrayList() {{
                add(Property.builder()
                        .dataType(new ArrayList(){ { add(DataType.TEXT); } })
                        .name("content")
                        .description("The content of the local knowledge,for search")
                        .build());
                add(Property.builder()
                        .dataType(new ArrayList(){ { add(DataType.TEXT); } })
                        .name("kid")
                        .description("The knowledge id of the local knowledge,for search")
                        .build());
                add(Property.builder()
                        .dataType(new ArrayList(){ { add(DataType.TEXT); } })
                        .name("docId")
                        .description("The doc id of the local knowledge,for search")
                        .build());
            } })
            .build();

        Result<Boolean> result = client.schema().classCreator().withClass(clazz).run();
        if (result.hasErrors()) {
            System.out.println(result.getError());
        }
        System.out.println(result.getResult());
            return result;
    }

    @Override
    public void storeEmbeddings(List<String> chunkList, List<List<Double>> vectorList,String kid, String docId,Boolean firstTime) {
        if (firstTime) {
            createSchema(kid);
        }
        WeaviateClient client = getClient();
        for (int i = 0; i < chunkList.size(); i++) {
            if (vectorList != null) {
                List<Double> vector = vectorList.get(i);
                Float[] vf = new Float[vector.size()];
                for (int j = 0; j < vector.size(); j++) {
                    Double value = vector.get(j);
                    vf[j] = value.floatValue();
                }
                Map<String, Object> dataSchema = new HashMap<>();
                dataSchema.put("content", chunkList.get(i));
                dataSchema.put("kid", kid);
                dataSchema.put("docId", docId);
                Result<WeaviateObject> result = client.data().creator()
                        .withClassName(className + kid)
                        .withID(UUID.randomUUID(true).toString())
                        .withVector(vf)
                        .withProperties(dataSchema)
                        .run();
            }else {
                Map<String, Object> dataSchema = new HashMap<>();
                dataSchema.put("content", chunkList.get(i));
                dataSchema.put("kid", kid);
                dataSchema.put("docId", docId);
                Result<WeaviateObject> result = client.data().creator()
                        .withClassName(className + kid)
                        .withID(UUID.randomUUID(true).toString())
                        .withProperties(dataSchema)
                        .run();
            }

        }
    }

    @Override
    public void removeByDocId(String kid,String docId) {
        List<String> resultList = new ArrayList<>();
        WeaviateClient client = getClient();
        Field fieldId = Field.builder().name("id").build();
        WhereFilter where = WhereFilter.builder()
            .path(new String[]{ "docId" })
            .operator(Operator.Equal)
            .valueString(docId)
//            .operator(Operator.And)
//            .path(new String[]{ "kid" })
//            .operator(Operator.Equal)
//            .valueString(kid)
            .build();
        Result<GraphQLResponse> result = client.graphQL().get()
            .withClassName(className + kid)
            .withFields(fieldId)
            .withWhere(where)
            .run();
        LinkedTreeMap<String,Object> t = (LinkedTreeMap<String, Object>) result.getResult().getData();
        LinkedTreeMap<String,ArrayList<LinkedTreeMap>> l = (LinkedTreeMap<String, ArrayList<LinkedTreeMap>>) t.get("Get");
        ArrayList<LinkedTreeMap> m = l.get(className + kid);
        for (LinkedTreeMap linkedTreeMap : m){
            String uuid = linkedTreeMap.get("id").toString();
            resultList.add(uuid);
        }
        for (String uuid : resultList) {
            Result<Boolean> deleteResult = client.data().deleter()
                .withID(uuid)
                .withClassName(className)
                .withConsistencyLevel(ConsistencyLevel.ONE)  // default QUORUM
                .run();
        }
    }

    @Override
    public void removeByKid(String kid) {
        List<String> resultList = new ArrayList<>();
        WeaviateClient client = getClient();
        Field fieldId = Field.builder().name("id").build();
        WhereFilter where = WhereFilter.builder()
                .path(new String[]{ "kid" })
                .operator(Operator.Equal)
                .valueString(kid)
                .build();
        Result<GraphQLResponse> result = client.graphQL().get()
                .withClassName(className + kid)
                .withFields(fieldId)
                .withWhere(where)
                .run();
        LinkedTreeMap<String,Object> t = (LinkedTreeMap<String, Object>) result.getResult().getData();
        LinkedTreeMap<String,ArrayList<LinkedTreeMap>> l = (LinkedTreeMap<String, ArrayList<LinkedTreeMap>>) t.get("Get");
        ArrayList<LinkedTreeMap> m = l.get(className + kid);
        for (LinkedTreeMap linkedTreeMap : m){
            String uuid = linkedTreeMap.get("id").toString();
            resultList.add(uuid);
        }
        for (String uuid : resultList) {
            Result<Boolean> deleteResult = client.data().deleter()
                    .withID(uuid)
                    .withClassName(className + kid)
                    .withConsistencyLevel(ConsistencyLevel.ONE)  // default QUORUM
                    .run();
        }
    }

    @Override
    public List<String> nearest(List<Double> queryVector,String kid) {
        if (StringUtils.isBlank(kid)){
            return new ArrayList<String>();
        }
        List<String> resultList = new ArrayList<>();
        Float[] vf = new Float[queryVector.size()];
        for (int j = 0; j < queryVector.size(); j++) {
            Double value = queryVector.get(j);
            vf[j] = value.floatValue();
        }
        WeaviateClient client = getClient();
        Field title = Field.builder().name("content").build();
        NearVectorArgument nearVector = NearVectorArgument.builder()
                .vector(vf)
//                .distance(0.1F)
                .build();
        Result<GraphQLResponse> result = client.graphQL().get()
                .withClassName(className + kid)
                .withFields(title)
                .withNearVector(nearVector)
                .withLimit(3)
                .run();
        LinkedTreeMap<String,Object> t = (LinkedTreeMap<String, Object>) result.getResult().getData();
        LinkedTreeMap<String,ArrayList<LinkedTreeMap>> l = (LinkedTreeMap<String, ArrayList<LinkedTreeMap>>) t.get("Get");
        ArrayList<LinkedTreeMap> m = l.get(className + kid);
        for (LinkedTreeMap linkedTreeMap : m){
            String content = linkedTreeMap.get("content").toString();
            resultList.add(content);
        }
        return resultList;
    }

    @Override
    public List<String> nearest(String query,String kid) {
        if (StringUtils.isBlank(kid)){
            return new ArrayList<String>();
        }
        List<String> resultList = new ArrayList<>();
        WeaviateClient client = getClient();
        Field title = Field.builder().name("content").build();
        NearTextArgument nearText = client.graphQL().arguments().nearTextArgBuilder()
                .concepts(new String[]{ query })
                .distance(0.6f) // use .certainty(0.7f) prior to v1.14
                .build();

        Result<GraphQLResponse> result = client.graphQL().get()
                .withClassName(className + kid)
                .withFields(title)
                .withNearText(nearText)
                .withLimit(3)
                .run();
        LinkedTreeMap<String,Object> t = (LinkedTreeMap<String, Object>) result.getResult().getData();
        LinkedTreeMap<String,ArrayList<LinkedTreeMap>> l = (LinkedTreeMap<String, ArrayList<LinkedTreeMap>>) t.get("Get");
        ArrayList<LinkedTreeMap> m = l.get(className + kid);
        for (LinkedTreeMap linkedTreeMap : m){
            String content = linkedTreeMap.get("content").toString();
            resultList.add(content);
        }
        return resultList;
    }

    public Result<Boolean> deleteSchema(String kid) {
        WeaviateClient client = getClient();
        Result<Boolean> result = client.schema().classDeleter().withClassName(className+ kid).run();
        if (result.hasErrors()) {
            System.out.println(result.getError());
        }else {
            System.out.println(result.getResult());
        }
        return result;
    }
}
