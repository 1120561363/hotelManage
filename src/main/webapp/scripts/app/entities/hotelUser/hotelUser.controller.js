'use strict';

angular.module('hotelmanageApp')
    .controller('HotelUserController', function ($scope, $state, HotelUser, ParseLinks) {

        $scope.hotelUsers = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            HotelUser.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.hotelUsers.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.hotelUsers = [];
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
            $scope.hotelUser = {
                name: null,
                password: null,
                auth: null,
                number: null,
                dateIn: null,
                sex: null,
                id: null
            };
        };
    });
