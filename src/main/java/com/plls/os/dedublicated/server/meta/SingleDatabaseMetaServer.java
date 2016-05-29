package com.plls.os.dedublicated.server.meta;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.holders.ChangeNotifyingSynchronizedIntHolder;
import com.plls.os.dedublicated.server.data.Chunk;
import com.plls.os.dedublicated.server.data.OSDChunkedObject;

import java.sql.*;

public class SingleDatabaseMetaServer implements MetaServer {

    private ComboPooledDataSource connectionPool;

    public SingleDatabaseMetaServer() throws Exception {
        Class.forName("org.postgresql.Driver");
        connectionPool = new ComboPooledDataSource();
        connectionPool.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
        connectionPool.setJdbcUrl("jdbc:postgresql://localhost:5432/osd_over_postgres");
        connectionPool.setUser("postgres");
        connectionPool.setPassword("123");
        // the settings below are optional -- c3p0 can work with defaults
        connectionPool.setMinPoolSize(5);
        connectionPool.setAcquireIncrement(5);
        connectionPool.setMaxPoolSize(20);
    }

    @Override
    public void fillFileMetaById(OSDChunkedObject obj) {

    }

    @Override
    public void addNewFile(OSDChunkedObject obj) throws Exception {
        String representativeHash = obj.getRepresentativeHash();
        if ( representativeHash == null){
            throw new Exception();
        }
        Connection c = connectionPool.getConnection();
        String sql_create_file = "select * from add_file(?, ?);"; // hash, name
        PreparedStatement stmt = c.prepareStatement(sql_create_file);
        stmt.setString(1,obj.getRepresentativeHash());
        stmt.setString(2,obj.name);
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {//duplicate name
                throw e;
            }
        }
        long combined_object_id = 0;
        long object_id = 0;
        if ( !rs.next()) {
            throw new Exception();
        }

        combined_object_id = rs.getLong("combined_object_id");
        object_id = rs.getLong("object_id");
        rs.close();
        obj.oject_id = object_id;
        obj.combined_object_id = combined_object_id;

        stmt.close();


        String sql = "select * from add_chunk_to_object(?, ?,?, ?);";// repr_hash, size, start, obj id
        stmt = c.prepareStatement(sql);
        // TODO: 29.05.16  : do it just in 1 query
        for(long start : obj.chunkDescriptions.keySet()){
            Chunk chunk = obj.chunkDescriptions.get(start);
            stmt.setString(1,chunk.hash);
            stmt.setInt(2, (int) chunk.size);
            stmt.setInt(3, (int) start);
            stmt.setInt(4, (int) object_id);
            rs = stmt.executeQuery();
            if (!rs.next()){
                throw new Exception();
            }
            chunk.id = rs.getLong(1);
            rs.close();
        }
        stmt.close();
        c.close();
//        String sql = "select * from add_chunk_to_object(?, ?,?, ?);";// repr_hash, size, start, obj id
//        stmt = c.prepareStatement(sql);
//        c.setAutoCommit(false);
//        for(long start : obj.chunkDescriptions.keySet()){
//            Chunk chunk = obj.chunkDescriptions.get(start);
//            stmt.setString(1,chunk.hash);
//            stmt.setLong(2,chunk.size);
//            stmt.setLong(3,start);
//            stmt.setLong(4,object_id);
//            stmt.addBatch();
//        }
//        stmt.executeBatch();
//        c.commit();
//        ResultSet generatedKeys = stmt.getGeneratedKeys();
//        while (rs.next()) {
//            System.out.print(rs.getInt(1));
//        }
//        stmt.close();
//        c.close();
    }

    @Override
    public void deleteFile(OSDChunkedObject obj) {

    }
}

/*

Create table if not exists object(
	id serial,
	name varchar(255) NOT NULL,
	combined_object_id integer,
	primary key(id)
);

Create table if not exists combined_object(
	id serial,
	ref_cnt integer default 0,
	representative_hash varchar(60) NOT NULL,
	primary key(id)
);

Create table if not exists chunk(
	id serial,
	combined_object_id integer,
	size integer,
	hash varchar(60) NOT NULL,
	primary key(id)
);

Create table if not exists object_to_chunk(
	object_id integer,
	chunk_id integer,
	start integer
);

alter table object add constraint object_unique_name unique ( name);
alter table combined_object add constraint combined_object_unique_hash unique ( representative_hash);


alter table object_to_chunk add constraint objectchunk_unique unique ( object_id, chunk_id, start);
alter table chunk add constraint chunk_unique_hash unique ( hash);



DROP FUNCTION add_file(character varying,character varying);

CREATE OR REPLACE FUNCTION add_file(attr_representation_hash varchar(60), attr_name varchar(255))
  RETURNS TABLE (combined_object_id integer, object_id integer ) AS $BODY$
DECLARE
 var_combined_object RECORD;
 var_object RECORD;
BEGIN
 SELECT INTO var_combined_object *
 FROM combined_object co
 WHERE co.representative_hash = attr_representation_hash for update;
 IF NOT found
 THEN
  insert into combined_object(representative_hash) values(attr_representation_hash) returning id into var_combined_object as id;
 END IF;
 insert into object(name, combined_object_id) values(attr_name, var_combined_object.id) returning id into var_object as id;
 return query select var_combined_object.id, var_object.id;
END;
$BODY$
  LANGUAGE plpgsql;

select * from add_file('ale ddra', 'vlsxecrda');



CREATE OR REPLACE FUNCTION add_chunk_to_object(attr_hash varchar(60), attr_size integer, attr_start integer, attr_object_id integer)
  RETURNS integer AS $BODY$
DECLARE
 var_chunk RECORD;
 var_object RECORD;
BEGIN
 SELECT INTO var_chunk *
 FROM chunk c
 WHERE c.hash = attr_hash for update;
 IF NOT found
 THEN
  insert into chunk(hash, size) values(attr_hash, attr_size) returning id into var_chunk as id;
 END IF;
 insert into object_to_chunk(object_id, chunk_id, start) values(attr_object_id, var_chunk.id, attr_start);
 return var_chunk.id;
END;
$BODY$
  LANGUAGE plpgsql;


 */