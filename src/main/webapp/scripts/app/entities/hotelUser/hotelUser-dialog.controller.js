'use strict';

angular.module('hotelmanageApp').controller('HotelUserDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HotelUser',
        function($scope, $stateParams, $uibModalInstance, entity, HotelUser) {

        $scope.hotelUser = entity;
        $scope.load = function(id) {
            HotelUser.get({id : id}, function(result) {
                $scope.hotelUser = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelmanageApp:hotelUserUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.hotelUser.id != null) {
                HotelUser.update($scope.hotelUser, onSaveSuccess, onSaveError);
            } else {
                HotelUser.save($scope.hotelUser, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDateIn = {};

        $scope.datePickerForDateIn.status = {
            opened: false
        };

        $scope.datePickerForDateInOpen = function($event) {
            $scope.datePickerForDateIn.status.opened = true;
        };
}]);
