'use strict';

angular.module('hotelmanageApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


