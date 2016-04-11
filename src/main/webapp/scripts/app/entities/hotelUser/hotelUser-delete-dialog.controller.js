'use strict';

angular.module('hotelmanageApp')
	.controller('HotelUserDeleteController', function($scope, $uibModalInstance, entity, HotelUser) {

        $scope.hotelUser = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HotelUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
