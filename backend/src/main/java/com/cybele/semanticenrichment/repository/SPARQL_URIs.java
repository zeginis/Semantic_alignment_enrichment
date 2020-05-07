package com.cybele.semanticenrichment.repository;

import java.util.Map;

import com.github.jsonldjava.shaded.com.google.common.collect.ImmutableMap;

public final class SPARQL_URIs {
		
	 public static final Map<String, String> CODELISTS = ImmutableMap.of(
		      "ACCRUAL_PERIODICITY", "<http://purl.org/cld/terms/Frequency>",
		      "LANGUAGE", "<http://publications.europa.eu/resource/authority/language>",
		      "COUNTRY", "<http://publications.europa.eu/resource/authority/country>");
}