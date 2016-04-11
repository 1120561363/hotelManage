/**
 * Created by yuxin on 2016/3/26.
 */
'use strict';

angular.module('hotelmanageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rooms', {
                parent: 'site',
                url: '/rooms?name=?&auth=?',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/rooms/all_rooms.html',
                        controller: 'AllRoomsController'
                    }
                },
            });
    })
    .controller('AllRoomsController',['$scope', '$stateParams','$state','$http','localStorageService','ServiceBaseURL',
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
                $state.go(url,
                    {
                        "name":$scope.name,
                        "auth":$scope.auth
                    });
            }

            console.log($scope);
            console.log($scope.auth);
            $scope.goToPage = function (url) {
                alert(url);
                $state.go(url);
            }
            $scope.update = function(room){
                console.log(room);
                $state.go('room_update',{
                    "number":room.number,
                    "price":room.price,
                    "type":room.type
                });
            }
            $scope.rooms = [];
            $scope.init = function () {
                $http({
                    method: 'GET',
                    url: ServiceBaseURL+'/api/rooms',
                    data: {
                        'page': 0,
                        'size': 7
                    }
                }).success(function (data) {
                    $scope.rooms = data;
                    console.log(data);
                });
            }
            $scope.init();
        }]);
