//URLs
var projects = "/uiperformance/rest/project/projects";
var workflows = "/uiperformance/rest/project/workflows";
var pages = "/uiperformance/rest/project/pages";
var workflow_executions = "/uiperformance/rest/project/allworkflows"
var builds = "/uiperformance/rest/project/builds";
var baselineservice = "/uiperformance/rest/project/baselineservice"
var regions = "/uiperformance/rest/project/regions";

//Cookies
var regionselected = getCookie("Region");
var projectselected = getCookie("Project");
var envselected = getCookie("Environment");
var workflowselected = getCookie("Workflow");
var pageselected = getCookie("Page");
var buildselected = getCookie("Build");


// Page Load function
function onPageLoad(perf_projects, perf_workflows) {
	if (envselected == undefined) {
		$('#perf_env').prepend(
				"<option selected disabled>Choose Environment</option>");
	} else {
		$('#perf_env').val(envselected);
	}
	
	if (buildselected === undefined) {
		$('#perf_builds').prepend(
				'<option selected disabled>Choose Build</option>')
	}else {
		$('#perf_builds').val(buildselected);
	}
	
	if (regionselected === undefined) {
		$('#perf_region').prepend(
				'<option selected disabled>Choose Region</option>')
	}else {
		$('#perf_region').val(regionselected);
	}

	populateTablewithPageData();
}

/*******************************************************************************
 * Rest Services Functions*******************
 ******************************************************************************/

// Load All Projects
function loadProjects() {

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : projects,
		data : envToJson(),
		dataType : "json",
		success : function(data, textStatus, jqXHR) {

			if (projectselected == undefined) {
				$('#perf_projects').append(
						'<option selected disabled>Choose Project</option>')
			}
			for (i = 0; i < data.length; i++) {

				if (data[i] == projectselected) {
					$('#perf_projects').append(
							'<option selected>' + data[i] + '</option>');
				} else {
					$('#perf_projects').append(
							'<option>' + data[i] + '</option>');
				}
			}
			perf_projects = data;
			loadBuilds();
			loadRegions();
			loadProjectSpecificWorkflows();
		}
	});
}

// Load All Projects
function loadBuilds() {

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : builds,
		data : projectselectedToJson(),
		dataType : "json",
		success : function(data, textStatus, jqXHR) {

			for (i = 0; i < data.length; i++) {

				if (data[i] == buildselected) {
					$('#perf_builds').append(
							'<option selected>' + data[i] + '</option>');
				} else {
					$('#perf_builds')
							.append('<option>' + data[i] + '</option>');
				}
			}

		}
	});
}



//Load All Regions
function loadRegions() {
	
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : regions,
		data : forRegionJson(),
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			
			for (q = 0; q < data.length; q++) {

				if (data[q] == buildselected) {
					$('#perf_region').append(
							'<option selected>' + data[q] + '</option>');
				}else {
					$('#perf_region').append(
							'<option>' + data[q] + '</option>');
				}
			}
			
		}
	});
}




// Load All Pages based on Project Selected
function loadProjectSpecificWorkflows() {
	$
			.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : workflows,
				dataType : "json",
				data : projectselectedToJson(),
				success : function(data, textStatus, jqXHR) {
					for (k = 0; k < data.length; k++) {
						$('#side-menu2')
								.append(
										'<li><a id='
												+ data[k]
												+ ' onclick=setWorkflowcookie($(this).attr("id"));location.href="index.html";>'
												+ data[k] + '</a></li>')

					}
				}
			});
}

// Load All Pages based on Project Selected
function populateTablewithPageData() {
	$
			.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : workflow_executions,
				dataType : "json",
				data : projectselectedToJson(),
				success : function(hashmap) {

					for ( var i in hashmap) {
						
						
						console.log("---------------"
								+ hashmap[i].date_of_execution)

						$('#datatable-responsive-wfbased')
								.append(
										'<tr id='
												+ i
												+ ' onmouseover="showTable('+i+')"; onmouseleave="hideTable('+i+')")>'
												+ '												<td>'
												+ hashmap[i].date_of_execution
												+ '</td>'
												+ '												<td><a>'
												+ hashmap[i].workflow
												+ '</a></td>'
												+ '												<td><b><a>'
												+ hashmap[i].workflow_execution_time
												+ '</a></b></td>'
												+ '												<td><a>'
												+ hashmap[i].build
												+ '</a></td>'
												+ '											    <td><a>'
												+ hashmap[i].region
												+ '</a></td>'

												+ '												<td><a id='
												+ i
												+ ' class="btn btn-primary btn-xs" href=#'
												+ ' onclick="baselineWorkflow(this.id)";><i'
												+ '														class="fa"></i> Baseline this Execution </a></td>'
												+ '											</tr>');

						
						var sources = hashmap[i].sources;
						
						for(var x in sources){
							
							$('#datatable-pagelevelbreak')
							.append(
									'<tr id=page_'+sources[x].run_id+' class=page_'+sources[x].run_id+' style="display:none">'
											
											+ '												<td>'
											+ sources[x].page
											+ '</td>'
											+ '												<td>'
											+ sources[x].loadtime
											+ '</td>'
											+ '												<td>'
											+ sources[x].speedindex
											+ '</td>'
											+ '												<td>'
											+ sources[x].firstByte
											+ '</td>'
											+ '											    <td>'
											+ sources[x].domCount
											+ '</td>'
											+ '											    <td>'
											+ sources[x].rum
											+ '</td>'
											+ '											</tr>');
							
						}
						
						
						
						
					}

				}

			});
}

