package ru.filippov.project;

/**
 * @author Aleksey Filippov
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping("/hello")
	public String printHello() {
		return "print_hello";
	}

}
