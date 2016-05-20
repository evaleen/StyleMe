'use strict';

angular.module('styleMeApp').controller('SearchCtrl', function($scope, $window, $location, $rootScope, Attributes) {

    $(document).ready(function() {
        var numitems = $(".StylesList li").length;
        $("ul.StylesList").css("column-count", Math.round(numitems / 2));

        $("#slider").slider({
            range: true,
            min: 0,
            max: 500,
            values: [0, 500],
            slide: function(event, ui) {
                $("#amount").val("€" + ui.values[0] + " - €" + ui.values[1]);
                $scope.minPrice = ui.values[0];
                $scope.maxPrice = ui.values[1];
            }
        });
        $("#amount").val("€" + $("#slider").slider("values", 0) + " - €" + $("#slider").slider("values", 1));
    });

    $scope.init = function() {
        $scope.styleheader = 'Style Me!';
        $scope.selectedStyle;
        $scope.minPrice = 0;
        $scope.maxPrice = 500;
        $scope.styles = Attributes.getStyles();
        $scope.types = Attributes.getClothingTypes();
        $scope.colours = Attributes.getColours();
    };

    $scope.select = function(style) {
        $scope.selectedStyle = style;
        angular.forEach($scope.styles, function(item) {
            item.selected = false;
            if (item.name == style.name) {
                item.selected = true;
            }
        });

    };

    $scope.convertToString = function(list) {
        var string = "";
        for (var index in list) {
            if (list[index].checked) {
                string = string + list[index].name.toLowerCase() + "_";
            }
        }
        if (string != "") {
            string = string.substring(0, string.length - 1);
        } else {
            string = null;
        }
        return string;
    };

    $scope.submit = function() {
        if (!$scope.selectedStyle) {
            $window.alert("You havent Selected a style! :(");
        } else {
            $scope.path = "results/s=" + $scope.selectedStyle.name.toLowerCase();
            $scope.path = $scope.path + "&t=" + $scope.convertToString($scope.types);
            $scope.path = $scope.path + "&c=" + $scope.convertToString($scope.colours);
            $scope.path = $scope.path + "&min=" + $scope.minPrice + "&max=" + $scope.maxPrice;
            $location.path($scope.path);
        }
    };
});