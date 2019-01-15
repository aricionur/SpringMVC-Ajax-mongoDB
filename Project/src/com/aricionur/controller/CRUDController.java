package com.aricionur.controller;

import com.aricionur.model.MongoDAO;
import com.aricionur.model.Person;



import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CRUDController {
	
//public static final String APP_NAME = "CRUD %100 AJAX";
    
    MongoDAO mongoDAO = MongoDAO.getInstance();
    
    
////////////////////////////////////////////////////////////////////////////   
    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String index(ModelMap map) {
        map.put("persons", mongoDAO.getMongodbAsPersonList());
        map.put("pageHeader", "Person List");
        mongoDAO.displayMongoDB();
        return "index";
        
    }
    
////////////////////////////////////////////////////////////////////////////   
  
    @RequestMapping(value = "/addPerson" , method = RequestMethod.POST)
    @ResponseBody
    public Person addPerson(@RequestBody Person person) {
        
        Person localPerson =	mongoDAO.insert(person);
        mongoDAO.displayMongoDB();
  
        return localPerson;
    }
    
////////////////////////////////////////////////////////////////////////////
    
    @RequestMapping(value = "/updatePerson" , method = RequestMethod.PUT)
    @ResponseBody
    public String updatePerson(@RequestBody Person person) {
        
    	System.out.println("ajaxdan: controllera gelen person bilgisi : "+ person.getName()+person.getSurname()+person.getPhoneNumber());
       
        mongoDAO.update(person);
        mongoDAO.displayMongoDB();
        
        return "{\"status\":\"Success\"}";
    }
    
////////////////////////////////////////////////////////////////////////////   
    @RequestMapping(value = "/deletePerson" , method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePerson(@RequestBody Map<String, String> id) {
    	mongoDAO.delete(Integer.parseInt(id.get("id")));
    	mongoDAO.displayMongoDB();
    }

}
