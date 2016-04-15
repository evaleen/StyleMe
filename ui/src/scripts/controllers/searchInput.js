'use strict';

angular.module('styleMeApp').controller('SearchInputCtrl', function($scope, $http, $window, $location, $rootScope, Attributes, ParseService) {

  jQuery(document).ready(function ($) {
    $('#tabs').tab();
  });

  $scope.init = function() {
    $scope.styleheader = 'Style Me!';
    $scope.selectedWomensStyle;
    $scope.selectedMensStyle;
    $scope.WomensStyles = Attributes.getWomensStyles();
    $scope.MensStyles = Attributes.getMensStyles();
    $scope.womensTypes = [];
    $scope.womensColours = [];
    $scope.mensTypes = [];
    $scope.mensColours = [];
    $scope.selectedStyle;
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
      $("#womensTab").removeClass("active");
      $("#mensTab").addClass("active");
  });

  $(document).on('click', '#womensClick', function() {
      $("#mensTab").removeClass("active");
      $("#womensTab").addClass("active");
  });

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
});
