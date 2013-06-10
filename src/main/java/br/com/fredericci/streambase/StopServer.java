package br.com.fredericci.streambase;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 
 * @goal stop
 * 
 * @author Dennys Fredericci
 */
public class StopServer extends AbstractMojo {

	/**
	 * @required
	 * @parameter property="streambase-home" alias="streambase-home"
	 */
	private File streambaseHome;

	public void execute() throws MojoExecutionException, MojoFailureException {

		try {

			getLog().info("Shutdown streambase server...");

			CommandLine command = new CommandLine(sbadmin());
			command.addArgument("shutdown");

			DefaultExecutor executor = new DefaultExecutor();
			executor.setStreamHandler(new PumpStreamHandler());
			executor.execute(command);

			getLog().info("Shutdown streambase server complete!");

		} catch (IOException e) {
			getLog().error("Shutdown streambase server error", e);
			throw new MojoExecutionException("Error starting streambase server", e);
		}

	}

	private String sbadmin() {
		String file = streambaseHome.getAbsolutePath() + File.separator + "bin" + File.separator + "sbadmin";
		return file;
	}

}
