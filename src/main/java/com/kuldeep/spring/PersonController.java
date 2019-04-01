package com.kuldeep.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kuldeep.spring.model.Person;
import com.kuldeep.spring.service.PersonService;
import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.telemetry.Duration;

@Controller
public class PersonController {
	
	private PersonService personService;
	
	TelemetryClient telemetryClient;
	
	// TelemetryConfiguration telemetryConfig;
	
	@Autowired(required=true)
	@Qualifier(value="personService")
	public void setPersonService(PersonService ps){
		this.personService = ps;
	}
	
	@Autowired(required=true)
	@Qualifier(value="telemetryClient")
	public void setTelemetryClient(TelemetryClient tc){
		this.telemetryClient = tc;
	}
	
	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public String listPersons(Model model) {
		model.addAttribute("person", new Person());
		model.addAttribute("listPersons", this.personService.listPersons());
		return "person";
	}
	
	//For add and update person both
	@RequestMapping(value= "/person/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") Person p){
		
		if(p.getId() == 0){
			//new person, add it
			this.personService.addPerson(p);
		}else{
			//existing person, call update
			this.personService.updatePerson(p);
		}
		
		return "redirect:/persons";
		
	}
	
	@RequestMapping("/remove/{id}")
    public String removePerson(@PathVariable("id") int id){
		
        this.personService.removePerson(id);
        return "redirect:/persons";
    }
 
    @RequestMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", this.personService.getPersonById(id));
        model.addAttribute("listPersons", this.personService.listPersons());
        return "person";
    }
    
    @RequestMapping("/hello")
    public String hello() {

        //track a custom event  
        this.telemetryClient.trackEvent("Sending a custom event...");

        //trace a custom trace
        this.telemetryClient.trackTrace("Sending a custom trace....");

        //track a custom metric
        this.telemetryClient.trackMetric("custom metric", 1.0);

        //track a custom dependency
        this.telemetryClient.trackDependency("SQL", "Insert", new Duration(0, 0, 1, 1, 1), true);
        
       // telemetryConfig.getActive().setInstrumentationKey(key);
        
        return "hello";
    }
	
}