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
        'ngAria'
    ])
    .config(function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/search.html',
                controller: 'SearchCtrl'
            })
            .when('/results/:details', {
                templateUrl: 'views/results.html',
                controller: 'ResultsCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });
