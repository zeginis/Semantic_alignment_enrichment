package com.cybele.semanticenrichment.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cybele.semanticenrichment.domain.Dataset;
import com.cybele.semanticenrichment.domain.SKOSConcept;
import com.cybele.semanticenrichment.exception.CodelistNotFoundException;
import com.cybele.semanticenrichment.repository.DatasetRepository;
import com.cybele.semanticenrichment.util.DatasetUtils;


@RestController()
@RequestMapping("/api")
public class BackendController {
		
	@Autowired
	DatasetRepository dtr;
	
    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);
            
    @PostMapping(path = "/dataset")
    @ResponseStatus(HttpStatus.CREATED)
    public URI addNewDataset (@RequestBody Dataset dataset) {    	   	
   		if(dataset.getIdentifier()!=null) {
   			  dataset.setUri(DatasetUtils.uriFromID("dataset", dataset.getIdentifier()));
   		}else {
   			  dataset.setUri(DatasetUtils.randomURI("dataset"));   		
   		}    	    	
    	Dataset insertedDataset= dtr.insertDataset(dataset);
	    LOG.info(insertedDataset.getUri() + " successfully saved into DB");
	    return insertedDataset.getUri();
    }

    @GetMapping(path = "/codelist/{id}")
    public List<SKOSConcept> getCodelist(@PathVariable("id") String id) {
        List<SKOSConcept> list=dtr.getCodelistContent(id);
        if (list.size()>0) {
        	return list;
        }else {
        	throw new CodelistNotFoundException("The codelist with the id " + id + " couldn't be found.");
        }
    }
  

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
    public String redirectApi() {
        LOG.info("URL entered directly into the Browser, so we need to redirect...");
        return "forward:/";
    }

}
