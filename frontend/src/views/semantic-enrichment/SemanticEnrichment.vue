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
	
	<div v-if="!showResponse">
   		 <h3>Create new dataset metadata record</h3>
         
		<div class="form-group">
    		<label class="font-weight-bold">Label</label>
    		<input type="text" v-model="dataset.label" class="form-control" placeholder="Enter label here...">    		
    	</div>
      	<div  class="form-group"  >
    		<label class="font-weight-bold">Language</label>
    		<select v-if="isLanguageReady"  v-model="dataset.language" class="form-control"  >
  			  <option v-for="l in languageCodeList" v-bind:value="l.uri" >{{ l.label }} </option>
  			</select>    
  			<div v-if="!isLanguageReady" v-cloak>
  			   <input class="form-control" placeholder="Loading languages...">    	
  			</div>  					
    	</div>    	
    	<div class="form-group">
    		<label class="font-weight-bold">Issued</label>    		
    		<input type="date" v-model="dataset.issued" class="form-control" placeholder="Select or enter issued date here...">    		
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold">Modified</label>
    		<input type="date" v-model="dataset.modified" class="form-control" placeholder="Select or enter modified date here...">    		
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold">Accrual Periodicity</label>
    		<select v-model="dataset.periodicity" class="form-control" >
  			  <option v-for="option in accrualPeriodicity" v-bind:value="option.uri">{{ option.label }} </option>
  			</select>    		
    	</div>
    	<div class="card bg-light">    		
    		<div class="card-title">Temporal coverage</div>
  			<div class="card-body">
		    	<label class="font-weight-bold">Start date</label>
    			<input type="date" v-model="dataset.temporalStart" class="form-control" placeholder="Select or enter start date here...">    		
    		</div>
    		<div class="card-body">
				<label class="font-weight-bold">End date</label>
    			<input type="date" v-model="dataset.temporalEnd" class="form-control" placeholder="Select or enter end date here...">    		
    		</div>    	
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold">Temporal Resolution</label>
    		<p>Temporal Resolution format: <b>P</b>n<b>Y</b>n<b>M</b>n<b>DT</b>n<b>H</b>n<b>M</b>n<b>S</b> where P=start of expression, n=value, Y=years, M=months, D=days, T=separate time, H=hours, 
    		 M=minutes, S=seconds</p>
    		<input type="text" v-model="dataset.temporalResolution" class="form-control" placeholder="Enter the temporal resolution...">    		
    	</div>
    	<div  class="form-group"  >
    		<label class="font-weight-bold">Spatial coverage</label>
    		<select v-if="isSpatialReady"  v-model="dataset.spatial" class="form-control"  >
  			  <option v-for="c in spatialCodeList" v-bind:value="c.uri" >{{ c.label }} </option>
  			</select>    
  			<div v-if="!isSpatialReady" v-cloak>
  			   <input class="form-control" placeholder="Loading Countries...">    	
  			</div>  					
    	</div>    	 	
    	<div class="form-group">
    		<label class="font-weight-bold">Spatial resolution in meters</label>
    		<input type="text" v-model="dataset.spatialResolution" class="form-control" placeholder="Enter the spatial resolution...">    		
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold">Conforms to standard</label>
    		<input type="text" v-model="dataset.conformsTo" class="form-control" placeholder="Enter the conforms to standard...">    		
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold">Landing page</label>
    		<input type="text" v-model="dataset.landingPage" class="form-control" placeholder="Enter the landing page URL...">    		
    	</div>
    	<div class="form-group">
    		<label class="font-weight-bold">Publisher</label>
    		<input type="text" v-model="dataset.publisher" class="form-control" placeholder="Enter the publisher...">    		
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
        isLanguageReady: false,
        isSpatialReady:false,
        showRetrievedUser: false
      }
    },
    mounted() {    
  	  this.getAccrualPeriodicityCodelist();
  	  this.getLanguageCodelist();
  	  this.getSpatialCodelist();
    },
    methods: {
      // Fetches posts when the component is created.
      createNewDataset () {
        api.createDatasetMetadata(this.dataset.label, this.dataset.language, this.dataset.issued, this.dataset.modified, 
        		this.dataset.periodicity, this.dataset.temporalStart,this.dataset.temporalEnd,this.dataset.temporalResolution,
        		this.dataset.spatial,this.dataset.spatialResolution, this.dataset.conformsTo, this.dataset.landingPage,
        		this.dataset.publisher).then(response => {
            // JSON responses are automatically parsed.
            this.response = response.data;
            this.dataset.id = response.data;
            console.log('Created new Dataset with Id ' + response.data);
            this.showResponse = true
          })
          .catch(e => {
            this.errors.push(e)
          })
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
