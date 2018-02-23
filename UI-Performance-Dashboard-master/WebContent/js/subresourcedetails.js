var visualcomplete = "/uiperformance/rest/project/vcpercent";
var subresources = "/uiperformance/rest/project/subresources";
var perfstats = "/uiperformance/rest/project/perfstats";
var regions = "/uiperformance/rest/project/regions";

//Cookies
var regionselected = getCookie("Region");
var envselected = getCookie("Environment");
var projectselected = getCookie("Project");
var workflowselected = getCookie("Workflow");

function loadVC2Data(Ref_ID) {
	// console.log('Inside Projects....');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : visualcomplete,
		dataType : "json",
		data : form2JSON(Ref_ID),
		success : function(data, textStatus, jqXHR) {
			fillAreaChart(getPercentageArray(data));
			PerfStats(Ref_ID);
		}
	});
}

function PerfStats(Ref_ID) {
	// console.log('Inside Projects....');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : perfstats,
		dataType : "json",
		data : form2JSON(Ref_ID),
		success : function(data, textStatus, jqXHR) {
			console.log('Visual Comlete Data....' + data.toString());
			if (data.length > 0) {
				for (i = 0; i < data.length; i++) {
					$('#perfstats_table').append(
							"<tr>" + "<td>" + data[i]._source.url + "</td>"
									+ "<td>" + data[i]._source.networkTime
									+ "</td>"
									+ "<td>" + data[i]._source.requestTime
									+ "</td>" + "<td>"
									+ data[i]._source.concurrentRequests
									+ "</td>" + "<td>" + data[i]._source.dbtime
									+ "</td>" + "<td>"
									+ data[i]._source.dbactions + "</td>"
									+ "<td>" + data[i]._source.xmitTime
									+ "</td>" + "<td>"
									+ data[i]._source.xmitSize + "</td>"
									+ "<td>" + data[i]._source.dbqueries
									+ "</td>" + "<td>"
									+ data[i]._source.dbqueryTime + "</td>"
									+ "<td>" + data[i]._source.dbnextCalls
									+ "</td>" +

									"<td>" + data[i]._source.dbgetTime
									+ "</td>" + "<td>"
									+ data[i]._source.dbpreparedQueries
									+ "</td>" + "<td>"
									+ data[i]._source.dbconnectionCalls
									+ "</td>" + "<td>"
									+ data[i]._source.otherConcurrentThreads
									+ "</td>" + "<td>"
									+ data[i]._source.dbconnectionTime
									+ "</td>" + "<td>"
									+ data[i]._source.companyIdT + "</td>"
									+ "<td>" + data[i]._source.authIDT
									+ "</td>" + "<td>"
									+ data[i]._source.runtimeFreeMemory
									+ "</td>" +

									"<td>" + data[i]._source.intuit_tid
									+ "</td>" + "<td>"
									+ data[i]._source.qboBuildNum + "</td>" +

									"</tr>");

				}
			} else {
				document.getElementById("dataTables-perfstats").remove();
				var perfstats_content = document
						.getElementById('perfstats-content');

				perfstats_content.innerHTML = perfstats_content.innerHTML
						+ 'No Performance Stats Data to Display';
			}
			
			
			
			$(document).ready(function() {
			    $('#dataTables-perfstats').DataTable( {
			        "scrollX": true
			    } );
			} );
		}
	});
}




