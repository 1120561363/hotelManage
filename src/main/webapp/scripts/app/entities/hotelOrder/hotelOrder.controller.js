'use strict';

angular.module('hotelmanageApp')
    .controller('HotelOrderController', function ($scope, $state, HotelOrder, ParseLinks) {

        $scope.hotelOrders = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            HotelOrder.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.hotelOrders.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.hotelOrders = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.hotelOrder = {
                number: null,
                customerName: null,
                customerMobile: null,
                roomNumber: null,
                period: null,
                amount: null,
                createdDate: null,
                id: null
            };
        };
    });
