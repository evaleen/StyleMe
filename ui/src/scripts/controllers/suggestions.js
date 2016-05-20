'use strict';

angular.module('styleMeApp').controller('SuggestionsCtrl', function($scope, $location, $routeParams, $rootScope, ParseService, GetClothesSuggestions) {

    $scope.init = function() {
        $scope.loaded = false;
        $scope.empty = true;
        $('#loadingDialog').modal('show');
        $scope.search = $routeParams.details;
        $scope.parseSearch();
        $scope.getClothesSuggestions();
        $scope.resultLimit = 1000;
        $scope.incTerms = [];
        $scope.decTerms = [];
    };

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
    };

    $scope.getClothesSuggestions = function() {
        GetClothesSuggestions.get({
            gender: $scope.gender,
            style: $scope.style,
            types: $scope.types,
            colours: $scope.colours,
            range: $scope.range
        }, function(list) {
            $scope.clothingList = list;
            $scope.removeLoading();
            $scope.isEmpty(list);
        });
    };

    $scope.removeLoading = function() {
        $('#loadingDialog').modal('hide');
        $('.modal-backdrop').remove();
        $scope.loaded = true;
    };

    $scope.isEmpty = function(list) {
        if ($scope.loaded == true) {
            if (list.length > 0) {
                $scope.empty = false;
            }
        }
    };

    $scope.getListAsString = function(list) {
        var string = "";
        list.forEach(function(item) {
            string = string + item + ", "
        });
        string = string.substring(0, string.length - 2);
    };

    $scope.$watch('clothingList', function(val) {
        $scope.data = [].concat.apply([], val);
    }, true);

    $scope.moreLike = function(terms, index) {
        $scope.incTerms = $scope.incTerms.concat(terms);
        $scope.decTerms = $scope.removeTerms($scope.decTerms, terms);
        angular.element('#incTerms' + index).addClass("selectedBtn");
        angular.element('#decTerms' + index).removeClass("selectedBtn");
    };

    $scope.lessLike = function(terms, index) {
        $scope.decTerms = $scope.decTerms.concat(terms);
        $scope.incTerms = $scope.removeTerms($scope.incTerms, terms);
        angular.element('#decTerms' + index).addClass("selectedBtn");
        angular.element('#incTerms' + index).removeClass("selectedBtn");
    };

    $scope.removeTerms = function(list, terms) {
        terms.forEach(function(term) {
            var index = list.indexOf(term);
            if (index > -1) {
                list.splice(index, 1);
            }
        });
        return list;
    };

    $scope.continue = function() {
        $scope.path = "results/g=" + $scope.gender;
        $scope.path = $scope.path + "&s=" + $scope.style.toLowerCase();
        $scope.path = $scope.path + "&t=" + ParseService.convertToString($scope.types);
        $scope.path = $scope.path + "&c=" + ParseService.convertToString($scope.colours);
        $scope.path = $scope.path + "&min=" + $scope.range[0] + "&max=" + $scope.range[1];
        $scope.path = $scope.path + "&pos=" + ParseService.convertToString($scope.incTerms);
        $scope.path = $scope.path + "&neg=" + ParseService.convertToString($scope.decTerms);
        $location.path($scope.path);
    };

    $scope.returnToSearch = function() {
        $location.path('/' + $scope.gender);
    };

});