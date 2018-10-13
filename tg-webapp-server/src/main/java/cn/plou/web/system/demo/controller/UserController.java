package cn.plou.web.system.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Controller
public class UserController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@ResponseBody
	@GetMapping("/test/hello")
	public String hello() {
		return "hello";
	}


}