function baselineWorkflow(run_id) {

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : baselineservice,
		data : baselineObject(run_id),
		dataType : "json",
		success : function(data, textStatus, jqXHR) {

			if (data.result == "success") {
				alert("Workflow Execution is Baselined !!!")
			} else {
				alert("Problem while baselining execution !!!")
			}
		}
	});
}


function showTable(i){
	var classname="page_"+i["0"].id
	console.log(classname)
	//document.getElementsByClassName(classname).style.visibility = "visible";
	$("[id=page-level-break]").show();
	$("[id="+classname+"]").show();
	
	
}


function hideTable(i){
	var classname="page_"+i["0"].id
	console.log(classname)
	//document.getElementsByClassName(classname).style.visibility = "visible";
	$("[id=page-level-break]").hide(); 
	$("[id="+classname+"]").hide();
}


/*******************************************************************************
 * Json Helper Functions*******************
 ******************************************************************************/

// Helper function to serialize all the form fields into a JSON string
function workflowToJson() {
	var data = JSON.stringify({
		"workflow" : workflowselected,
		"project" : projectselected,
		"env" : envselected,
		"page" : pageselected
	});
	console.log('----->' + data);
	return data;
}

// Helper function to serialize all the form fields into a JSON string
function envToJson() {

	var data = JSON.stringify({
		"env" : envselected
	});
	return data;
}

// Helper function to serialize all the form fields into a JSON string
function projectselectedToJson() {

	var data = JSON.stringify({
		"env" : envselected,
		"project" : projectselected,
		"workflow" : workflowselected,
		"build" : buildselected
	});
	return data;
}

function baselineObject(run_id) {

	var data = JSON.stringify({
		"env" : envselected,
		"run_id" : run_id
	});
	return data;
}


//Helper function to serialize all the form fields into a JSON string
function forRegionJson() {

	var data = JSON.stringify({
		"env" : envselected,
		"project" : projectselected,
		"build" :  buildselected
	});
	return data;
}





/*******************************************************************************
 * Cookie Functions*******************
 ******************************************************************************/

function setprojectcookie() {
	deleteCookie('Workflow')
	document.cookie = 'Project=' + $("#perf_projects").val() + ';expires='
			+ getCookieExpiryDate();
	// Set_Cookie('Project',$("#perf_projects").val());
}

function setBuildcookie() {
	deleteCookie('Build')
	document.cookie = 'Build=' + $("#perf_builds").val() + ';expires='
			+ getCookieExpiryDate();
	// Set_Cookie('Project',$("#perf_projects").val());
}

function setenvcookie() {
	deleteCookie('Project')
	deleteCookie('Workflow')
	document.cookie = 'Environment=' + $("#perf_env").val() + ';expires='
			+ getCookieExpiryDate();
	// Set_Cookie('Project',$("#perf_projects").val());
}

function setWorkflowcookie(linkclicked) {
	document.cookie = 'Workflow=' + linkclicked + ';expires='
			+ getCookieExpiryDate();
}

function setPagecookie(pageselected) {
	document.cookie = 'Page=' + pageselected + ';expires='
			+ getCookieExpiryDate();
}


function setRegioncookie() {
	deleteCookie('Region')
	document.cookie = 'Region=' + $("#perf_region").val() + ';expires='
			+ getCookieExpiryDate();
}



/*******************************************************************************
 * Generic Functions**********************
 ******************************************************************************/

function getCookieExpiryDate() {
	var expires = new Date();
	expires.setTime(expires.getTime() + 31536000000); // 1 year
	return expires.toUTCString();
}

function deleteCookie(name) {
	document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function getCookie(name) {
	var value = "; " + document.cookie;
	var parts = value.split("; " + name + "=");
	if (parts.length == 2)
		return parts.pop().split(";").shift();
}

function convertToDecimal(num) {
	var convertednum = parseFloat(Math.round(num * 100) / 100).toFixed(2);
	return convertednum;
}
