package com.intuit.ui.perf.services;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.intuit.ui.perf.beans.GitValidationBean;
import com.intuit.ui.perf.utilities.JGitUtilities;

@Path("jgit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JGitResource {

	@POST
	@Path("validaterepo")
	public Response getPageResources(GitValidationBean gitbean) throws URISyntaxException {
		JGitUtilities jgit = new JGitUtilities();
		URIish uri = new URIish(gitbean.getGitRepo());
		CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("dyadav1",
				"0ddc09c6e381460826118a36ce381f7d0355092c");
		// new
		// UsernamePasswordCredentialsProvider("dyadav1","0ddc09c6e381460826118a36ce381f7d0355092c");
		GitValidationBean bean = jgit.IsValidRemoteRepository(uri, credentialsProvider, "C://");

		return Response.ok(bean).build();
	}

	@POST
	@Path("clone")
	public Response cloneRepo(GitValidationBean gitbean) {
		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		File file = new File("C:\\Users\\qbdt\\git\\"+modifiedDate);
		try {
			CloneCommand command = Git.cloneRepository();
			command.setURI(gitbean.getGitRepo());
			CloneCommand clone = command.setCredentialsProvider(new UsernamePasswordCredentialsProvider("dyadav1",
					"0ddc09c6e381460826118a36ce381f7d0355092c"));
			Git response = clone.setDirectory(file).call();
			System.out.println(response.toString());
		} catch (Exception e) {
			gitbean.setMessage(e.getMessage());
		}
		return Response.ok(gitbean).build();
	}


}
