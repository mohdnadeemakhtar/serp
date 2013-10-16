package de.seco.serp.controller;

public class AuthController extends BaseController {

	public void login () {
		session.setAttribute("userId", 0);
		System.out.println("logged in");
		render("");
	}
	
	public void logout () {
		session.invalidate();
	}
}
