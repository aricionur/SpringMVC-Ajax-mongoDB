package com.aricionur.model;

import java.util.ArrayList;
import java.util.List;

import com.aricionur.dao.DAO;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;

public class PersonService {

	DAO dao;
	Gson gson;

	private static int currentId;

	public PersonService() {
		System.out.println("\n  **PersonService constructer  **");

		this.dao = new DAO();
		this.gson = new Gson();

		this.currentId = 1;

		// make some initial insert to MongoDB
		BasicDBObject dbObject_initial = new BasicDBObject();
		dbObject_initial.put("id", currentId);
		dbObject_initial.put("name", "NameOne");
		dbObject_initial.put("surname", "SurnameOne");
		dbObject_initial.put("phoneNumber", "5351112233");
		dao.insert(dbObject_initial);

		BasicDBObject dbObject_initial_2 = new BasicDBObject();
		dbObject_initial_2.put("id", ++currentId);
		dbObject_initial_2.put("name", "NameTwo");
		dbObject_initial_2.put("surname", "SurnameTwo");
		dbObject_initial_2.put("phoneNumber", "5352223344");
		dao.insert(dbObject_initial_2);

	}

	public Person convertDBObjectToPerson(BasicDBObject dbObject) {
		Person person = gson.fromJson(dbObject.toString(), Person.class);
		return person;
	}

	public Person insert(Person person) {
		System.out.println("\n******** PersonService insert method***********");

		// create a dbObject to store key and value
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("id", ++currentId);
		dbObject.put("name", person.getName());
		dbObject.put("surname", person.getSurname());
		dbObject.put("phoneNumber", person.getPhoneNumber());

		dao.insert(dbObject);

		return convertDBObjectToPerson(dbObject);

	}

	public void update(Person person) {
		System.out.println("\n******** PersonService update method***********");
		System.out.println("\n Person to be updated  : " + person.getId() + " " + person.getName() + " "
				+ person.getSurname() + " " + person.getPhoneNumber());

		BasicDBObject searchQuery = new BasicDBObject().append("id", person.getId());

		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("id", person.getId());
		dbObject.put("name", person.getName());
		dbObject.put("surname", person.getSurname());
		dbObject.put("phoneNumber", person.getPhoneNumber());

		dao.update(searchQuery, dbObject);

	}

	public void delete(int id) {
		System.out.println("\n********Person Service delete method***********");
		System.out.println("to be deleted person id : " + id);

		BasicDBObject searchQuery = new BasicDBObject().append("id", id);

		dao.delete(searchQuery);

	}

	public List<Person> getMongodbAsPersonList() {

		return dao.getMongodbAsPersonList();
	}

	public void displayMongoDB() {
		List<Person> personList = dao.getMongodbAsPersonList();

		System.out.println("\n   ******  writing person list from current mongoDB  ********");
		for (Person eachPerson : personList) {
			System.out.println("Person info : " + eachPerson.getId() + " " + eachPerson.getName() + " "
					+ eachPerson.getSurname() + " " + eachPerson.getPhoneNumber());
		}
	}

}
