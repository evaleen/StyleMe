'use strict';

/**
 * @ngdoc overview
 * @name styleMeApp
 * @description
 * # styleMeApp
 *
 * Main module of the app.
 */
var app = angular
    .module('styleMeApp', ['ngResource',
        'ngRoute',
        'ngAria',
        'ngTagsInput',
        'rzModule'
    ])
    .config(function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/searchInput.html',
                controller: 'SearchInputCtrl'
            }).when('/:gender', {
                templateUrl: 'views/searchInput.html',
                controller: 'SearchInputCtrl'
            })
            .when('/suggestions/:details', {
                templateUrl: 'views/suggestions.html',
                controller: 'SuggestionsCtrl'
            })
            .when('/results/:details', {
                templateUrl: 'views/results.html',
                controller: 'ResultsCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });