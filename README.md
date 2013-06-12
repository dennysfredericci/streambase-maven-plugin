streambase-maven-plugin
=======================

[![Build Status](https://travis-ci.org/dennysfredericci/streambase-maven-plugin.png?branch=master)](https://travis-ci.org/dennysfredericci/streambase-maven-plugin)

This is a simple maven streambase plugin, useful for integration tests



pom.xml :
    
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    	<modelVersion>4.0.0</modelVersion>
    	<groupId>br.com.fredericci.test</groupId>
    	<artifactId>sb-test-plugin</artifactId>
    	<version>0.0.1-SNAPSHOT</version>
    
    	<build>
    		<plugins>
    
    			<plugin>
    				<groupId>org.apache.maven.plugins</groupId>
    				<artifactId>maven-failsafe-plugin</artifactId>
    				<version>2.14.1</version>
    				<executions>
    					<execution>
    						<goals>
    							<goal>integration-test</goal>
    							<goal>verify</goal>
    						</goals>
    					</execution>
    				</executions>
    			</plugin>
    
    
    			<plugin>
    				<groupId>br.com.fredericci.streambase</groupId>
    				<artifactId>streambase-maven-plugin</artifactId>
    				<version>1.0.0-SNAPSHOT</version>
    				<configuration>
    					<working-directory>/Applications/StreamBase -
    						7.3/workspace/demo_Financial - Market Feed Monitor</working-directory>
    					<streambase-home>/Applications/StreamBase - 7.3/StreamBase 7.3.4/</streambase-home>
    					<application>MarketFeedMonitor.sbapp</application>
    				</configuration>
    				<executions>
    					<execution>
    						<id>pre-it-start</id>
    						<goals>
    							<goal>start</goal>
    						</goals>
    						<phase>pre-integration-test</phase>
    					</execution>
    					<execution>
    						<id>post-it-stop</id>
    						<goals>
    							<goal>shutdown</goal>
    						</goals>
    						<phase>post-integration-test</phase>
    					</execution>
    				</executions>
    			</plugin>
    
    
    		</plugins>
    	</build>
    </project>
