/**
 * Created by yuxin on 2016/3/26.
 */
'use strict';

angular.module('hotelmanageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('room_update', {
                parent: 'site',
                url: '/room/update?name=?&auth=?&number=?&price=?&type=?',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/rooms/update.room.html',
                        controller: 'UpdateRoomsController'
                    }
                },
            });
    })
    .controller('UpdateRoomsController',['$scope', '$stateParams','$state','$http','localStorageService','ServiceBaseURL',
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
            $scope.number = $stateParams.number;
            $scope.price = $stateParams.price;
            $scope.type = $stateParams.type;
            $scope.numberBackup = $stateParams.number;
            $scope.priceBackup = $stateParams.price;
            $scope.typeBackup = $stateParams.type;
            console.log( $scope.number);
            console.log( $scope.type);
            console.log( $scope.price );
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
                $state.go(url);
            }
            $scope.back = function(){
                $state.go('rooms');
            }

            $scope.reset = function(){
                $scope.number = $scope.numberBackup;
                $scope.price = $scope.priceBackup;
                $scope.type = $scope.typeBackup;
            }
            $scope.updateRoom = function(number,price,type){
                console.log(type.$modelValue);
                var ss = type.$modelValue;
                var type= 1;
                if(ss == "单人间"){
                    type = 1;
                }else  if(ss == "双人间"){
                          type = 2;
                      }
               else  if(ss == "商务套房"){
                    type = 3;
                }
                else if(ss == "贵宾房"){
                    type = 4;
                }
                $http({
                    method: 'POST',
                    url: ServiceBaseURL+'/api/rooms/update',
                    data: {
                        'number': number.$modelValue,
                        'price': price.$modelValue,
                        'type':type
                    }
                }).success(function (data) {
                    if (data !== null) {
                        console.log(data);
                        var auth = data.auth;
                        var name = data.firstName;
                        $state.go('rooms');
                    }
                    else {
                        alert("修改失败");
                    }
                }).error(function(){
                    alert("网络故障");
                });
            }
        }]);
