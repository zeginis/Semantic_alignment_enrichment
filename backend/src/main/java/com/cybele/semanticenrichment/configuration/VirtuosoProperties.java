package com.cybele.semanticenrichment.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="virtuoso")
public class VirtuosoProperties {
	
	private String endpoint;
    private int port;
    private String user;
    private String password;
    private String frequencyCodelistGraph;
    private String languageCodelistGraph;
    private String countryCodelistGraph;
    private String agentCodelistGraph;
    private String dataGraph;
    
    
    
	
    public String getfrequencyCodelistGraph() {
		return frequencyCodelistGraph;
	}
	public void setfrequencyCodelistGraph(String frequencyCodelistGraph) {
		this.frequencyCodelistGraph = frequencyCodelistGraph;
	}
	
	public String getlanguageCodelistGraph() {
		return languageCodelistGraph;
	}
	public void setlanguageCodelistGraph(String languageCodelistGraph) {
		this.languageCodelistGraph = languageCodelistGraph;
	}
	
    public String getcountryCodelistGraph() {
		return countryCodelistGraph;
	}
	public void setcountryCodelistGraph(String countryCodelistGraph) {
		this.countryCodelistGraph = countryCodelistGraph;
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
	public String getagentCodelistGraph() {
		return agentCodelistGraph;
	}
	public void setagentCodelistGraph(String agentCodelistGraph) {
		this.agentCodelistGraph = agentCodelistGraph;
	} 
}







