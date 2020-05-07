package com.cybele.semanticenrichment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="virtuoso")
public class VirtuosoProperties {
	
	private String endpoint;
    private int port;
    private String user;
    private String password;
    private String codelistGraph;
    private String dataGraph;
    
	public String getCodelistGraph() {
		return codelistGraph;
	}
	public void setCodelistGraph(String codelistGraph) {
		this.codelistGraph = codelistGraph;
	}
	public String getDataGraph() {
		return dataGraph;
	}
	public void setDataGraph(String dataGraph) {
		this.dataGraph = dataGraph;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}         
}







