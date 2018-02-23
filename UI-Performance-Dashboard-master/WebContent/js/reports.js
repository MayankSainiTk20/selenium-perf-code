//Endpoints
var projects = "/uiperformance/rest/project/projects";
var workflows = "/uiperformance/rest/project/workflows";
var pages = "/uiperformance/rest/project/pages";
var tpdata = "/uiperformance/rest/project/tpdata";
var builds = "/uiperformance/rest/project/builds";
var regions = "/uiperformance/rest/project/regions";

// Cookies
var regionselected = getCookie("Region");
var projectselected = getCookie("Project");
var envselected = getCookie("Environment");
var workflowselected = getCookie("Workflow");
var pageselected = getCookie("Page");
var buildselected = getCookie("Build");

// Page Load function
function onPageLoad(perf_projects, perf_workflows) {

	try {
		if (envselected == undefined) {
			$('#perf_env').prepend(
					"<option selected disabled>Choose Environment</option>");
		} else {
			$('#perf_env').val(envselected);
		}

		if (buildselected === undefined) {
			$('#perf_builds').prepend(
					'<option selected disabled>Choose Build</option>')
		} else {
			$('#perf_builds').val(buildselected);
		}

		if (regionselected === undefined) {
			$('#perf_region').prepend(
					'<option selected disabled>Choose Region</option>')
		} else {
			$('#perf_region').val(regionselected);
		}

		document.getElementById('dy_workflow').href = "allworkflows.html?workflow="
				+ workflowselected;

	} catch (err) {
		console.log(err)
	} finally {
		populateTablewithPageData();
	}
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

// Load All Regions
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
				} else {
					$('#perf_region')
							.append('<option>' + data[q] + '</option>');
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
				url : pages,
				dataType : "json",
				data : workflowToJson(),
				success : function(data) {
					var fontcolor;
					console.log("---------------" + data)
					for (key in data) {
						console.log('REF : ' + data[key].ref_id)

						if (Math.floor(data[key].last30run / data[key].count) > data[key].baseline_loadtime) {
							fontcolor = "red";
						} else {
							fontcolor = "#26b99a";
						}

						$('#datatable-responsive-pagebased')
								.append(
										'<tr role="row"}>'
										// Page
										+ '<td><b>'
												+ key.split(".")[4]
												+ '</b></td>'
												+

												/*******************************
												 * // Speed Index '
												 * <td>' + data[key].last10si + '</td>' + //
												 * Page load time '
												 * <td>' + data[key].last10run + '</td>' + //
												 * Speed Index
												 ******************************/

												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#2A3F54"; href=lastexecutions.html'
												+ ' onclick=setPagecookie($(this).attr("id"));window.location.reload()>'
												+ Math.floor(data[key].last30si
														/ data[key].count)
												+ '</a></b></i></td>'
												+
												// Page load time
												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:'
												+ fontcolor
												+ '"; href=lastexecutions.html'
												+ ' onclick=setPagecookie($(this).attr("id"));window.location.reload()>'
												+ Math
														.floor(data[key].last30run
																/ data[key].count)
												+ '</a></b></i></td>'
												+

												// DOM Count
												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#2A3F54"; href=lastexecutions.html'
												+ ' onclick=setPagecookie($(this).attr("id"));window.location.reload()>'
												+ Math
														.floor(data[key].last30_domCount
																/ data[key].count)
												+ '</a></b></i></td>'
												+

												// Shell Load Time
												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#2A3F54"; href=lastexecutions.html'
												+ ' onclick=setPagecookie($(this).attr("id"));window.location.reload()>'
												+ Math
														.floor(data[key].last30_shellLoadTime
																/ data[key].count)
												+ '</a></b></i></td>'
												+

												// First Byte
												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#2A3F54"; href=lastexecutions.html'
												+ ' onclick=setPagecookie($(this).attr("id"));window.location.reload()>'
												+ Math
														.floor(data[key].last30_firstByte
																/ data[key].count)
												+ '</a></b></i></td>'
												+

												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#2A3F54"; href=lastexecutions.html'
												+ ' onclick=setPagecookie($(this).attr("id"));window.location.reload()>'
												+

												Math.floor(data[key].last30rum
														/ data[key].count)
												+ '</a></b></i></td>'
												+

												/*******************************
												 * '
												 * <td>' + data[key].last30si + '</td>' + //
												 * Page load time '
												 * <td>' + data[key].last30run + '</td>' +
												 ******************************/

												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#2A3F54";href="#">'
												+ data[key].baseline_si
												+ '</a></b></i></td>' +
												// Page load time
												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#2A3F54">'
												+ data[key].baseline_loadtime
												+ '</a></b></i></td>' +

												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#2A3F54">'
												+ data[key].baseline_build
												+ '</a></b></i></td>' +

												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#2A3F54">' +

												data[key].baseline_rum
												+ '</a></b></i></td>' +

												+'</tr>');

						populateTablewithTPData(key);

						$('#datatable-responsive-pagebased-lastrun')
								.append(
										'<tr role="row">'
										// Page
										+ '<td><b>'
												+ key.split(".")[4]
												+ '</b></td>'
												+
												// Speed Index
												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#26b99a"; href=subresourcedetails.html?ref='
												+ data[key].ref_id
												+ '&si='
												+ data[key].lastrunsi
												+ '&pl='
												+ data[key].lastruntime
												+ ' onclick=setPagecookie($(this).attr("id"));window.location.reload()>'
												+ data[key].lastrunsi
												+ '</a></b></i></td>'
												+
												// Page load time
												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#26b99a"; href=subresourcedetails.html?ref='
												+ data[key].ref_id
												+ '&si='
												+ data[key].lastrunsi
												+ '&pl='
												+ data[key].lastruntime
												+ ' onclick=setPagecookie($(this).attr("id"));window.location.reload()>'
												+ data[key].lastruntime
												+ '</a></b></i></td>'
												+

												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#26b99a"; href=subresourcedetails.html?ref='
												+ data[key].ref_id
												+ '&si='
												+ data[key].lastrunsi
												+ '&pl='
												+ data[key].lastruntime
												+ ' onclick=setPagecookie($(this).attr("id"));window.location.reload()>'
												+ data[key].lastrun_FirstByte
												+ '</a></b></i></td>'
												+

												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#26b99a"; href=subresourcedetails.html?ref='
												+ data[key].ref_id
												+ '&si='
												+ data[key].lastrunsi
												+ '&pl='
												+ data[key].lastruntime
												+ ' onclick=setPagecookie($(this).attr("id"));window.location.reload()>'
												+ data[key].lastrun_DOMCount
												+ '</a></b></i></td>'
												+

												'<td><i><b><a id='
												+ key.split(".")[4]
												+ ' style="color:#26b99a"; href=subresourcedetails.html?ref='
												+ data[key].ref_id
												+ '&si='
												+ data[key].lastrunsi
												+ '&pl='
												+ data[key].lastruntime
												+ ' onclick=setPagecookie($(this).attr("id"));window.location.reload()>'
												+

												data[key].lastrunrum
												+ '</a></b></i></td>');
					}

				}
			});
}

