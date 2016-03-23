'use strict';
angular.module('styleMeApp')
  .factory('GetClothes', function($resource) {
    return $resource(ApplicationProperties.homeUrl + '/style/clothes', {
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
