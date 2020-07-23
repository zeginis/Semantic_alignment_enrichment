<template>
  <div class="SemanticEnrichment">
  
  <img src="@/assets/images/semanticEnrichmentBanner.jpg" class="img-fluid" style="max-width:700px">  
    <h1>Dataset metadata</h1>
    This form allows you to introduce metadata about relevant datasets in CYBELE project in compliance with 
    
    <a href="https://gitlab.res.eng.it/cybele/data/cybele-common-semantic-model">CYBELE semantic model</a>
	  
	  
	<div v-if="showResponse">
		<p>
		<h5>Dataset created with Id: {{ dataset.id }} <img src="@/assets/images/done.png" class="img-fluid" style="max-width:60px"></h5>
	</div>
	
	<button v-if="showResponse" class="btn btn-primary" @click="enterNewDataset()">Enter new dataset</button>
	
	<div v-if="errors.length" style="color:red">
    	<b>Please correct the following error(s):</b>
    	<ul>
      		<li v-for="error in errors">{{ error }}</li>
    	</ul>
   </div>  
	
	<div v-if="!showResponse">
   		 <h3>Create new dataset metadata record</h3>
         <p style="color:gray"><i>* required information</i></p>
         
		<div class="form-group">
    		<label class="font-weight-bold my-0">Label *</label>
    		<p class="my-0"><small><em>A name given to the dataset.</em></small></p>
    		<input type="text" v-model="dataset.label" class="form-control" placeholder="Enter label here..." required>    		
    	</div>
      	<div  class="form-group"  >
    		<label class="font-weight-bold my-0">Language *</label>
    		<p class="my-0"><small><em>The language of the dataset.</em></small></p>
    		<select v-if="isLanguageReady"  v-model="dataset.language" class="form-control"  >
  			  <option v-for="l in languageCodeList" v-bind:value="l.uri" >{{ l.label }} </option>
  			</select>    
  			<div v-if="!isLanguageReady" v-cloak>
  			   <input class="form-control" placeholder="Loading languages...">    	
  			</div>  					
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold my-0">Publisher *</label>
    		<p class="my-0"><small><em>The entity responsible for making the dataset available.</em></small></p>
    		<select v-if="isAgentReady"  v-model="dataset.publisher" class="form-control"  >
  			  <option v-for="a in agentCodeList" v-bind:value="a.uri" >{{ a.label }} </option>
  			</select>    
  			<div v-if="!isAgentReady" v-cloak>
  			   <input class="form-control" placeholder="Loading Agents...">    	
  			</div>    		    		
    	</div>    		
    	<div class="form-group">
    		<label class="font-weight-bold my-0">Issued</label>    		
    		<p class="my-0"><small><em>The date of listing the dataset in the CYBELE platform.</em></small></p>
    		<input type="date" v-model="dataset.issued" class="form-control" placeholder="Select or enter issued date here...">    		
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold my-0">Modified</label>
    		<p class="my-0"><small><em>Most recent date on which the dataset was changed, updated or modified..</em></small></p>
    		<input type="date" v-model="dataset.modified" class="form-control" placeholder="Select or enter modified date here...">    		
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold my-0">Accrual Periodicity</label> 
    		<p class="my-0"><small><em>The frequency at which dataset is published.</em></small></p>
    		<select v-model="dataset.periodicity" class="form-control" >
  			  <option v-for="option in accrualPeriodicity" v-bind:value="option.uri">{{ option.label }} </option>
  			</select>    		
    	</div>
    	<div class="card bg-light py-0">    		
    		<div class="card-title  my-0">Temporal coverage *</div>
    		<p class="my-0"><small><em>The temporal period that the dataset covers.</em></small></p>
  			<div class="card-body  py-1">
		    	<label class="font-weight-bold">Start date</label>
    			<input type="date" v-model="dataset.temporalStart" class="form-control" placeholder="Select or enter start date here...">    		
    		</div>
    		<div class="card-body  py-1">
				<label class="font-weight-bold">End date</label>
    			<input type="date" v-model="dataset.temporalEnd" class="form-control" placeholder="Select or enter end date here...">    		
    		</div>    	
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold my-0">Temporal Resolution</label>
    		<p class="my-0"><small><em>Minimum time period resolvable in the dataset.</em></small></p> 
    		<p class="my-0"><small><em>Format: <b>P</b>n<b>Y</b>n<b>M</b>n<b>DT</b>n<b>H</b>n<b>M</b>n<b>S</b> where P=start of expression, n=value, Y=years, M=months, D=days, T=separate time, H=hours, 
    		 M=minutes, S=seconds</em></small></p>
    		<input type="text" v-model="dataset.temporalResolution" class="form-control" placeholder="Enter the temporal resolution...">    		
    	</div>    	
    	<div  class="form-group"  >
    		<label class="font-weight-bold my-0">Spatial coverage *</label>
    		<p class="my-0"><small><em>The geographical area covered by the dataset.</em></small></p>    		
    		<select v-if="isSpatialReady"  v-model="dataset.spatial" class="form-control"  >
  			  <option v-for="c in spatialCodeList" v-bind:value="c.uri" >{{ c.label }} </option>
  			</select>    
  			<div v-if="!isSpatialReady" v-cloak>
  			   <input class="form-control" placeholder="Loading Countries...">    	
  			</div>  					
    	</div>    	 	
    	<div class="form-group">
    		<label class="font-weight-bold my-0" >Spatial resolution</label>
    		<p class="my-0"><small><em>Minimum spatial separation resolvable in a dataset, measured in meters. </em></small></p> 
    		<input type="number" v-model="dataset.spatialResolution" class="form-control" placeholder="Enter the spatial resolution...">    		
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold my-0">Conforms to standard</label>
    		<p class="my-0"><small><em>	An established standard to which the distribution conforms. </em></small></p> 
    		<input type="text" v-model="dataset.conformsTo" class="form-control" placeholder="Enter the URI of the standard...">    		
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold my-0">Landing page</label>
    		<p class="my-0"><small><em>A Web page to gain access to the dataset</em></small></p> 
    		<input type="text" v-model="dataset.landingPage" class="form-control" placeholder="Enter the landing page URL...">    		
    	</div>
    	<div  class="form-group"  >
    		<label class="font-weight-bold my-0">License</label>
    		<p class="my-0"><small><em>A legal document under which the distribution is made available.</em></small></p>
    		<select v-if="isLicenseReady"  v-model="dataset.license" class="form-control"  >
  			  <option v-for="lc in licenseCodeList" v-bind:value="lc.uri" >{{ lc.label }} </option>
  			</select>    
  			<div v-if="!isLicenseReady" v-cloak>
  			   <input class="form-control" placeholder="Loading licenses">    	
  			</div>  					
    	</div>      	
    	<div  class="form-group"  >
    		<label class="font-weight-bold my-0">Media type</label>
    		<p class="my-0"><small><em>A legal document under which the distribution is made available.</em></small></p>
    		<select v-if="isMediaTypeReady"  v-model="dataset.mediaType" class="form-control"  >
  			  <option v-for="mt in mediaTypeCodeList" v-bind:value="mt.uri" >{{ mt.label }} </option>
  			</select>    
  			<div v-if="!isMediaTypeReady" v-cloak>
  			   <input class="form-control" placeholder="Loading media types">    	
  			</div>  					
    	</div>
    	    	    
    	<button class="btn btn-primary" @click="createNewDataset()">Save metadata</button>
    </div>	
  </div>
