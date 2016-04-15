'use strict';

angular.module('styleMeApp').controller('ResultsCtrl', function($scope, $window, $location, $routeParams, $rootScope, GetClothes, IncrementStyleTerms, DecrementStyleTerms) {

  $scope.init = function() {
    $scope.loaded = false;
    $('#loadingDialog').modal('show');
    $scope.search = $routeParams.details;
    $scope.parseSearch();
    $scope.getClothes();
    $scope.resultLimit = 24;
    $scope.moreResults = true;
  };

  $(document).ready(function(){
    $('[data-toggle="tooltipLove"]').tooltip();
    $('[data-toggle="tooltipHate"]').tooltip();
});

  $scope.parseSearch = function() {
    var items = $scope.search.split(/[&]/);
    $scope.gender = items[0].substring(2, items[0].length);
    $scope.style = items[1].substring(2, items[1].length);
    $scope.types = items[2].split(/[_]/);
    $scope.types[0] = $scope.types[0].substring(2, $scope.types[0].length);
    $scope.colours = items[3].split(/[_]/);
    $scope.colours[0] = $scope.colours[0].substring(2, $scope.colours[0].length);
    $scope.range = [items[4], items[5]];
    $scope.range[0] = $scope.range[0].substring(4, $scope.range[0].length);
    $scope.range[1] = $scope.range[1].substring(4, $scope.range[1].length);
    $scope.incTerms = items[6].split(/[_]/);
    $scope.incTerms[0] = $scope.incTerms[0].substring(2, $scope.incTerms[0].length);
    $scope.decTerms = items[7].split(/[_]/);
    $scope.decTerms[0] = $scope.decTerms[0].substring(2, $scope.decTerms[0].length);
  };

  $scope.getClothes = function() {
    GetClothes.get({
      //style: $scope.style,
      //types: $scope.types,
      //colours: $scope.colours,
      //range: $scope.range,
      //websites: $scope.websites,
      incTerms: $scope.incTerms,
      decTerms: $scope.decTerms
    }, function(list) {
      $scope.clothingList = list;
      if(list.length <= 24) {
        $scope.moreResults = false;
      }
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
  };


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

  $scope.incrementTerms = function(terms, index) {
    IncrementStyleTerms.save({
      style: $scope.style,
      terms: terms
    });
    var image = document.getElementById('love'+index);
    image.src = "images/love_inverted.png";
    var image = document.getElementById('dislike'+index);
    image.src = "images/dislike.png";
  };

  $scope.decrementTerms = function(terms, index) {
    DecrementStyleTerms.save({
      style: $scope.style,
      terms: terms
    });
    var image = document.getElementById('dislike'+index);
    image.src = "images/dislike_inverted.png";
    var image = document.getElementById('love'+index);
    image.src = "images/love.png";
  };

  $scope.goSearch = function() {
    $location.path('/');
  };

  $scope.$watch('clothingList', function(val) {
    $scope.data = [].concat.apply([], val);
  }, true);

  $scope.loadMore = function() {
    $scope.resultLimit += 24;
    if ($scope.resultLimit >= $scope.clothingList.length) {
      $scope.moreResults = false;
    }
  };



});
