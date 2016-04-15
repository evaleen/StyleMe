'use strict';
angular.module('styleMeApp')
  .factory('IncrementStyleTerms', function($resource) {
    return $resource(ApplicationProperties.homeUrl + '/style/incrementTerms', {
      style: '@style',
      terms: '@terms'
    }, {method:'POST'});
});