//Load All Regions
function loadRegions() {
	
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : regions,
		data : projectselectedToJson(),
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




function getPercentageArray(data) {

	var percentVsTime = {};
	for (i = 0; i < data.length; i++) {
		// console.log(data[i]._source.time+"==============================="+data[i]._source.percentagecompletion)
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

function setprojectcookie() {
	deleteCookie('Workflow')
	document.cookie = 'Project=' + $("#perf_projects").val() + ';expires='
			+ getCookieExpiryDate();
	// Set_Cookie('Project',$("#perf_projects").val());
}


function setRegioncookie() {
	deleteCookie('Region')
	document.cookie = 'Region=' + $("#perf_region").val() + ';expires='
			+ getCookieExpiryDate();
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

// Helper function to serialize all the form fields into a JSON string
function form2JSON(ref_id) {
	// console.log('json string print ');
	var data = JSON.stringify({
		"ref_id" : ref_id,
		"env" : envselected
	});
	console.log('PerfStats----->' + data);
	return data;
}

// Helper function to serialize all the form fields into a JSON string
function form2JSONSubResources(ref_id) {
	// console.log('json string print ');
	var data = JSON.stringify({
		"ref_id" : ref_id,
		"env" : envselected
	});
	// console.log('----->' + data);
	return data;
}

var ref_id;

window.onload = loadSubResources;

var totalResources;
var totalTimeTaken;

var totalCSS;
var totalJS;
var totalIMG;
var totalOther;

var avgCSSDuration;
var avgIMGDuration;
var avgJSDuration;
var avgOtherDuration;

var speedIndex;

function getParameterByName(name) {
	var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
	// console.log(match && decodeURIComponent(match[1].replace(/\+/g, ' ')));
	return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
}

function loadSubResources() {
	// console.log('Inside Workflows....');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : subresources,
		dataType : "json",
		data : form2JSONSubResources(getParameterByName('ref')),
		success : function(data, textStatus, jqXHR) {
			// console.log('Subresources....' + data);
			loadDataTables(data)
		}
	});
}

function doCalculations(data) {
	totalResources = data.length;
	timeTaken = 0;
	totalCSSCount = 0;
	totalIMGCount = 0
	totalJSCount = 0;
	totalOtherCount = 0;

	totalCSSDuration = 0;
	totalIMGDuration = 0;
	totalJSDuration = 0;
	totalOtherDuration = 0;

	for (i = 0; i < data.length; i++) {

		if (identifyResourceType(data[i]._source.name) == "js") {
			totalJSCount = totalJSCount + 1;
			totalJSDuration = data[i]._source.duration + totalJSDuration;
		} else if (identifyResourceType(data[i]._source.name) == "css") {
			totalCSSCount = totalCSSCount + 1;
			totalCSSDuration = data[i]._source.duration + totalCSSDuration;
		} else if ((identifyResourceType(data[i]._source.name) == "gif")
				|| (identifyResourceType(data[i]._source.name) == "jpeg")
				|| (identifyResourceType(data[i]._source.name) == "png")
				|| (identifyResourceType(data[i]._source.name) == "svg")
				|| (identifyResourceType(data[i]._source.name) == "jpg")
				|| (identifyResourceType(data[i]._source.name) == "bmp")) {
			totalIMGCount = totalIMGCount + 1;
			totalIMGDuration = data[i]._source.duration + totalIMGDuration;
		} else {
			totalOtherCount = totalOtherCount + 1;
			totalOtherDuration = data[i]._source.duration + totalOtherDuration;
		}
		timeTaken = data[i]._source.duration + timeTaken;
	}

	totalCSS = totalCSSCount;
	avgCSSDuration = parseInt(totalCSSDuration, 10);

	totalJS = totalJSCount;
	avgJSDuration = parseInt(totalJSDuration, 10);

	totalIMG = totalIMGCount;
	avgIMGDuration = parseInt(totalIMGDuration, 10);

	totalOther = totalOtherCount;
	avgOtherDuration = parseInt(totalOtherDuration, 10);

	// totalTimeTaken = timeTaken / totalResources;
}

function identifyResourceType(fileName) {
	// console.log(fileName+"=========="+fileName.split('.').pop())
	return fileName.split('.').pop();
}

function loadDataTables(data) {

	var REF_ID = getParameterByName('ref');
	$('a.donut-details').attr(
			'href',
			'http://pppdc9prd743.corp.intuit.net:9200/_plugin/head/qbowaterfall.html?ref='
					+ REF_ID);

	doCalculations(data)

	$('tr.speed_index').append(
			'<td style="color:#26b99a";><b>' + getParameterByName('si')
					+ '</b></td>');
	$('tr.speed_index').append('<td><b>-</b></td>');

	$('tr.total_resources').append(
			'<td style="color:#2A3F54";><b>' + getParameterByName('pl')
					+ ' milliseconds</b></td>');
	$('tr.total_resources').append(
			'<td style="color:#2A3F54";><b>' + totalResources + '</b></td>');

	$('tr.total_js').append(
			'<td style="color:#26b99a";><b>' + avgJSDuration
					+ ' milliseconds</b></td>');
	$('tr.total_js').append(
			'<td style="color:#26b99a";><b>' + totalJSCount + '</b></td>');

	$('tr.total_css').append(
			'<td style="color:#26b99a";><b>' + avgCSSDuration
					+ ' milliseconds</b></td>');
	$('tr.total_css').append(
			'<td style="color:#26b99a";><b>' + totalCSSCount + '</b></td>');

	$('tr.total_img').append(
			'<td style="color:#26b99a";><b>' + avgIMGDuration
					+ ' milliseconds</b></td>');
	$('tr.total_img').append(
			'<td style="color:#26b99a";><b>' + totalIMGCount + '</b></td>');

	$('tr.total_other').append(
			'<td style="color:#26b99a";><b>' + avgOtherDuration
					+ ' milliseconds</b></td>');
	$('tr.total_other').append(
			'<td style="color:#26b99a";><b>' + totalOtherCount + '</b></td>');

	fillDonutChart()
	loadVC2Data(REF_ID);

}

function fillDonutChart() {
	Morris.Donut({
		element : 'morris-donut-chart',
		data : [ {
			label : "JavaScript",
			value : totalJSCount
		}, {
			label : "CSS",
			value : totalCSSCount

		}, {
			label : "Image",
			value : totalIMGCount
		}, {
			label : "Others",
			value : totalOtherCount
		} ],
		resize : true
	});

}

function fillAreaChart(vcData) {

	var result = vcData;
	var json = JSON.parse(result)
	Morris.Area({
		dataType : 'JSON',
		element : 'morris-area-chart',
		data : json,
		xkey : [ 'time' ],
		ykeys : [ 'percentage' ],
		labels : [ 'percentage', 'time' ],
		pointSize : 5,
		hideHover : 'auto',
		resize : true
	});
}


//Helper function to serialize all the form fields into a JSON string
function projectselectedToJson() {

	var data = JSON.stringify({
		"env" : envselected,
		"project" : projectselected,
		"build" :  buildselected
	});
	return data;
}
