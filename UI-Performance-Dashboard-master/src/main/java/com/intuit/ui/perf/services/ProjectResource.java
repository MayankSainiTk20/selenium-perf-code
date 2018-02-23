package com.intuit.ui.perf.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.intuit.ui.perf.entities.Entity_Project;
import com.intuit.ui.perf.entities.PerformanceProjectRepository;
import com.intuit.ui.perf.utilities.JGitUtilities;


@Path("project")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectResource {

	@Autowired
	private PerformanceProjectRepository projectrepo;
	
	@POST
	@Path("add")
	public Response getPageResources(Entity_Project project){
		System.out.println("Inside add project..........");
		try{
		JGitUtilities jgitUtil=new JGitUtilities();	
		jgitUtil.cloneGITRepository(project);
		projectrepo.save(project);
		}catch(Exception e){
			e.printStackTrace();
		}
		return Response.ok(project).build();
	}
	
	
	
}
