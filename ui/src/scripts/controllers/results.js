'use strict';

angular.module('styleMeApp').controller('ResultsCtrl', function($scope, $window, $location, $routeParams, $rootScope, GetClothes) {

  $scope.init = function() {
    $scope.loaded = false;
    $('#loadingDialog').modal('show');
    $scope.search = $routeParams.details;
    $scope.parseSearch();
    $scope.getClothes();
    $scope.resultLimit = 1000;
  };

  $scope.parseSearch = function() {
    var items = $scope.search.split(/[&]/);
    $scope.style = items[0].substring(2, items[0].length);
    $scope.types = items[1].split(/[_]/);
    $scope.types[0] = $scope.types[0].substring(2, $scope.types[0].length);
    $scope.colours = items[2].split(/[_]/);
    $scope.colours[0] = $scope.colours[0].substring(2, $scope.colours[0].length);
    $scope.range = [items[3], items[4]];
    $scope.range[0] = $scope.range[0].substring(4, $scope.range[0].length);
    $scope.range[1] = $scope.range[1].substring(4, $scope.range[1].length);
  };

  $scope.getClothes = function() {
    GetClothes.get({
      style: $scope.style,
      types: $scope.types,
      colours: $scope.colours,
      range: $scope.range,
      websites: $scope.websites
    }, function(list) {
      $scope.clothingList = list; //= $scope.chunk(list, 3);
      $scope.removeLoading();
    });
  };

  $scope.removeLoading = function() {
    $('#loadingDialog').modal('hide');
    $('.modal-backdrop').remove();
    $scope.loaded = true;
  };

  $scope.isEmpty = function(list) {
    if($scope.loaded == true) {
      if (list.length == 0) {
        return true;
      }
    }
    return false;
  }


  $scope.getListAsString = function(list) {
    var string = "";
    list.forEach(function(item) {
      string = string + item + ", "
    });
    string = string.substring(0, string.length-2);
    return string;
  };

  $scope.open = function(url){
    var win = window.open(url, '_blank');
    win.focus();
  };

  $scope.goSearch = function() {
    $location.path('/');
  };

  $scope.chunk = function(arr, size) {
    var chunckedArray = [];
    for (var i=0; i<arr.length; i+=size) {
      chunckedArray.push(arr.slice(i, i+size));
    }
    return chunckedArray;
  };

  $scope.$watch('clothingList', function(val) {
    $scope.data = [].concat.apply([], val);
  }, true); // deep watch

});
