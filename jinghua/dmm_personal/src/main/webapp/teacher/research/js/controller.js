var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','researchService','dialog',
    function(scope,researchService,dialog){
    	dialog.showLoading();
    	researchService.getResearchCounts().then(function(data){
    		dialog.hideLoading();
            console.log(data);
            scope.thesisCounts = data.thesisCounts;
	        scope.projectCounts = data.projectCounts;
	        scope.workCounts = data.workCounts;
	        scope.patentCounts = data.patentCounts;
	        scope.outcomeCounts = data.outcomeCounts;
	        scope.softCounts = data.softCounts;
        });
}]).controller("projectcontroller",['$scope','researchService','dialog',
    function(scope,researchService,dialog){
    	dialog.showLoading();
    	var type = "";
    	researchService.getProjectCounts().then(function(data){
    		dialog.hideLoading();
    		scope.projectCounts = data.projectCounts;
    		scope.nationalProject = data.nationalProject;
    		scope.inquestsProject = data.inquestsProject;
    		scope.leaderProject = data.leaderProject;
        });
        researchService.getProjects(type).then(function(data){
        	dialog.hideLoading();
            scope.projects = data;
        });
        scope.getAll = function(){
    		$("#gj").removeClass("jzhi-menu-hover");
    		$("#zy").removeClass("jzhi-menu-hover");
    		$("#zd").removeClass("jzhi-menu-hover");
    		$("#sy").addClass("jzhi-menu-hover");
    		type = "";
    		researchService.getProjects(type).then(function(data){
	            scope.projects = data;
	        });
        }
    	scope.getNational = function(){
    		$("#sy").removeClass("jzhi-menu-hover");
    		$("#zy").removeClass("jzhi-menu-hover");
    		$("#zd").removeClass("jzhi-menu-hover");
    		$("#gj").addClass("jzhi-menu-hover");
    		type = "national";
    		researchService.getProjects(type).then(function(data){
	            scope.projects = data;
	        });
        }
        scope.getLeader = function(){
        	$("#sy").removeClass("jzhi-menu-hover");
        	$("#zy").removeClass("jzhi-menu-hover");
    		$("#gj").removeClass("jzhi-menu-hover");
    		$("#zd").addClass("jzhi-menu-hover");
        	type = "leader";
        	researchService.getProjects(type).then(function(data){
	            scope.projects = data;
	        });
    	}
    	scope.getInquest = function(){
    		$("#sy").removeClass("jzhi-menu-hover");
    		$("#gj").removeClass("jzhi-menu-hover");
    		$("#zd").removeClass("jzhi-menu-hover");
    		$("#zy").addClass("jzhi-menu-hover");
        	type = "inquest";
        	researchService.getProjects(type).then(function(data){
	            scope.projects = data;
	        });
    	}
        
}]).controller("thesiscontroller",['$scope','researchService','dialog',
    function(scope,researchService,dialog){
    	dialog.showLoading();
    	var type = "publisher";
    	$("#awardtable").hide();
    	$("#intable").hide();
    	$("#meetingtable").hide();
    	$("#reshiptable").hide();
    	scope.getPublisher = function(){
    		$("#hj").removeClass("jzhi-menu-hover");
    		$("#qk").removeClass("jzhi-menu-hover");
    		$("#hy").removeClass("jzhi-menu-hover");
    		$("#zz").removeClass("jzhi-menu-hover");
    		$("#fb").addClass("jzhi-menu-hover");
    		$("#awardtable").hide();
	    	$("#intable").hide();
	    	$("#meetingtable").hide();
	    	$("#reshiptable").hide();
    		$("#publishertable").show();
    		type = "publisher";
    		researchService.getThesises(type).then(function(data){
	            scope.thesises = data;
	        });
    	}
    	scope.getAward = function(){
    		$("#fb").removeClass("jzhi-menu-hover");
    		$("#qk").removeClass("jzhi-menu-hover");
    		$("#hy").removeClass("jzhi-menu-hover");
    		$("#zz").removeClass("jzhi-menu-hover");
    		$("#hj").addClass("jzhi-menu-hover");
    		$("#publishertable").hide();
	    	$("#intable").hide();
	    	$("#meetingtable").hide();
	    	$("#reshiptable").hide();
    		$("#awardtable").show();
    		type = "award";
    		researchService.getThesises(type).then(function(data){
	            scope.thesises = data;
	        });
    	}
    	scope.getIn = function(){
    		$("#hj").removeClass("jzhi-menu-hover");
    		$("#fb").removeClass("jzhi-menu-hover");
    		$("#hy").removeClass("jzhi-menu-hover");
    		$("#zz").removeClass("jzhi-menu-hover");
    		$("#qk").addClass("jzhi-menu-hover");
    		$("#publishertable").hide();
	    	$("#awardtable").hide();
	    	$("#meetingtable").hide();
	    	$("#reshiptable").hide();
    		$("#intable").show();
    		type = "in";
    		researchService.getThesises(type).then(function(data){
	            scope.thesises = data;
	        });
    	}
    	scope.getMeeting = function(){
    		$("#hj").removeClass("jzhi-menu-hover");
    		$("#qk").removeClass("jzhi-menu-hover");
    		$("#fb").removeClass("jzhi-menu-hover");
    		$("#zz").removeClass("jzhi-menu-hover");
    		$("#hy").addClass("jzhi-menu-hover");
    		$("#publishertable").hide();
	    	$("#awardtable").hide();
	    	$("#intable").hide();
	    	$("#reshiptable").hide();
    		$("#meetingtable").show();
    		type = "meeting";
    		researchService.getThesises(type).then(function(data){
	            scope.thesises = data;
	        });
    	}
    	scope.getReship = function(){
    		$("#hj").removeClass("jzhi-menu-hover");
    		$("#qk").removeClass("jzhi-menu-hover");
    		$("#hy").removeClass("jzhi-menu-hover");
    		$("#fb").removeClass("jzhi-menu-hover");
    		$("#zz").addClass("jzhi-menu-hover");
    		$("#publishertable").hide();
	    	$("#awardtable").hide();
	    	$("#intable").hide();
	    	$("#meetingtable").hide();
    		$("#reshiptable").show();
    		type = "reship";
    		researchService.getThesises(type).then(function(data){
	            scope.thesises = data;
	        });
    	}
    	researchService.getThesisCounts().then(function(data){
    		dialog.hideLoading();
            console.log(data);
            scope.thesisCounts = data.thesisCounts;
            scope.awardThesisCounts = data.awardThesisCounts;
            scope.inThesisCounts = data.inThesisCounts;
            scope.meetingThesisCounts = data.meetingThesisCounts;
            scope.reshipThesisCounts = data.reshipThesisCounts;
        });
        researchService.getThesises(type).then(function(data){
        	dialog.hideLoading();
            scope.thesises = data;
        });
}]).controller("workcontroller",['$scope','researchService','dialog',
    function(scope,researchService,dialog){
    	dialog.showLoading();
    	var type = "chief";
    	researchService.getWorksCounts().then(function(data){
    		dialog.hideLoading();
    		scope.chief_editor = data.chief_editor;
    		scope.subeditor = data.subeditor;
    		scope.partake = data.partake;
    	});
        researchService.getWorks(type).then(function(data){
        	dialog.hideLoading();
            scope.works = data;
        });
        scope.getChiefEditor = function(){
        	$("#cyry").removeClass("jzhi-menu-hover");
        	$("#zb").addClass("jzhi-menu-hover");
        	type = "chief";
        	researchService.getWorks(type).then(function(data){
	            scope.works = data;
	        });
        }
        scope.getPartake = function(){
        	$("#zb").removeClass("jzhi-menu-hover");
        	$("#cyry").addClass("jzhi-menu-hover");
        	type = "partake";
        	researchService.getWorks(type).then(function(data){
	            scope.works = data;
	        });
        }
}]).controller("patentcontroller",['$scope','researchService','dialog',
    function(scope,researchService,dialog){
    	dialog.showLoading();
    	var type = "accept";
        researchService.getPatentCounts().then(function(data){
        	dialog.hideLoading();
            scope.acceptPatent = data.acceptPatent;
            scope.accreditPatent = data.accreditPatent;
        });
        researchService.getPatents(type).then(function(data){
        	dialog.hideLoading();
            scope.patents = data;
        });
        scope.getAcceptPatent = function(){
        	$("#sq").removeClass("jzhi-menu-hover");
        	$("#sl").addClass("jzhi-menu-hover");
        	type = "accept";
        	researchService.getPatents(type).then(function(data){
	            scope.patents = data;
	        });
        }
        scope.getAccreditPatent = function(){
        	$("#sl").removeClass("jzhi-menu-hover");
        	$("#sq").addClass("jzhi-menu-hover");
        	type = "accredit";
        	researchService.getPatents(type).then(function(data){
	            scope.patents = data;
	        });
        }
}]).controller("outcomecontroller",['$scope','researchService','dialog',
    function(scope,researchService,dialog){
    	dialog.showLoading();
    	var type = "appraisal";
    	$("#appralsaltable").show();
    	$("#awardtable").hide();
        researchService.getOutcomeCounts().then(function(data){
        	dialog.hideLoading();
            scope.appraisaloutcome = data.appraisaloutcome;
            scope.awardoutcome = data.awardoutcome;
        });
        researchService.getOutcomes(type).then(function(data){
        	dialog.hideLoading();
            scope.outcomes = data;
        });
        scope.getAppraisalOutcome = function(){
        	$("#hj").removeClass("jzhi-menu-hover");
        	$("#jd").addClass("jzhi-menu-hover");
       		type = "appraisal";
       		$("#appralsaltable").show();
    		$("#awardtable").hide();
       		researchService.getOutcomes(type).then(function(data){
	            scope.outcomes = data;
	        });
        }
        scope.getAwardOutcome = function(){
        	$("#jd").removeClass("jzhi-menu-hover");
        	$("#hj").addClass("jzhi-menu-hover");
       		type = "award";
       		$("#appralsaltable").hide();
    		$("#awardtable").show();
       		researchService.getOutcomes(type).then(function(data){
	            scope.outcomes = data;
	        });
        }
        
}]).controller("softcontroller",['$scope','researchService','dialog',
    function(scope,researchService,dialog){
    	dialog.showLoading();
    	researchService.getSofts().then(function(data){
    		dialog.hideLoading();
            console.log(data);
            scope.softs = data;
        });
}]);

app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "controller"
	})
	.when("/project", {
		templateUrl: "tpl/project.html",
		reloadOnSearch: false,
		controller : "projectcontroller"
	})
	.when("/thesis", {
		templateUrl: "tpl/thesis.html",
		reloadOnSearch: false,
		controller : "thesiscontroller"
	})
	.when("/work", {
		templateUrl: "tpl/work.html",
		reloadOnSearch: false,
		controller : "workcontroller"
	})
	.when("/patent", {
		templateUrl: "tpl/patent.html",
		reloadOnSearch: false,
		controller : "patentcontroller"
	})
	.when("/outcome", {
		templateUrl: "tpl/outcome.html",
		reloadOnSearch: false,
		controller : "outcomecontroller"
	})
	.when("/soft", {
		templateUrl: "tpl/soft.html",
		reloadOnSearch: false,
		controller : "softcontroller"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);