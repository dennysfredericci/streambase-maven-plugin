package br.com.fredericci.streambase;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * This class shutdown a streambase server using the sbadmin file on
 * streambase-home/bin
 * 
 * @goal shutdown
 * 
 * @author Dennys Fredericci
 */
public class ShutdownServer extends AbstractMojo {

	/**
	 * The streambase installation directory
	 * 
	 * @required
	 * @parameter property="streambase-home" alias="streambase-home"
	 */
	private File streambaseHome;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		try {

			getLog().info("Shutdown streambase server...");
			shutdown();
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

	/**
	 * Execute the commando line sbadmin shutdown
	 */
	private void shutdown() throws ExecuteException, IOException {
		CommandLine command = new CommandLine(sbadmin());
		command.addArgument("shutdown");

		DefaultExecutor executor = new DefaultExecutor();
		executor.setStreamHandler(new PumpStreamHandler());
		executor.execute(command);
	}

}
