'use strict';

angular.module('styleMeApp').service('ParseService', function() {

  this.convertNameToString = function(list) {
    var string = "";
    if(list != []) {
      for(var index in list) {
        string = string + list[index].name.toLowerCase() + "_";
      }
      string = string.substring(0, string.length-1);
    } else {
      string = null;
    }
    return string;
  },

  this.convertToString = function(list) {
    var string = "";
    if(list != []) {
      for(var index in list) {
        string = string + list[index].toLowerCase() + "_";
      }
      string = string.substring(0, string.length-1);
    } else {
      string = null;
    }
    return string;
  };

});
