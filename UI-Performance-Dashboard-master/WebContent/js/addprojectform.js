var validaterepo = "/uiperformance/rest/jgit/validaterepo";
var addProject="/uiperformance/rest/project/add";
var clonerepo = "/uiperformance/rest/jgit/clone";

$("#gitrepo").focusout(function() {
	console.log("Focusing out !!!" + $(this).text());
	validateGitRepo();
});

$("#addProject").click(function() {
	console.log('Submitting form to add project');
	insertProject();
	/*.done(function () {
        console.log("COMPLETED !!!")
        location.href = "homepage.html"
    }); */
});


function validateGitRepo() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : validaterepo,
		dataType : "json",
		data : formToJSON(),
		success : function(data, textStatus, jqXHR) {
			if (data.isValid) {
				$('.GitRepoError').hide();
				$("#gitdiv").removeClass("has-warning");
				$("#gitdiv").removeClass("has-error");
				$("#gitdiv").addClass("has-success");

			} else {
				$("#gitdiv").removeClass("has-warning");
				$("#gitdiv").removeClass("has-success");
				$("#gitdiv").addClass("has-error");
				$('.GitRepoError').append(data.message);
				$('.GitRepoError').show();
			}
		}
	});
}

function cloneGitRepo() {
	console.log('Inside Cloning Git repo....');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : clonerepo,
		dataType : "json",
		data : cloneGitRepoJSON(),
		success : function(data, textStatus, jqXHR) {
			console.log('output======>' + JSON.stringify(data))
			sleep(60000);
		}
	});
}



function insertProject() {
	console.log('Insertion of new Project details....');
	$.ajax({
		type : 'POST',
		async: false,
		contentType : 'application/json',
		url : addProject,
		dataType : "json",
		data : addProjectJSON(),
		success : function(data) {
			window.location.href = 'index.html';
		}
	});
}


// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
	var data = JSON.stringify({
		"gitRepo" : $('#gitrepo').val()
	});
	return data;
}


function addProjectJSON() {
	console.log('json string print ');
	var data = JSON.stringify({
	"project_name" : $("#project_name").val(),
	"project_git_repo" : $('#gitrepo').val(),
	"project_testng_file":$("#project_testng_files").val(),
	"project_execution_schedule" : $("#project_schedule").val(),
	"project_admin_email" : $("#project_admin_mailid").val(),
	"project_region" : $("#project_region").val(),
	});
	console.log('----->' + data);
	return data;
}



function cloneGitRepoJSON() {
	console.log('json string print ');
	var data = JSON.stringify({
		"gitRepo" : $('#gitrepo').val(),
		"localRepoPath" : "C://temp8"
	});
	console.log('----->' + data);
	return data;
}

jQuery.ajaxSetup({
	beforeSend : function() {
		$('.loader').show();
	},
	complete : function() {
		$('.loader').hide();
	},
	success : function() {
	}
});




function sleep(milliseconds) {
	var start = new Date().getTime();
	for (var i = 0; i < 1e7; i++) {
		if ((new Date().getTime() - start) > milliseconds) {
			break;
		}
	}
}