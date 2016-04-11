'use strict';

angular.module('hotelmanageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hotel', {
                abstract: true,
                parent: 'site'
            });
    });
