var projects = "/uiperformance/rest/project/projects";
var workflows = "/uiperformance/rest/project/pages";


var projectselected = getCookie("Project");
var envselected = getCookie("Environment");
var workflowselected = getCookie("Workflow");


function onPageLoad(perf_projects, perf_workflows) {
	// $('#perfpagename').text('Page Performance of ' + getCookie("Workflow"));
	if (envselected == undefined) {
		$('#perf_env').prepend(
				"<option selected disabled>Choose Project</option>");
	} else {
		$('#perf_env').val(envselected);
	}
}

// Load All the Projects
function loadProjects() {
	// console.log('Inside Load Projects....');
	$
			.ajax({
				type : 'GET',
				contentType : 'application/json',
				url : projects,
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					// console.log('Projects....' + data);
					for (i = 0; i < data.length; i++) {
						if (projectselected == undefined) {
							$('#perf_projects')
									.append(
											'<option selected disabled>Choose Project</option>')
						}

						if (data[i] == projectselected) {
							$('#perf_projects')
									.append(
											'<option selected>' + data[i]
													+ '</option>');
						} else {
							$('#perf_projects').append(
									'<option>' + data[i] + '</option>');
						}
					}
					perf_projects = data;
					loadProjectSpecificWorkflows(projectselected);
				}
			});
}

// Load All Workflows based on Project Selected
function loadProjectSpecificWorkflows(projectselected) {
	// console.log('Inside Workflows....');
	$
			.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : workflows,
				dataType : "json",
				data : projectselected,
				success : function(data, textStatus, jqXHR) {
					console.log('Pages Pertaining to ' + projectselected
							+ '....' + data);
					perf_workflows = data;

					for (k = 0; k < perf_workflows.length; k++) {
						$('#side-menu2')
								.append(
										'<li><style type="text/css">a:hover {cursor:pointer;}</style><b><font size="4"><a id='
												+ perf_workflows[k]
												+ ' onclick=setWorkflowcookie($(this).attr("id"));window.location.reload()><i class="fa fa-dashboard fa-fw"></i>'
												+ ' '
												+ perf_workflows[k].split(".")[4]
												+ '</a></b></font></li>');
					}

					loadRespectiveRegions();

					// If Its not defined show all on left side only
					if (workflowselected == undefined) {
						// onclick Method to be called
						// selectPerfFlowUS($(this).attr("id"));selectPerfFlowCA($(this).attr("id"));selectPerfFlowFR($(this).attr("id"));
					} else {
						selectPerfFlow(workflowselected);
						/*
						 * selectPerfFlowUS(workflowselected);
						 * selectPerfFlowCA(workflowselected);
						 * selectPerfFlowFR(workflowselected);
						 */
					}
				}
			});
}

// ==================================================================================================================================================================================

// Load all the respective regions
var regions = "/perfdashboard/rest/project/regions";

function loadRespectiveRegions() {
	// console.log('Inside Load Projects....');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : regions,
		dataType : "json",
		data : workflowToJson(),
		success : function(data, textStatus, jqXHR) {
			console.log('Regions---->' + data);
			listofregions = data;

			// populateTable(data);

			for ( var r in listofregions) {
				console.log('region----->' + listofregions[r]);
				selectPerfFlow(workflowselected, listofregions[r]);
			}
		}
	});
}

function selectPerfFlow(workflowselected, region) {
	// console.log('Inside Page Links....');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : regionbasedreport,
		dataType : "json",
		data : formToJSONTable(workflowselected, region),
		success : function(data) {
			console.log("selectPerfFlow------>" + data.toString());
			populateTable(data)
		}
	});
}

