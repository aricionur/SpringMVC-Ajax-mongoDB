package com.aricionur.model;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;


import com.mongodb.DBCursor;


public class MongoDAO {

	MongoClient mongo;
	DB db;
	DBCollection collection;
	Gson gson ;

	private static int currentId;
	private static MongoDAO uniqueInstance; 
		
	public static synchronized MongoDAO getInstance() {
		
		if(uniqueInstance == null) {
			uniqueInstance = new MongoDAO();
		}
		return uniqueInstance;
	}
	
	private MongoDAO() {
		System.out.println("***********Mongo DAO constructer********");
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

		this.currentId=1;
		BasicDBObject dbObject_initial = new BasicDBObject();
		dbObject_initial.put("id", currentId);
		dbObject_initial.put("name", "NameOne");
		dbObject_initial.put("surname", "SurnameOne");
		dbObject_initial.put("phoneNumber", "5351112233");
		this.collection.insert(dbObject_initial);
		
		BasicDBObject dbObject_initial_2 = new BasicDBObject();
		dbObject_initial_2.put("id", ++currentId);
		dbObject_initial_2.put("name", "NameTwo");
		dbObject_initial_2.put("surname", "SurnameTwo");
		dbObject_initial_2.put("phoneNumber", "5352223344");
		this.collection.insert(dbObject_initial_2);
	}

	public Person convertDBObjectToPerson(BasicDBObject dbObject)  {
		Person person = gson.fromJson(dbObject.toString(), Person.class);
		return person;
	}
	
	public Person insert(Person person) {
		// create a dbObject to store key and value
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("id", ++currentId );
		dbObject.put("name", person.getName());
		dbObject.put("surname", person.getSurname());
		dbObject.put("phoneNumber", person.getPhoneNumber());
		
		collection.insert(dbObject);
		
		return convertDBObjectToPerson(dbObject);
		
		
	}

	public void update(Person person) {
		System.out.println("\n********update method***********");
		System.out.println("person to be updated  : " + person.getId() +" "+ person.getName() +" "+ person.getSurname() +" "+ person.getPhoneNumber() );
	
		BasicDBObject searchQuery = new BasicDBObject().append("id", person.getId());
		
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("id", person.getId());
		dbObject.put("name", person.getName());
		dbObject.put("surname", person.getSurname());
		dbObject.put("phoneNumber", person.getPhoneNumber());
		
		collection.update(searchQuery, dbObject);	

		
		
		
	}
	
	public void delete(int id) {
		System.out.println("\n********delete method***********");
		System.out.println("to be deleted person id : " + id );
	
		BasicDBObject searchQuery = new BasicDBObject().append("id", id);
		

		collection.remove(searchQuery);

		
		
	}
	
	public List<BasicDBObject> getMongoDBAsBasicDBObject(){
		
		List<BasicDBObject> basicDBObjectList = new ArrayList<>();

		DBCursor cursorDoc = collection.find();
		
		while (cursorDoc.hasNext()) {
			BasicDBObject dbObject =(BasicDBObject) cursorDoc.next();
			basicDBObjectList.add(dbObject); 
		}
		
		return basicDBObjectList;
		
	}
	
	public List<Person> getMongodbAsPersonList() {
		List<Person> PersonList = new ArrayList<>();
		List<BasicDBObject> dbObjectList = getMongoDBAsBasicDBObject();

		for (BasicDBObject eachBasicDBObject : dbObjectList) {
			Person person = gson.fromJson(eachBasicDBObject.toString(), Person.class);
			PersonList.add(person);
		}
	
		return PersonList;
	}
	
	public void displayMongoDB() {
		List<Person> personList = getMongodbAsPersonList();
		
		System.out.println("\n   ******  writing person list from current mongoDB  ********");
		for (Person eachPerson : personList) {
			System.out.println("Person info : " + eachPerson.getId() + " " +eachPerson.getName() + " " + eachPerson.getSurname() +" " + eachPerson.getPhoneNumber());
		}	
	}
	
	

}
