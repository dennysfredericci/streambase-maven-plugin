package br.com.fredericci.streambase.service;

import java.io.File;

public class Configuration {

	private String application;

	private String[] arguments;

	private String home;

	private String workingDirectory;

	public String getApplication() {
		return application;
	}

	public String[] getArguments() {
		return arguments;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public String sbadmin() {
		return home + File.separator + "bin" + File.separator + "sbadmin";
	}

	public String sbd() {
		return home + File.separator + "bin" + File.separator + "sbd";
	}

}
