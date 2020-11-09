package com.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pojo.User;
import com.service.UserService;


@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login")
	public String login() {
		return "/login";
	}
	
	@RequestMapping(value = "/")
	public String index(Model model) {
		List<User> users = userService.userList();
		model.addAttribute("users", users);
		return "/index";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String toSave() {
		return "/add";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(User user) {
		userService.save(user);
		return "redirect:/";

	}
	
	@RequestMapping(value = "/detail/{id}")
	public String detail(@PathVariable(value = "id") int id, ModelMap map) {
		User user = userService.get(id);
		map.put("user", user);
		return "/detail";
	}
	
	@RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
	public String update(@PathVariable(value = "id") int id, ModelMap map) {
		User user = userService.get(id);
		map.put("user", user);
		return "/update";
	}

	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") int id, User user) {
		userService.update(id, user);
        return "redirect:/";
	}
	
	@RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") int id) {
		userService.delete(id);
        return "redirect:/";
	}
	
}