</template>

<script>
  import api from "../backend-api";
  
  export default {
    name: 'SemanticEnrichment',
    data () {
      return {
        response: [],
        errors: [],
        dataset: {
          label: null,
          language: null,
          issued:null,
          modified:null,
          periodicity: null,
          temporalStart:null,
          temporalEnd:null,
          temporalResolution:null,         
          spatial:null,
          spatialResolution:null,
          conformsTo:null,
          landingPage:null,
          publisher:null,
          id: 0
        },
        showResponse: false,
        accrualPeriodicity: [],
        languageCodeList: [], 
        spatialCodeList:[],
        agentCodeList:[],
        licenseCodeList:[],
        mediaTypeCodeList:[],
        isLanguageReady: false,
        isSpatialReady:false,
        isAgentReady:false,
        isMediaTypeReady:false,
        isLicenseReady:false,
        showRetrievedUser: false
      }
    },
    mounted() {    
  	  this.getAccrualPeriodicityCodelist();
  	  this.getLanguageCodelist();
  	  this.getSpatialCodelist();
  	  this.getAgentCodelist();
  	  this.getLicenseCodelist();
  	  this.getMediaTypeCodelist()
    },
	methods: {      
    	enterNewDataset(){
    		this.showResponse = false
    	},
		validURL(str) {
    		var pattern = new RegExp('[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)'); 
    		return pattern.test(str);
      	},	
      	validDuration(str){
    	  var pattern = new RegExp('^(-?)P(?=.)((\\d+)Y)?((\\d+)M)?((\\d+)D)?(T(?=.)((\\d+)H)?((\\d+)M)?(\\d*(\\.\\d+)?S)?)?$');
    	  return pattern.test(str); 	  
    	  },      
      checkForm(){
    	this.errors = [];
      	if (!this.dataset.label) {
              this.errors.push("Label required.");
        }      	
    	if (!this.dataset.language) {
            this.errors.push("Language required.");
      	} 
    	if (!this.dataset.publisher) {
            this.errors.push("Publisher required.");
     	 }
    	if (!this.dataset.temporalStart) {
              this.errors.push("Temporal start required.");
        }      	
      	if (!this.dataset.temporalEnd) {
              this.errors.push("Temporal end required.");
        }
      	if(this.dataset.temporalResolution && !this.validDuration(this.dataset.temporalResolution)){
    		 this.errors.push("Temporal resolution \""+ this.dataset.temporalResolution +"\" is not in valid format.");
    	}
      	if (!this.dataset.spatial) {
            this.errors.push("Spatial required.");
      	} 
      	if(this.dataset.landingPage && !this.validURL(this.dataset.landingPage)){
      		 this.errors.push("Landing page is not a valid URL.");
      	}  
      	if(this.dataset.conformsTo && !this.validURL(this.dataset.conformsTo)){
     		 this.errors.push("The conforms to standard is not a valid URL.");
     	}  
      	      	
      	if (!this.errors.length) {
            return true;
      	}
      	
      	 window.scrollTo({ top: 0, behavior: 'smooth' });
      	
      },	
      // Fetches posts when the component is created.
      createNewDataset () {    	  
    	if(this.checkForm()){
	        api.createDatasetMetadata(this.dataset.label, this.dataset.language, this.dataset.issued, 
	        		this.dataset.modified,this.dataset.periodicity, this.dataset.temporalStart,
	        		this.dataset.temporalEnd,this.dataset.temporalResolution,this.dataset.spatial,
	        		this.dataset.spatialResolution, this.dataset.conformsTo, this.dataset.landingPage,
	        		this.dataset.publisher,this.dataset.license,this.dataset.mediaType,
	        		"20","testTable",["test1","test2","test3"]).then(response => {
	            // JSON responses are automatically parsed.
	            this.response = response.data;
	            this.dataset.id = response.data;
	            console.log('Created new Dataset with Id ' + response.data);
	            this.showResponse = true
	          })
	          .catch(e => {
	            this.errors.push(e)
	          })
    	}    	
      },
      getAccrualPeriodicityCodelist(){
    	  api.getCodelistContent('ACCRUAL_PERIODICITY').then(response => {
              // JSON responses are automatically parsed.
              this.accrualPeriodicity = response.data;              
            })
            .catch(e => {
              this.errors.push(e)
            })
      },
      getLanguageCodelist(){
    	  api.getCodelistContent('LANGUAGE').then(response => {
              // JSON responses are automatically parsed.
              this.languageCodeList = response.data;    
              this.isLanguageReady=true
              
            })           
            .catch(e => {
              this.errors.push(e)             
            })    	  
      },
      getSpatialCodelist(){
    	  api.getCodelistContent('COUNTRY').then(response => {
              // JSON responses are automatically parsed.
              this.spatialCodeList = response.data;    
              this.isSpatialReady=true              
            })           
            .catch(e => {
              this.errors.push(e)             
            })    	  
      },
      getAgentCodelist(){
    	  api.getCodelistContent('AGENT').then(response => {
              // JSON responses are automatically parsed.
              this.agentCodeList = response.data;    
              this.isAgentReady=true              
            })           
            .catch(e => {
              this.errors.push(e)             
            })    	  
      },
      getLicenseCodelist(){
    	  api.getCodelistContent('LICENSE').then(response => {
              // JSON responses are automatically parsed.
              this.licenseCodeList = response.data;    
              this.isLicenseReady=true              
            })           
            .catch(e => {
              this.errors.push(e)             
            })    	  
      },
      getMediaTypeCodelist(){
    	  api.getCodelistContent('MEDIA_TYPE').then(response => {
              // JSON responses are automatically parsed.
              this.mediaTypeCodeList = response.data;    
              this.isMediaTypeReady=true              
            })           
            .catch(e => {
              this.errors.push(e)             
            })    	  
      }
      
     
/*      retrieveUser () {
        api.getUser(this.user.id).then(response => {
            // JSON responses are automatically parsed.
            this.retrievedUser = response.data;
            this.showRetrievedUser = true
          })
          .catch(e => {
            this.errors.push(e)
          })
      }
      
      <button v-if="showResponse"  @click="retrieveUser()">Retrieve user {{user.id}} data from database</button>

      <h4 v-if="showRetrievedUser">Retrieved User {{retrievedUser.firstName}} {{retrievedUser.lastName}}</h4>*/
    }
  }

</script>
