'use strict';

angular.module('hotelmanageApp')
	.controller('HotelOrderDeleteController', function($scope, $uibModalInstance, entity, HotelOrder) {

        $scope.hotelOrder = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HotelOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
