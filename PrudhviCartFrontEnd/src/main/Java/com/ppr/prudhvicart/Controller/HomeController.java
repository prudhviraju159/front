package com.niit.shoppingcart.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingcart.dao.CartDAO;
import com.niit.shoppingcart.dao.CategoryDAO;

import com.niit.shoppingcart.dao.UserDetailsDAO;
import com.niit.shoppingcart.model.Cart;
import com.niit.shoppingcart.model.Category;
import com.niit.shoppingcart.model.UserDetails;

@Controller
public class HomeController {
	
	Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	UserDetails userDetails;

	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private UserDetailsDAO userDetailsDAO;

	@Autowired
	private Category category;

	//     ${categoryList}
	@RequestMapping("/")
	public ModelAndView onLoad(HttpSession session) {
		log.debug("Starting of the method onLoad");
		ModelAndView mv = new ModelAndView("/home");
		session.setAttribute("category", category);
		session.setAttribute("categoryList", categoryDAO.list());
		log.debug("Ending of the method onLoad");
		return mv;
	}
	
  // ${successMessage}
	@RequestMapping(value = "user/register", method = RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute UserDetails userDetails) {
		userDetailsDAO.saveOrUpdate(userDetails);
		ModelAndView mv  = new ModelAndView("/home");
		mv.addObject("successMessage", "You are successfully register");
		
		return mv;
	}

	@RequestMapping("/registerHere")
	public ModelAndView registerHere() {
		ModelAndView mv = new ModelAndView("/home");
		mv.addObject("userDetails", userDetails);
		mv.addObject("isUserClickedRegisterHere", "true");
		return mv;
	}

	@RequestMapping("/loginHere")
	public ModelAndView loginHere() {
		ModelAndView mv = new ModelAndView("/home");
		mv.addObject("userDetails", new UserDetails());
		mv.addObject("isUserClickedLoginHere", "true");
		return mv;
	}



}

