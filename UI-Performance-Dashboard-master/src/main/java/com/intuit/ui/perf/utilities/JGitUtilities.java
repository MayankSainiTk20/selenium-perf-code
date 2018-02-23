package com.intuit.ui.perf.utilities;

import java.io.File;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.intuit.ui.perf.beans.GitValidationBean;
import com.intuit.ui.perf.entities.Entity_Project;


public class JGitUtilities {

	public GitValidationBean IsValidRemoteRepository(URIish repoUri, CredentialsProvider credentialsProvider,
			String localRepo) {
		File file = new File(localRepo);
		GitValidationBean jgit = new GitValidationBean();
		boolean isValid;
		String message;

		jgit.setGitRepo(repoUri.getHumanishName());

		try {
			Git git = Git.init().setBare(true).setDirectory(file).call();
			StoredConfig config = git.getRepository().getConfig();
			config.setString("remote", "origin", "url", repoUri.toString());
			config.save();
			LsRemoteCommand cmd = git.lsRemote();

			if (credentialsProvider != null) {
				cmd.setCredentialsProvider(credentialsProvider);
			}
			cmd.setRemote("origin").call();
		} catch (TransportException te) {
			isValid = false;
			message = te.getMessage();
			jgit.setIsValid(isValid);
			jgit.setMessage(message);
			return jgit;
		} catch (InvalidRemoteException ire) {
			isValid = false;
			message = ire.getMessage();
			jgit.setIsValid(isValid);
			jgit.setMessage(message);
			return jgit;
		} catch (GitAPIException gae) {
			isValid = false;
			message = gae.getMessage();
			jgit.setIsValid(isValid);
			jgit.setMessage(message);
			return jgit;
		} catch (Exception e) {
			isValid = false;
			message = e.getMessage();
			jgit.setIsValid(isValid);
			jgit.setMessage(message);
			return jgit;
		}
		isValid = true;
		message = "Git Repo is Valid";

		jgit.setIsValid(isValid);
		jgit.setMessage(message);
		return jgit;
	}

	public Entity_Project cloneGITRepository(Entity_Project project) throws InvalidRemoteException, TransportException,
			GitAPIException {
		File file = new File("C:\\Users\\qbdt\\git");
		CloneCommand command = Git.cloneRepository();
		command.setURI(project.getProject_git_repo());
		CloneCommand clone = command.setCredentialsProvider(new UsernamePasswordCredentialsProvider("dyadav1",
				"0ddc09c6e381460826118a36ce381f7d0355092c"));
		Git response = clone.setDirectory(file).call();
		return project;
	}
}
