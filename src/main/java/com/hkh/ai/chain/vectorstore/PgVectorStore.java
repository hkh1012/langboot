package com.hkh.ai.chain.vectorstore;

import com.hkh.ai.chain.retrieve.PromptRetrieverProperties;
import com.pgvector.PGvector;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PgVectorStore implements VectorStore{


    @Value("${chain.vector.store.pgvector.host}")
    private String pgHost;
    @Value("${chain.vector.store.pgvector.port}")
    private Integer pgPort;

    @Value("${chain.vector.store.pgvector.dimension}")
    private Integer dimension;

    @Value("${chain.vector.store.pgvector.collection}")
    private String collectionName;

    @Value("${chain.vector.store.type}")
    private String vectorType;

    private Connection connection;

    @PostConstruct
    public void init(){
        if (vectorType.equals("pg")){
            try {
                Class.forName("org.postgresql.Driver");
                // replace user and password with the configuration of your pg database
                connection = DriverManager.getConnection("jdbc:postgresql://" + pgHost + ":"+ pgPort +"/pg","postgres","pg123456");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private void createSchema(String kid) {
        Statement createStmt;
        try {
            createStmt = connection.createStatement();
            createStmt.executeUpdate("CREATE TABLE " + collectionName + kid +" (id bigserial PRIMARY KEY, content text, kid varchar(20), docId varchar(20),fid varchar(20),embedding vector(" + dimension + "))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void newSchema(String kid) {
        createSchema(kid);
    }

    @Override
    public void removeByKidAndFid(String kid, String fid) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement("delete from " + collectionName + kid +" where fid = ?");
            stmt.setString(1,fid);
            int rowsDeleted = stmt.executeUpdate();
            System.out.println("pg deleted rows: " + rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void storeEmbeddings(List<String> chunkList, List<List<Double>> vectorList, String kid, String docId, List<String> fidList) {
        try {
            for (int i = 0; i < chunkList.size(); i++) {
                PreparedStatement stmt = connection.prepareStatement("insert into " + collectionName + kid + " (content,kid,docId,fid,embedding) values (?,?,?,?,?)");
                stmt.setString(1,chunkList.get(i));
                stmt.setString(2,kid);
                stmt.setString(3,docId);
                stmt.setString(4,fidList.get(i));
                stmt.setObject(5,new PGvector(vectorList.get(i)));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



    @Override
    public void removeByDocId(String kid, String docId) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement("delete from " + collectionName + kid +" where docId = ?");
            stmt.setString(1,docId);
            int rowsDeleted = stmt.executeUpdate();
            System.out.println("pg deleted rows: " + rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removeByKid(String kid) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement("drop table " + collectionName + kid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<String> nearest(List<Double> queryVector, String kid) {
        PreparedStatement stmt;
        List<String> result = new ArrayList<>();
        try {
            stmt = connection.prepareStatement("SELECT content FROM " +  collectionName + kid + " ORDER BY embedding <=> ? LIMIT 5");
            stmt.setObject(1, new PGvector(queryVector));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("content"));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * milvus 不支持通过文本检索相似性
     * @param query
     * @param kid
     * @return
     */
    @Override
    public List<String> nearest(String query, String kid) {
        return null;
    }

}
