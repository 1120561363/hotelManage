/**
 * Created by yuxin on 2016/3/26.
 */
'use strict';

angular.module('hotelmanageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orders', {
                parent: 'site',
                url: '/orders?name=?&auth=?',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/order/orders.html',
                        controller: 'OrderController'
                    }
                },
            });
    })
    .controller('OrderController',['$scope', '$stateParams','$state','$http','localStorageService','ServiceBaseURL',
        function ($scope, $stateParams,$state,$http,localStorageService,ServiceBaseURL) {
            $scope.user = localStorageService.get("user");
            if($scope.user == null){
                $state.go('home');
            }
            $scope.loginOut = function(){
                localStorageService.remove("user");
                $state.go('home');
            }
            $scope.name = localStorageService.get("user").firstName;
            $scope.auth = localStorageService.get("user").auth;
            $scope.flag1 = false;
            $scope.flag2 = false;
            $scope.flag3 = false;
            $scope.changeFlag1 = function(){
                if($scope.flag1 ==false){
                    $scope.flag1 = true;
                }else  if($scope.flag1 ==true){
                    $scope.flag1 = false;
                }
            }
            $scope.changeFlag2 = function(){
                if($scope.flag2 ==false){
                    $scope.flag2 = true;
                }else  if($scope.flag2 ==true){
                    $scope.flag2 = false;
                }
            }
            $scope.changeFlag3 = function(){
                if($scope.flag3 ==false){
                    $scope.flag3 = true;
                }else  if($scope.flag3 ==true){
                    $scope.flag3 = false;
                }
            }
            $scope.goToHtml = function (url) {
                $state.go(url);
            }

            console.log($scope);
            console.log($scope.auth);
            $scope.goToPage = function (url) {
                alert(url);
                $state.go(url);
            }
            $scope.orders = [];
            $scope.init = function () {
                $http({
                    method: 'GET',
                    url: ServiceBaseURL+'/api/hotelOrders',
                    data: {
                        'page': 0,
                        'size': 5
                    }
                }).success(function (data) {
                    console.log(data);
                    $scope.orders = data;

                });
            }
            $scope.init();
        }]);
