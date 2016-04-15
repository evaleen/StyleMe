'use strict';
angular.module('styleMeApp')
  .factory('GetClothesSuggestions', function($resource) {
    return $resource(ApplicationProperties.homeUrl + '/style/clothesSuggestions', {
      gender: '@gender',
      style: '@style',
      types: '@types',
      colours: '@colours',
      range: '@range',
      websites: '@websites'
    }, {
      get: {
        isArray: true
      }
    });
  });
