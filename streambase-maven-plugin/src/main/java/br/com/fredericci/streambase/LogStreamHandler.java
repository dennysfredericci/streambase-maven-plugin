/**
 * 
 */
package br.com.fredericci.streambase;

import org.apache.commons.exec.LogOutputStream;
import org.apache.maven.plugin.logging.Log;

/**
 * @author Dennys Fredericci
 * 
 */
public class LogStreamHandler extends LogOutputStream {

	private final Log log;
	private final WaitCondition waitCondition;

	public LogStreamHandler(Log log, WaitCondition waitCondition) {
		this.log = log;
		this.waitCondition = waitCondition;
	}

	@Override
	protected void processLine(String line, int level) {
		log.info("[STREAMBASE] " + line);
		waitCondition.check(line);
	}

}
