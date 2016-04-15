'use strict';
angular.module('styleMeApp')
  .factory('DecrementStyleTerms', function($resource) {
    return $resource(ApplicationProperties.homeUrl + '/style/decrementTerms', {
      style: '@style',
      terms: '@terms'
    }, {method:'POST'});
});
