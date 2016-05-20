'use strict';

angular.module('styleMeApp').service('Attributes', function() {

    this.getMensStyles = function() {
        return [{
            name: "Preppy",
            selected: false
        }, {
            name: "Punk",
            selected: false
        }, {
            name: "Bohemian",
            selected: false
        }, {
            name: "Vintage",
            selected: false
        }, {
            name: "Retro",
            selected: false
        }, {
            name: "Grunge",
            selected: false
        }, {
            name: "Sophisticated",
            selected: false
        }, {
            name: "Sporty",
            selected: false
        }, {
            name: "Gothic",
            selected: false
        }, {
            name: "Minimalist",
            selected: false
        }, {
            name: "Funky",
            selected: false
        }, {
            name: "Casual",
            selected: false
        }];
    };

    this.getWomensStyles = function() {
        return [{
            name: "Preppy",
            selected: false
        }, {
            name: "Punk",
            selected: false
        }, {
            name: "Chic",
            selected: false
        }, {
            name: "Bohemian",
            selected: false
        }, {
            name: "Vintage",
            selected: false
        }, {
            name: "Retro",
            selected: false
        }, {
            name: "Grunge",
            selected: false
        }, {
            name: "Tomboy",
            selected: false
        }, {
            name: "Sophisticated",
            selected: false
        }, {
            name: "Sporty",
            selected: false
        }, {
            name: "Gothic",
            selected: false
        }, {
            name: "Glamorous",
            selected: false
        }, {
            name: "Feminine",
            selected: false
        }, {
            name: "Minimalist",
            selected: false
        }, {
            name: "Funky",
            selected: false
        }, {
            name: "Casual",
            selected: false
        }];
    };

    this.getClothingTypes = function() {
        return [{
            name: "Dresses",
            checked: false,
            image: "dress.png"
        }, {
            name: "Playsuits",
            checked: false,
            image: "playsuit.png"
        }, {
            name: "Tops",
            checked: false,
            image: "top.png"
        }, {
            name: "Skirts",
            checked: false,
            image: "skirt.png"
        }, {
            name: "Shorts",
            checked: false,
            image: "shorts.png"
        }, {
            name: "Jackets",
            checked: false,
            image: "jackets.png"
        }, {
            name: "Denim",
            checked: false,
            image: "denim.png"
        }, {
            name: "Knitwear",
            checked: false,
            image: "knitwear.png"
        }, {
            name: "Trousers",
            checked: false,
            image: "trousers.png"
        }, {
            name: "Swimwear",
            checked: false,
            image: "swimwear.png"
        }, {
            name: "Basics",
            checked: false,
            image: "basics.png"
        }];
    };

    this.getColours = function() {
        return [{
            name: "Black",
            checked: false
        }, {
            name: "White",
            checked: false
        }, {
            name: "Red",
            checked: false
        }, {
            name: "Orange",
            checked: false
        }, {
            name: "Yellow",
            checked: false
        }, {
            name: "Green",
            checked: false
        }, {
            name: "Blue",
            checked: false
        }, {
            name: "Purple",
            checked: false
        }, {
            name: "Pink",
            checked: false
        }, {
            name: "Multi",
            checked: false
        }, {
            name: "Brown",
            checked: false
        }, {
            name: "Beige",
            checked: false
        }, {
            name: "Grey",
            checked: false
        }, {
            name: "Cream",
            checked: false
        }, {
            name: "Silver",
            checked: false
        }, {
            name: "Gold",
            checked: false
        }, {
            name: "Wine",
            checked: false
        }, {
            name: "Khaki",
            checked: false
        }, {
            name: "Navy",
            checked: false
        }];
    };

});