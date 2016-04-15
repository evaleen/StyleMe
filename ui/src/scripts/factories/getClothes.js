'use strict';
angular.module('styleMeApp')
  .factory('GetClothes', function($resource) {
    return $resource(ApplicationProperties.homeUrl + '/style/clothes', {
      incTerms: '@incTerms',
      decTerms: '@decTerms'
    }, {
      get: {
        isArray: true
      }
    });
  });
