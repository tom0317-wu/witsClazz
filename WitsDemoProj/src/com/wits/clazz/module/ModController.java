package com.wits.clazz.module;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ModController {
	
	@RequestMapping(value = "/M1", method = RequestMethod.GET)
	public String helloWorld(Model model) {
		model.addAttribute("Hello", "AAA123");
		
		return "HelloWorld";
	}
	
	@RequestMapping(value = "/M2", method = RequestMethod.GET)
	public String helloWits(Model model) {
		model.addAttribute("Hello", "WistronITS");
		
		return "HelloWits";
	}

}
