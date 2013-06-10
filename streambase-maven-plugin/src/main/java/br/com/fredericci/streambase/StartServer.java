package br.com.fredericci.streambase;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 
 * @goal start
 * 
 * @author Dennys Fredericci
 */
public class StartServer extends AbstractMojo {

	/**
	 * @required
	 * @parameter property="application" alias="application"
	 */
	private String application;

	/**
	 * @parameter property="arguments" alias="arguments"
	 */
	private String[] arguments;

	/**
	 * @required
	 * @parameter property="streambase-home" alias="streambase-home"
	 */
	private File streambaseHome;

	/**
	 * @parameter property="wait-for" alias="wait-for"
	 */
	private String waitFor;

	/**
	 * @parameter property="working-directory" alias="working-directory"
	 */
	private File workingDirectory;

	public void execute() throws MojoExecutionException, MojoFailureException {

		try {

			getLog().info("Starting Streambase Server...");

			CommandLine command = getCommand();
			executeCommand(command);

			getLog().info("Streambase Server started!");

		} catch (Exception e) {
			getLog().error("Error starting streambase server!", e);
			throw new MojoExecutionException("Error starting streambase server", e);
		}

	}

	private void executeCommand(CommandLine command) throws ExecuteException, IOException, InterruptedException {
		WaitCondition waitCondition = new WaitCondition(waitFor);
		DefaultExecutor executor = new DefaultExecutor();
		executor.setWorkingDirectory(workingDirectory);
		executor.setStreamHandler(new PumpStreamHandler(new LogStreamHandler(getLog(), waitCondition)));
		executor.execute(command, new DefaultExecuteResultHandler());
		waitCondition.await();
	}

	private CommandLine getCommand() {
		String sbd = streambaseHome.getAbsolutePath() + File.separator + "bin" + File.separator + "sbd";
		CommandLine command = new CommandLine(sbd);
		command.addArguments(arguments);
		command.addArgument(application);
		return command;
	}
}