// Load All Pages based on Project Selected
function populateTablewithTPData(page) {
	$
			.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : tpdata,
				dataType : "json",
				data : dataToTP(page),
				success : function(data, textStatus, jqXHR) {

					var pagename = page.split(".")[4]

					// Append Header
					$('#tpdata')
							.append(

									'<div class="col-md-12">'
											+ '			<div class="x_panel">'
											+ '					<div class="x_title">'
											+ '						<h2>'
											+ pagename
											+ ' TP Stats'
											+ '						</h2>'
											+ '						<ul class="nav navbar-right panel_toolbox">'
											+ '							<li><a class="collapse-link"><i'
											+ '									class="fa fa-chevron-up"></i></a></li>'
											+ '							<li><a class="close-link"><i class="fa fa-close"></i></a>'
											+ '							</li>'
											+ '						</ul>'
											+ '						<div class="clearfix"></div>'
											+ '					</div>'
											+ '					<div class="x_content">'
											+ '						<div class="row">'
											+ '<div class="col-md-6">'
											+ '<div id="graph_bar_'
											+ pagename
											+ '"style="width: 100%; height: 280px;"></div>'
											+ '</div>'
											+ '							<div class="col-md-6">'
											+ '								<table id="datatable-responsive-tpdata"'
											+ '									class="table table-striped table-bordered dt-responsive nowrap dataTable no-footer dtr-inline"'
											+ '									cellspacing="0" width="100%" role="grid"'
											+ '									aria-describedby="datatable-responsive_info"'
											+ '									style="width: 100%;">'
											+ '									<thead>'
											+ '										<tr role="row">'
											+ '											<th class="sorting_asc" tabindex="0"'
											+ '												aria-controls="datatable-responsive" rowspan="1"'
											+ '												colspan="1" style="width: 100px;" aria-sort="ascending"'
											+ '												aria-label="First name: activate to sort column descending"> TP #</th>'
											+ '											<th class="sorting" tabindex="0"'
											+ '												aria-controls="datatable-responsive" rowspan="1"'
											+ '												colspan="1" style="width: 100px;"'
											+ '												aria-label="Last name: activate to sort column ascending">Time (Milliseconds)</th></tr></thead>'
											+

											// Actual Data
											'<tr role="row">'
											+
											// 1
											'<td><b> Minimum </b></td>'
											+ '<td><b>'
											+ parseInt(data.one)
											+ '</b></td>'
											+ '</tr>'
											+ '<tr>'
											+
											// 5
											'<td><b> 5th Percentile </b></td>'
											+ '<td><b>'
											+ parseInt(data.five)
											+ '</b></td>'
											+ '</tr>'
											+ '<tr>'
											+
											// 25
											'<td><b> 25th Percentile </b></td>'
											+ '<td><b>'
											+ parseInt(data.twentyfive)
											+ '</b></td>'
											+ '</tr>'
											+ '<tr>'
											+
											// 50
											'<td><b> 50th Percentile </b></td>'
											+ '<td><b>'
											+ parseInt(data.fifty)
											+ '</b></td>'
											+ '</tr>'
											+ '<tr>'
											+
											// 75
											'<td><b> 75th Percentile </b></td>'
											+ '<td><b>'
											+ parseInt(data.seventyfive)
											+ '</b></td>'
											+ '</tr>'
											+ '<tr>'
											+
											// 95
											'<td><b> 95th Percentile </b></td>'
											+ '<td><b>'
											+ parseInt(data.nintyfive)
											+ '</b></td>'
											+ '</tr>'
											+ '<tr>'
											+
											// 99
											'<td><b> 99th Percentile </b></td>'
											+ '<td><b>'
											+ parseInt(data.nintynine)
											+ '</b></td>'
											+ '</tr>'
											+ '</table></div></div></div></div></div>')

					$(document).ready(
							function() {
								Morris.Bar({
									element : 'graph_bar_' + pagename + '',
									data : [ {
										device : 'Minimum',
										geekbench : parseInt(data.one)
									}, {
										device : '5th',
										geekbench : parseInt(data.five)
									}, {
										device : '25th',
										geekbench : parseInt(data.twentyfive)
									}, {
										device : '50th',
										geekbench : parseInt(data.fifty)
									}, {
										device : '75th',
										geekbench : parseInt(data.seventyfive)
									}, {
										device : '95th',
										geekbench : parseInt(data.nintyfive)
									}, {
										device : '99th',
										geekbench : parseInt(data.nintynine)
									} ],
									xkey : 'device',
									ykeys : [ 'geekbench' ],
									labels : [ 'Time' ],
									barRatio : 0.4,
									barColors : [ '#26B99A', '#34495E',
											'#ACADAC', '#3498DB' ],
									xLabelAngle : 35,
									hideHover : 'auto',
									resize : true
								});

								$MENU_TOGGLE.on('click', function() {
									$(window).resize();
								});
							});

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

	/*
	 * if (region == 'US') { pageloadtime_us = lastrun speedindex_us =
	 * data[0]._source.speedindex
	 * 
	 * console.log(pageloadtime + '======' + speedindex_us) } else if (region ==
	 * 'CA') { pageloadtime_ca = lastrun speedindex_ca =
	 * data[0]._source.speedindex } if (region == 'FR') { pageloadtime_fr =
	 * lastrun speedindex_fr = data[0]._source.speedindex }
	 */

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

/*******************************************************************************
 * Json Helper Functions*******************
 ******************************************************************************/

// Helper function to serialize all the form fields into a JSON string
function workflowToJson() {

	var data = JSON.stringify({
		"workflow" : workflowselected,
		"project" : projectselected,
		"env" : envselected,
		"build" : buildselected,
		"region" : regionselected
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
		"build" : buildselected
	});
	return data;
}

function dataToTP(page) {
	var data = JSON.stringify({
		"env" : envselected,
		"page" : page
	});

	return data;
}

/*******************************************************************************
 * Cookie Functions*******************
 ******************************************************************************/

function setprojectcookie() {
	deleteCookie('Workflow')
	deleteCookie('Build')
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
	deleteCookie('Build')
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

function create(htmlStr) {
	var frag = document.createDocumentFragment(), temp = document
			.createElement('div');
	temp.innerHTML = htmlStr;
	while (temp.firstChild) {
		frag.appendChild(temp.firstChild);
	}
	return frag;
}