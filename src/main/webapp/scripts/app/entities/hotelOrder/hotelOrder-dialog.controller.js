'use strict';

angular.module('hotelmanageApp').controller('HotelOrderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HotelOrder',
        function($scope, $stateParams, $uibModalInstance, entity, HotelOrder) {

        $scope.hotelOrder = entity;
        $scope.load = function(id) {
            HotelOrder.get({id : id}, function(result) {
                $scope.hotelOrder = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelmanageApp:hotelOrderUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.hotelOrder.id != null) {
                HotelOrder.update($scope.hotelOrder, onSaveSuccess, onSaveError);
            } else {
                HotelOrder.save($scope.hotelOrder, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreatedDate = {};

        $scope.datePickerForCreatedDate.status = {
            opened: false
        };

        $scope.datePickerForCreatedDateOpen = function($event) {
            $scope.datePickerForCreatedDate.status.opened = true;
        };
}]);
