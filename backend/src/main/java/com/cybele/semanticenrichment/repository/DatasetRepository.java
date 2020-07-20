package com.cybele.semanticenrichment.repository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cybele.semanticenrichment.configuration.VirtuosoProperties;
import com.cybele.semanticenrichment.domain.Dataset;
import com.cybele.semanticenrichment.domain.SKOSConcept;
import com.cybele.semanticenrichment.util.DatasetUtils;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;




@Component
public class DatasetRepository {
		 
	private static final Logger LOG = LoggerFactory.getLogger(DatasetRepository.class);  
  
	@Autowired
	VirtuosoProperties vp;
	
	public Dataset insertDataset(Dataset dataset) {
	  LOG.info("Inserting dataset:"+dataset);
	  if(dataset.getUri()==null) {
		  dataset.setUri(DatasetUtils.randomURI("dataset"));
	  }
	  String sparql = "INSERT DATA{ GRAPH <"+vp.getDataGraph()+">{"
	  		+ "<"+dataset.getUri()+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/dcat#Dataset>.";
	  if(dataset.getLabel()!=null && !dataset.getLabel().equals("")) {
		  sparql+= "<"+dataset.getUri()+"> <http://purl.org/dc/terms/title> \"" +dataset.getLabel()+"\".";
	  }
	  if(dataset.getLanguage()!=null && !dataset.getLanguage().equals("")) {
		  sparql+="<"+dataset.getUri()+"> <http://purl.org/dc/terms/language> <" +dataset.getLanguage()+">.";
	  }
	  if(dataset.getIssued()!=null && !dataset.getIssued().equals("")) {
		  sparql+="<"+dataset.getUri()+"> <http://purl.org/dc/terms/issued> \"" +dataset.getIssued()+"\"^^<http://www.w3.org/2001/XMLSchema#date>.";;
	  }
	  if(dataset.getModified()!=null && !dataset.getModified().equals("")) {
		  sparql+="<"+dataset.getUri()+"> <http://purl.org/dc/terms/modified> \"" +dataset.getModified()+"\"^^<http://www.w3.org/2001/XMLSchema#date>.";
		  						
	  }
	  if(dataset.getAccrualPeriodicity()!=null) {
		  sparql+="<"+dataset.getUri()+"> <hhttp://purl.org/dc/terms/accrualPeriodicity> <" +dataset.getAccrualPeriodicity()+">.";
	  }
	  if(dataset.getTemporalStart()!=null && dataset.getTemporalEnd()!=null && !dataset.getTemporalStart().equals("") && !dataset.getTemporalEnd().equals("")) {
		  URI temporalRandom=DatasetUtils.randomURI("temporal");
		  sparql+="<"+dataset.getUri()+"> <http://purl.org/dc/terms/temporal> <" +temporalRandom+">."
				+ "<" +temporalRandom+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://purl.org/dc/terms/PeriodOfTime>." 
		  		+ "<"+temporalRandom+"> <http://www.w3.org/ns/dcat#startDate> \"" + dataset.getTemporalStart()+"\"^^<http://www.w3.org/2001/XMLSchema#date>."
		  		+ "<"+temporalRandom+"> <http://www.w3.org/ns/dcat#endDate> \""+dataset.getTemporalEnd()+"\"^^<http://www.w3.org/2001/XMLSchema#date>.";
	  }
	  if(dataset.getTemporalResolution()!=null && !dataset.getTemporalResolution().equals("")) {
		  sparql+="<"+dataset.getUri()+"> <http://www.w3.org/ns/dcat#temporalResolution> \"" +dataset.getTemporalResolution()+"\".";
	  }
	  if(dataset.getSpatial()!=null && !dataset.getSpatial().equals("")) {
		  sparql+="<"+dataset.getUri()+"> <http://purl.org/dc/terms/spatial> <" +dataset.getSpatial()+">.";
	  }
	  if(dataset.getSpatialResolution()!=null && !dataset.getSpatialResolution().equals("")) {
		  sparql+="<"+dataset.getUri()+"> <http://www.w3.org/ns/dcat#spatialResolutionInMeters> " +dataset.getSpatialResolution()+" .";
	  }
	  if(dataset.getConformsTo()!=null && !dataset.getConformsTo().equals("")) {
		  sparql+="<"+dataset.getUri()+"> <http://purl.org/dc/terms/conformsTo> <" +dataset.getConformsTo()+">.";
	  }
	  if(dataset.getLandingPage()!=null && !dataset.getLandingPage().equals("")) {
		  sparql+="<"+dataset.getUri()+"> <http://www.w3.org/ns/dcat#landingPage> <" +dataset.getLandingPage()+">.";
	  }
	  if(dataset.getPublisher()!=null && !dataset.getPublisher().equals("")) {
		  sparql+="<"+dataset.getUri()+"> <http://purl.org/dc/terms/publisher> \"" +dataset.getPublisher()+"\".";
	  }
	  sparql+="}}";
	  LOG.info(sparql);
	  VirtGraph set = new VirtGraph ("jdbc:virtuoso://"+vp.getEndpoint()+":"+vp.getPort(), vp.getUser(), vp.getPassword());
	  VirtuosoUpdateRequest update = VirtuosoUpdateFactory.create(sparql,set);
	  update.exec();	   
	  return dataset;
  }
  
