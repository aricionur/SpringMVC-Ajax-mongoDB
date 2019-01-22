package com.aricionur.dao;

import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public abstract class ADao {

	MongoClient mongo;
	DB db;
	DBCollection collection;
	Gson gson;
	
	public ADao() {
		System.out.println("  ** Mongo DAO constructer  **");
		try {
			this.mongo = new MongoClient("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		/**** Get database ****/
		this.db = mongo.getDB("persondb");

		/**** Get collection / table ****/

		this.collection = db.getCollection("person");

		this.collection.remove(new BasicDBObject());
		
		this.gson = new Gson();
	}

}