function populateTable(data) {
	var pageloadtime;
	var total10 = 0;
	var total30 = 0;
	var ref_id;

	var region = data[0]._source.region.toUpperCase();

	lastrun = convertToDecimal(data[0]._source.loadtime / 1000);
	ref_id = data[0]._source.ref;

	pageloadtime = lastrun
	speedindex = data[0]._source.speedindex

	console.log(pageloadtime + '======' + speedindex)

	/*
	 * if (region == 'US') { pageloadtime_us = lastrun speedindex_us =
	 * data[0]._source.speedindex
	 * 
	 * console.log(pageloadtime + '======' + speedindex_us) } else if (region ==
	 * 'CA') { pageloadtime_ca = lastrun speedindex_ca =
	 * data[0]._source.speedindex } if (region == 'FR') { pageloadtime_fr =
	 * lastrun speedindex_fr = data[0]._source.speedindex }
	 */

	// console.log("========Region========="+region+"============lastrun=========="+lastrun+"=========Ref_ID==============="+ref_id)
	loadVCData(region, ref_id, pageloadtime, speedindex);

	for (i = 0; i < data.length; i++) {
		total30 = data[i]._source.loadtime + total30;
	}
	avg30run = convertToDecimal((total30 / data.length) / 1000);

	if (data.length < 10) {
		avg10run = avg30run;
	} else {
		for (i = 0; i < 10; i++) {
			total10 = data[i]._source.loadtime + total10;
		}
		avg10run = convertToDecimal((total10 / 10) / 1000);
	}

	$('#dataTables-example').append(
			// region
			'<tr>' + '<td>' + region + '</td>' +
			// Last Run
			'<td>' + lastrun + ' seconds</td>' + '<td>' + avg10run
					+ ' seconds</td>' + '<td>' + avg30run + ' seconds</td>'
					// Graph'<td><div class=panel-body><div
					// id=morris-area-chart-'+region+'></div></div></td>'
					+ '</tr>');

}

// ==================================================================================================================================================================================

function loadVCData(Region, Ref_ID, pageloadtime, speedindex) {
	// console.log('Inside Projects....');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : visualcomplete,
		dataType : "json",
		data : form2JSON(Ref_ID),
		success : function(data, textStatus, jqXHR) {
			// console.log('Visual Comlete Data....' +
			// Region+"======="+Ref_ID+"Data Length"+data.length);

			fillAreaChart(Region, Ref_ID, getPercentageArray(Region, data),
					pageloadtime, speedindex);

			/*
			 * if (Region == 'US') { fillAreaChart('US', Ref_ID,
			 * getPercentageArray(Region, data)); } else if (Region == 'CA') {
			 * fillAreaChart('CA', Ref_ID, getPercentageArray(Region, data)); }
			 * else if (Region == 'FR') { fillAreaChart('FR', Ref_ID,
			 * getPercentageArray(Region, data)); }
			 */

		}
	});
}

/*
 * function selectPerfFlowUS(linkclicked) { // console.log('Inside Page
 * Links....'); $.ajax({ type : 'POST', contentType : 'application/json', url :
 * regionbasedreport, dataType : "json", data : formToJSONUS(linkclicked),
 * success : function(data) { // console.log(data); populateTable(data) } }); }
 * 
 * function selectPerfFlowCA(linkclicked) { // console.log('Inside Page
 * Links....'); $.ajax({ type : 'POST', contentType : 'application/json', url :
 * regionbasedreport, dataType : "json", data : formToJSONCA(linkclicked),
 * success : function(data) { // console.log(data); populateTable(data) } }); }
 * 
 * function selectPerfFlowFR(linkclicked) { // console.log('Inside Page
 * Links....'); $.ajax({ type : 'POST', contentType : 'application/json', url :
 * regionbasedreport, dataType : "json", data : formToJSONFR(linkclicked),
 * success : function(data) { // console.log(data); populateTable(data) } }); }
 * 
 */

// Helper function to serialize all the form fields into a JSON string
function form2JSON(ref_id) {
	// console.log('json string print ');
	var data = JSON.stringify({
		"ref_id" : ref_id,
		"env" : envselected
	});
	// console.log('----->' + data);
	return data;
}

/*
 * // Helper function to serialize all the form fields into a JSON string
 * function formToJSONUS(linkclicked) { // console.log('json string print ');
 * var data = JSON.stringify({ "page" : linkclicked, "region" : "US",
 * "resources" : "30", "env" : envselected }); // console.log('----->' + data);
 * return data; }
 *  // Helper function to serialize all the form fields into a JSON string
 * function formToJSONCA(linkclicked) { // console.log('json string print ');
 * var data = JSON.stringify({ "page" : linkclicked, "region" : "CA",
 * "resources" : "30", "env" : envselected }); // console.log('----->' + data);
 * return data; }
 *  // Helper function to serialize all the form fields into a JSON string
 * function formToJSONFR(linkclicked) { // console.log('json string print ');
 * var data = JSON.stringify({ "page" : linkclicked, "region" : "FR",
 * "resources" : "30", "env" : envselected }); // console.log('----->' + data);
 * return data; }
 */

