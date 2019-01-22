package test.com.aricionur.controller;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import com.aricionur.controller.ControllerMVC;
import com.aricionur.config.ViewConfig;
import com.aricionur.model.Person;
import com.aricionur.model.PersonService;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {
/////////////////

		@Mock
		PersonService personServiceMock;

		@InjectMocks
		ControllerMVC controllerMVC;
		
		MockMvc mockMvc;
		
		ViewConfig viewConfig = new ViewConfig();
		InternalResourceViewResolver viewResolver = (InternalResourceViewResolver) viewConfig.viewResolver();
		
		@Before
		public void setup() {
			MockitoAnnotations.initMocks(this);
			
			mockMvc = MockMvcBuilders.standaloneSetup(controllerMVC).setViewResolvers(viewResolver).build();
			
		}
		
		@Test
		public void testGet() throws Exception {
			List<Person> personList = new ArrayList<>();
			personList.add(new Person(55,"nameOfUnitTest", "surnameOfUnitTest","phoneNumberOfUnitTest"));

			when(personServiceMock.getMongodbAsPersonList()).thenReturn((List<Person>) personList);

			mockMvc.perform(get("/getPersons"))
					.andExpect(status().isOk())
					.andExpect(view().name("index"))
					.andExpect(model().attribute("persons",hasSize(1)))
					.andExpect(model().attribute("persons",hasItem(
							allOf(
									hasProperty("id",is(55)),
									hasProperty("name",is("nameOfUnitTest")),
									hasProperty("surname",is("surnameOfUnitTest")),
									hasProperty("phoneNumber",is("phoneNumberOfUnitTest"))))));
		}
		
		@Test
		public void testAdd() throws Exception{
			Person addedPerson = new Person(88, "nameOfAddedFromThenReturn", "surnameOfAddedFromThenReturn", "phoneNumberOfAddedFromThenReturn");
		
			when(personServiceMock.insert(isA(Person.class))).thenReturn(addedPerson);
			
			MvcResult result = mockMvc.perform(post("/addPerson")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"name\":\"nameOfAdded\", \"surname\":\"surnameOfAdded\", \"phoneNumber\":\"phoneNumberOfAdded\"}")
					)
					.andExpect(model().attributeExists("addedPerson"))
					.andReturn();
			 
			 
			Person returnedPerson = (Person) result.getModelAndView().getModel().get("addedPerson");
			
			assertEquals("88", returnedPerson.getId());
			assertEquals("nameOfAddedFromThenReturn", returnedPerson.getName());
			assertEquals("surnameOfAddedFromThenReturn", returnedPerson.getSurname());
			assertEquals("phoneNumberOfAddedFromThenReturn", returnedPerson.getPhoneNumber());
			
//			assertThat(returnedCustomer.getId(), is("88"));
//			assertThat(returnedCustomer.getName(), is("nameOfAddedFromThenReturn"));
//			assertThat(returnedCustomer.getSurname(), is("surnameOfAddedFromThenReturn"));

			
//                    ** Another Alternative Method **
//		    ArgumentCaptor<Person> formObjectArgument = ArgumentCaptor.forClass(Person.class);
//	        verify(personServiceMock, times(1)).insert(formObjectArgument.capture());
//	        verifyNoMoreInteractions(personServiceMock);
//	 
//	        Person formObject = formObjectArgument.getValue();
//	 
//	        assertThat(formObject.getId(), is("88"));
//	        assertThat(formObject.getName(), is("nameOfAddedFromThenReturn"));
//	        assertThat(formObject.getSurname(), is("surnameOfAddedFromThenReturn"));
//		    assertThat(formObject.getPhoneNumber(), is("phoneNumberOfAddedFromThenReturn"));

			
		} 
		
	/////////////////////
}
