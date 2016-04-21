'use strict';
angular.module('styleMeApp')
  .factory('GetClothes', function($resource) {
    return $resource(ApplicationProperties.homeUrl + '/style/clothes', {
      gender: '@gender',
      style: '@style',
      types: '@types',
      colours: '@colours',
      range: '@range',
      incTerms: '@incTerms',
      decTerms: '@decTerms'
    }, {
      get: {
        isArray: true
      }
    });
  });
