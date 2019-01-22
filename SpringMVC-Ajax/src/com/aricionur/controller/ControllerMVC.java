package com.aricionur.controller;


import com.aricionur.model.Person;
import com.aricionur.model.PersonService;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ControllerMVC {
	
    
    PersonService personService = new PersonService();
    
    
////////////////////////////////////////////////////////////////////////////   
    @RequestMapping(value = "/getPersons" , method = RequestMethod.GET)
    public String index(ModelMap map) {
        map.put("persons", personService.getMongodbAsPersonList());
        map.put("pageHeader", "Person List");
        personService.displayMongoDB();
        return "index";
        
    }
    
////////////////////////////////////////////////////////////////////////////   
  
    @RequestMapping(value = "/addPerson" , method = RequestMethod.POST)
    @ResponseBody
    public Person addPerson(@RequestBody Person person,Model model) {
        
        Person localPerson =	personService.insert(person);
        
        model.addAttribute("addedPerson", localPerson);  // used for only unit test
        
        personService.displayMongoDB();
  
        return localPerson;
    }
    
////////////////////////////////////////////////////////////////////////////
    
    @RequestMapping(value = "/updatePerson" , method = RequestMethod.PUT)
    @ResponseBody
    public String updatePerson(@RequestBody Person person) {
        
    	System.out.println("from ajax person info : "+ person.getName()+person.getSurname()+person.getPhoneNumber());
       
    	personService.update(person);
    	personService.displayMongoDB();
        
        return "{\"status\":\"Success\"}";
    }
    
////////////////////////////////////////////////////////////////////////////   
    @RequestMapping(value = "/deletePerson" , method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePerson(@RequestBody Map<String, String> id) {
    	personService.delete(Integer.parseInt(id.get("id")));
    	personService.displayMongoDB();
    }

}
