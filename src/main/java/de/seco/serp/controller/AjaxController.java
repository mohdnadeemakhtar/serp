package de.seco.serp.controller;

import de.seco.serp.util.Templates;

public class AjaxController extends BaseController {

	public void templates () {
		render (Templates.allTemplates());
	}
}
