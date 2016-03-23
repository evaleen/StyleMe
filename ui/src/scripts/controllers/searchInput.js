'use strict';

angular.module('styleMeApp').controller('SearchInputCtrl', function($scope, $http, $window, $location, $rootScope, Attributes) {

  $(document).ready(function() {
     });

  $scope.init = function() {
    $scope.styleheader = 'Style Me!';
    $scope.selectedStyle;
    $scope.styles = Attributes.getStyles();
    $scope.types = [];
    $scope.colours = [];
    $scope.selectedStyle;
  };

  $scope.selectStyle = function(style) {
    $scope.selectedStyle = style;
  };

  $scope.slider = {
  minPrice: 0,
  maxPrice: 1000,
  options: {
    floor: 0,
    ceil: 1000,
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

  $scope.convertToString = function(list) {
    var string = "";
    if(list != []) {
      for(var index in list) {
        string = string + list[index].name.toLowerCase() + "_";
      }
      string = string.substring(0, string.length-1);
    } else {
      string = null;
    }
    return string;
  };

  $scope.submit = function() {
    if(!$scope.selectedStyle) {
      $window.alert("You havent Selected a style! :(");
    } else {
      $scope.path = "results/s=" + $scope.selectedStyle.toLowerCase();
      $scope.path = $scope.path + "&t=" + $scope.convertToString($scope.types);
      $scope.path = $scope.path + "&c=" + $scope.convertToString($scope.colours);
      $scope.path = $scope.path + "&min=" + $scope.slider.minPrice + "&max=" + $scope.slider.maxPrice;
      $location.path($scope.path);
    };
  };
});
