/**
 * 
 */
package com.learn.velocitytemplate.controller;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learn.velocity.bean.DashBoardBean;

/**
 * @author Prabu S B
 *
 */
@Controller
public class VelocityController {
	
	
	@Autowired
	private VelocityEngine engine;
	
	@Autowired
	private MessageSource messageSource;
	
	
	/**
	 * TODO not working need to check
	 * Old way to return the template output as string, it works but not rendering the output to HTML
	 * @param model
	 * @return
	 */
	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE, value="/dasboard")
	public String getDashboardByOldWay(Model model) {		
		

		VelocityEngine velocity = new VelocityEngine();
        velocity.init();
        Template template = velocity.getTemplate("src/main/resources/templates/dashboard.vm");

        VelocityContext context = new VelocityContext();
        
        context.put("title", "Apache Velocity");

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();      
		
		//return "dashboard";
	}
	
	
	/**
	 * New way of rendering the template as HTMl with spring features
	 * @param model
	 * @return
	 */
	@RequestMapping("/dasboardDetails/{language}/{flightNumber}")
	public String getDashboardByNewSpringBoot(Model model, @PathVariable String language, @PathVariable String flightNumber) {		
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.setFlightNumber(flightNumber);
		dashBoardBean.setOrigin("Amsterdam");
		dashBoardBean.setDestination("New York");
		model.addAttribute("viewBean", dashBoardBean); 
		model.addAttribute("labels", getLabels(language));
		return "dashboard";
	}
	
	
	/**
	 * Get the labels from message source
	 * @param language
	 * @return
	 */
	private Map<String, String> getLabels(String language)	{
		Map<String, String> labels = new HashMap<>();
		Locale locale = new Locale(language);
		labels.put("title", messageSource.getMessage("title", null, locale));
		labels.put("header", messageSource.getMessage("header", null, locale));
		labels.put("titledetails", messageSource.getMessage("titledetails", null, locale));
		labels.put("signup", messageSource.getMessage("signup", null, locale));
		labels.put("flightNumber", messageSource.getMessage("flightNumber", null, locale));
		labels.put("origin", messageSource.getMessage("origin", null, locale));
		labels.put("destination", messageSource.getMessage("destination", null, locale));
		
		return labels;
	}



}