// Helper function to serialize all the form fields into a JSON string
function formToJSONTable(workflowselected, region) {
	// console.log('json string print ');
	var data = JSON.stringify({
		"page" : workflowselected,
		"region" : region,
		"resources" : "30",
		"env" : envselected
	});
	// console.log('----->' + data);
	return data;
}

function getPercentageArray(region, data) {
	console.log("Region====" + region + "====data====" + data.length);
	var percentVsTime = {};
	for (i = 0; i < data.length; i++) {
		console.log(data[i]._source.time + "==============================="
				+ data[i]._source.percentagecompletion)

		// console.log('Percentage Comleted---->'+
		// data[i]._source.percentagecompletion);
		// percentVsTime[data[i]._source.time] =
		// data[i]._source.percentagecompletion;
		percentVsTime[data[i]._source.timestampinmillisecond] = data[i]._source.percentagecompletion;

	}

	var result = "["

	for ( var i in percentVsTime) {
		result = result.concat('{"percentage":' + percentVsTime[i] + ',"time":'
				+ i + '},')
	}
	result = result.replace(/,\s*$/, '');
	result = result.concat("]");

	return result;
}

function convertMapToJSON(percentVsTime) {

	var result = "["

	for ( var i in percentVsTime) {
		result = result.concat('{percentage:' + percentVsTime[i] + ',time:' + i
				+ '},')
	}
	result = result.replace(/,\s*$/, '');
	result = result.concat("]");

	return result;

}

function fillAreaChart(Region, Ref_Id, vcData, pageloadtime, speedindex) {

	// var result=convertMapToJSON(percentVsTime);
	var result = vcData;
	console.log("Ref_ID===" + Ref_Id + "=====Result=====" + result)
	var json = JSON.parse(result)

	var si = speedindex;
	var pl = pageloadtime;

	/*
	 * if (Region == 'US') { si = speedindex_us pl = pageloadtime_us } else if
	 * (Region == 'CA') { si = speedindex_ca pl = pageloadtime_ca } else if
	 * (Region == 'FR') { si = speedindex_fr pl = pageloadtime_fr }
	 */

	var generateHere = document.getElementById("vc-graphs");

	var fragment = create('<div class="col-lg-6"><a class="region-'
			+ Region
			+ ' href="subresourcedetails.html"><div class="well"><label>'
			+ Region
			+ ' Region</label><div class="panel-body"><div id="morris-area-chart-'
			+ Region + '"></div></div></div></a></div>');

	generateHere.appendChild(fragment);

	// With jquery
	// $('a.region-'+Region).attr('id', Ref_Id);
	$('a.region-' + Region)
			.attr(
					'href',
					'subresourcedetails.html?ref=' + Ref_Id + '&si=' + si
							+ '&pl=' + pl);
	Morris.Area({
		dataType : 'JSON',
		element : 'morris-area-chart-' + Region,
		data : json,
		xkey : 'time',
		ykeys : [ 'percentage' ],
		labels : [ 'percentage', 'time' ],
		pointSize : 5,
		// hideHover : 'auto',
		resize : true
	});
}

/*******************************************************************************
 * Json Helper Functions*******************
 ******************************************************************************/

// Helper function to serialize all the form fields into a JSON string
function workflowToJson() {
	// console.log('json string print ');
	var data = JSON.stringify({
		"page" : workflowselected,
		"project" : projectselected,
		"env" : envselected
	});
	console.log('----->' + data);
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

function create(htmlStr) {
	var frag = document.createDocumentFragment(), temp = document
			.createElement('div');
	temp.innerHTML = htmlStr;
	while (temp.firstChild) {
		frag.appendChild(temp.firstChild);
	}
	return frag;
}