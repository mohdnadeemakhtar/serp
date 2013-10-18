package de.seco.serp.controller;

import de.seco.serp.util.Templates;

/*
 * controller for webapp
 */
public class AjaxController extends BaseController {

	public void templates () {
		render (Templates.allTemplates(), "json");
	}
}
