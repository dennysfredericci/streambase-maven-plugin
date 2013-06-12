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
 * This class start a streambase server using the sbd file on
 * streambase-home/bin
 * 
 * @goal start
 * 
 * @author Dennys Fredericci
 */
public class StartServer extends AbstractMojo {

	/**
	 * The main application .sbapp, .ssql, .sbar, .sbbundle, or .sbdeploy file
	 * 
	 * @required
	 * @parameter property="application" alias="application"
	 */
	private String application;

	/**
	 * Any argument used to send do sbd
	 * 
	 * @parameter property="arguments" alias="arguments"
	 */
	private String[] arguments;

	/**
	 * The streambase installation directory
	 * 
	 * @required
	 * @parameter property="streambase-home" alias="streambase-home"
	 */
	private File streambaseHome;

	/**
	 * Parameter used to wait until your process is ready!
	 * 
	 * The method to check it verify if waitFor contains in console log.
	 * 
	 * The default value is: StreamBaseHTTPServer
	 * 
	 * @parameter property="wait-for" alias="wait-for"
	 *            default-value="StreamBaseHTTPServer"
	 */
	private String waitFor;

	/**
	 * @parameter property="working-directory" alias="working-directory"
	 */
	private File workingDirectory;

	/**
	 * The main method for goal start
	 */
	@Override
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

	/**
	 * This method execute the command line async, but use the waitFor to
	 * continue execution
	 */
	private void executeCommand(CommandLine command) throws ExecuteException, IOException, InterruptedException {
		WaitCondition waitCondition = new WaitCondition(waitFor);
		DefaultExecutor executor = new DefaultExecutor();
		executor.setWorkingDirectory(workingDirectory);
		executor.setStreamHandler(new PumpStreamHandler(new LogStreamHandler(getLog(), waitCondition)));
		executor.execute(command, new DefaultExecuteResultHandler());
		waitCondition.await();
	}

	/**
	 * @return {@link CommandLine} for sbd
	 */
	private CommandLine getCommand() {
		String sbd = streambaseHome.getAbsolutePath() + File.separator + "bin" + File.separator + "sbd";
		CommandLine command = new CommandLine(sbd);
		command.addArguments(arguments);
		command.addArgument(application);
		return command;
	}
}