  public List<SKOSConcept> getCodelistContent(String codelist) {
	  	  
	  List<SKOSConcept> list=new ArrayList<SKOSConcept>();
	  String codelistURI=SPARQL_URIs.CODELISTS.get(codelist);
	  if(codelistURI!=null || codelist.equals(SPARQL_URIs.AGENT)) {
		  LOG.info("Retrieving codelist:"+codelist);
		  String query="PREFIX skos: <http://www.w3.org/2004/02/skos/core#>"
		  		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
		  		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
		  		+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
		  		+ "select ?concept ?label ";
		  if(codelist.equals(SPARQL_URIs.ACCRUAL_PERIODICITY)) {
			  query+= "FROM  <"+vp.getfrequencyCodelistGraph()+">";
		  }else if(codelist.equals(SPARQL_URIs.COUNTRY)) {
			  query+= "FROM  <"+vp.getcountryCodelistGraph()+">";
		  }else if(codelist.equals(SPARQL_URIs.LANGUAGE)) {
			  query+= "FROM  <"+vp.getlanguageCodelistGraph()+">";
		  }else if(codelist.equals(SPARQL_URIs.AGENT)) {
			  query+= "FROM  <"+vp.getagentCodelistGraph()+">";
		  }
		  //Agents are not in a skos code list.
		  if(codelist.equals(SPARQL_URIs.AGENT)) {
			  query+= "where{?concept rdf:type foaf:Organization."
				  		+ "?concept rdfs:label ?label.\n" 
				  		+ "FILTER langMatches( lang(?label), \"en\" ) }"
				  		+ "ORDER BY ?label";			  
		  }else {
			  query+= "where{?concept skos:inScheme "+codelistURI+"."
				  		+ "?concept skos:prefLabel ?label.\n" 
				  		+ "FILTER langMatches( lang(?label), \"en\" ) }"
				  		+ "ORDER BY ?label";
		  }
		  VirtGraph set = new VirtGraph ("jdbc:virtuoso://"+vp.getEndpoint()+":"+vp.getPort(), vp.getUser(), vp.getPassword());
	
		  Query sparql = QueryFactory.create(query);
		  VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, set);  
		  
		  ResultSet results = vqe.execSelect();		 
		  while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
			SKOSConcept c=new SKOSConcept();
			c.setURI(result.get("concept").toString());
			c.setLabel(result.get("label").asLiteral().getString());
			list.add(c);
		  }
	  }else {
		  LOG.info("Codelist not found:"+codelist);
	  }
	  return list; 	
  }
  
  public List<SKOSConcept> getAgents(String codelist) {
  	  
	  List<SKOSConcept> list=new ArrayList<SKOSConcept>();
	  String codelistURI=SPARQL_URIs.CODELISTS.get(codelist);
	  if(codelistURI!=null) {
		  LOG.info("Retrieving codelist:"+codelist);
		  String query="PREFIX skos: <http://www.w3.org/2004/02/skos/core#>"
		  		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
		  		+ "select ?concept ?label ";
		  if(codelist.equals(SPARQL_URIs.ACCRUAL_PERIODICITY)) {
			  query+= "FROM  <"+vp.getfrequencyCodelistGraph()+">";
		  }else if(codelist.equals(SPARQL_URIs.COUNTRY)) {
			  query+= "FROM  <"+vp.getcountryCodelistGraph()+">";
		  }else if(codelist.equals(SPARQL_URIs.LANGUAGE)) {
			  query+= "FROM  <"+vp.getlanguageCodelistGraph()+">";
		  }
		  query+= "where{?concept skos:inScheme "+codelistURI+"."
		  		+ "?concept skos:prefLabel ?label.\n" 
		  		+ "FILTER langMatches( lang(?label), \"en\" ) }"
		  		+ "ORDER BY ?label";	  
		  VirtGraph set = new VirtGraph ("jdbc:virtuoso://"+vp.getEndpoint()+":"+vp.getPort(), vp.getUser(), vp.getPassword());
	
		  Query sparql = QueryFactory.create(query);
		  VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, set);  
		  
		  ResultSet results = vqe.execSelect();		 
		  while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
			SKOSConcept c=new SKOSConcept();
			c.setURI(result.get("concept").toString());
			c.setLabel(result.get("label").asLiteral().getString());
			list.add(c);
		  }
	  }else {
		  LOG.info("Codelist not found:"+codelist);
	  }
	  return list; 	
  }
  
}
