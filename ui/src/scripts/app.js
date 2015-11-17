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
                templateUrl: 'views/home.html',
                controller: 'MainCtrl'
            })
            .when('/loading', {
                templateUrl: 'views/loading.html',
                controller: 'LoadingCtrl'
            })
            .when('/results', {
                templateUrl: 'views/results.html',
                controller: 'ResultsCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });