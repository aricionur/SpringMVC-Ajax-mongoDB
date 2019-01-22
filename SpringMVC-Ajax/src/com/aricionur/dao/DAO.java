package com.aricionur.dao;

import java.util.ArrayList;
import java.util.List;

import com.aricionur.model.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

public class DAO extends ADao {

	public void insert(BasicDBObject dbObject) {

		System.out.println("\n  ** dao delete method  **");
		collection.insert(dbObject);

	}

	public void update(BasicDBObject searchQuery, BasicDBObject dbObject) {
		System.out.println("\n ** dao update method **");

		collection.update(searchQuery, dbObject);

	}

	public void delete(BasicDBObject searchQuery) {
		System.out.println("\n**  dao delete method  **");

		collection.remove(searchQuery);

	}

	public List<BasicDBObject> getMongoDBAsBasicDBObject() {

		List<BasicDBObject> basicDBObjectList = new ArrayList<>();

		DBCursor cursorDoc = collection.find();

		while (cursorDoc.hasNext()) {
			BasicDBObject dbObject = (BasicDBObject) cursorDoc.next();
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
			System.out.println("Person info : " + eachPerson.getId() + " " + eachPerson.getName() + " "
					+ eachPerson.getSurname() + " " + eachPerson.getPhoneNumber());
		}
	}

}
