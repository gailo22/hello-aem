/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.gailo22.core.servlets;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;

@SuppressWarnings("serial")
@SlingServlet(paths = "/bin/hello-aem/login")
public class LoginServlet extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repository;

	public void bindRepository(SlingRepository repository) {
		this.repository = repository;
	}

	@Override
	protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {

		try {
			String id = UUID.randomUUID().toString();
			String email = req.getParameter("inputEmail");
			String password = req.getParameter("inputPassword");
			
			System.out.println("email: " + email);
			System.out.println("password: " + password);
			
			if ("admin".equalsIgnoreCase(password)) {

				JSONObject obj = new JSONObject();
				obj.put("id", id);
				obj.put("firstname", email);
				obj.put("lastname", password);
	
				// Get the JSON formatted data
				String jsonData = obj.toString(); // obj.toJSONString(); 
				
				System.out.println("jsonData: " + jsonData);
	
				// Return the JSON formatted data
				resp.getWriter().write(jsonData);
			} else {
				JSONObject obj = new JSONObject();
				obj.put("msg", "error");
				obj.put("description", "Invalid email or password!");
				resp.getWriter().write(obj.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setHeader("Content-Type", "application/json");
		try {
			JSONObject obj = new JSONObject();
			obj.put("hello", "world");
			
			resp.getWriter().write(obj.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
