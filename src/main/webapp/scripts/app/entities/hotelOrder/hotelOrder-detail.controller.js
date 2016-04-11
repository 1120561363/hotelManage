'use strict';

angular.module('hotelmanageApp')
    .controller('HotelOrderDetailController', function ($scope, $rootScope, $stateParams, entity, HotelOrder) {
        $scope.hotelOrder = entity;
        $scope.load = function (id) {
            HotelOrder.get({id: id}, function(result) {
                $scope.hotelOrder = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelmanageApp:hotelOrderUpdate', function(event, result) {
            $scope.hotelOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
