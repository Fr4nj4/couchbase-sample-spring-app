package com.autentia.example.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

Cluster cluster = CouchbaseCluster.create("localhost:8091");
cluster.authenticate("Administrator", "password");
Bucket bucket = cluster.openBucket("greeting");
 
JsonObject hello = JsonObject.create()
  .put("message", "Hello Wordddd!")
  .put("author", "foo");
 
bucket.upsert(JsonDocument.create("g:hello2", hello));
 
System.out.println(bucket.get("g:hello2"));
 
bucket.bucketManager().createN1qlPrimaryIndex(true, false);
 
N1qlQueryResult result = bucket.query(
N1qlQuery.parameterized("SELECT name FROM `greeting` WHERE author=$1",
JsonArray.from("foo")));
 
for (N1qlQueryRow row : result) {
  System.out.println(row);
}
 
bucket.close();
cluster.disconnect();
    }
}
