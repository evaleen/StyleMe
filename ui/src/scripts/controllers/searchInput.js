'use strict';

angular.module('styleMeApp').controller('SearchInputCtrl', function($scope, $http, $window, $location, $rootScope, $routeParams, Attributes, ParseService) {

  jQuery(document).ready(function ($) {
    $('#tabs').tab();
  });

  $scope.init = function() {
    $scope.styleheader = 'Style Me!';
    $scope.selectedWomensStyle;
    $scope.selectedMensStyle;
    $scope.WomensStyles = Attributes.getWomensStyles();
    $scope.MensStyles = Attributes.getMensStyles();
    $scope.types = [];
    $scope.colours = [];
    $scope.gender = $routeParams.gender;
    if($scope.gender == "mens") {
      $scope.reset("#womensTab", "#mensTab");
      $("#womens").removeClass("active");
      $("#mens").addClass("active");
    } else {
      $scope.reset("#mensTab", "#womensTab");
    }
  };

  $scope.selectWomensStyle = function(style) {
    $scope.selectedWomensStyle = style;
  };

  $scope.selectMensStyle = function(style) {
    $scope.selectedMensStyle = style;
  };

  $scope.slider = {
    minPrice: 0,
    maxPrice: 500,
    options: {
      floor: 0,
      ceil: 500,
      translate: function(value) { return ''; },
      getPointerColor: function(value) { return '#84596b'; },
      getSelectionBarColor: function(value) { return '#84596b'; }
    }
  };

  $scope.loadTags = function($query, file) {
    return $http.get('resources/' + file, { cache: true}).then(function(response) {
      var item = response.data;
      return item.filter(function(item) {
        return item.name.toLowerCase().indexOf($query.toLowerCase()) != -1;
      });
    });
  };

  $(document).on('click', '#mensClick', function() {
      $scope.reset("#womensTab", "#mensTab");
  });

  $(document).on('click', '#womensClick', function() {
      $scope.reset("#mensTab", "#womensTab");
  });

  $scope.reset = function (deactivateTab, activateTab) {
    $(deactivateTab).removeClass("active");
    $(activateTab).addClass("active");
    setTimeout(function(){
      $scope.$broadcast('reCalcViewDimensions');
    }, 10);
    $scope.types = [];
    $scope.colours = [];
  };

  $scope.submit = function(gender) {
    if(gender == 'mens' && !$scope.selectedMensStyle || gender == 'womens' && !$scope.selectedWomensStyle) {
      $window.alert("You havent Selected a style! :(");
    } else {
      if(gender == 'mens') { var style = $scope.selectedMensStyle; }
      else { var style = $scope.selectedWomensStyle }
      $scope.path = "suggestions/g=" + gender;
      $scope.path = $scope.path + "&s=" + style.toLowerCase();
      $scope.path = $scope.path + "&t=" + ParseService.convertNameToString($scope.types);
      $scope.path = $scope.path + "&c=" + ParseService.convertNameToString($scope.colours);
      $scope.path = $scope.path + "&min=" + $scope.slider.minPrice + "&max=" + $scope.slider.maxPrice;
      $location.path($scope.path);
    };
  };

  $scope.showMessage = function() {
    window.alert("Your message was sent!");
  };


});
