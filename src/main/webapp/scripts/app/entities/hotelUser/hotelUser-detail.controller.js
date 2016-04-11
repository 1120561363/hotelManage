'use strict';

angular.module('hotelmanageApp')
    .controller('HotelUserDetailController', function ($scope, $rootScope, $stateParams, entity, HotelUser) {
        $scope.hotelUser = entity;
        $scope.load = function (id) {
            HotelUser.get({id: id}, function(result) {
                $scope.hotelUser = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelmanageApp:hotelUserUpdate', function(event, result) {
            $scope.hotelUser = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
