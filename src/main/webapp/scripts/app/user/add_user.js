'use strict';

angular.module('hotelmanageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('add_user', {
                parent: 'site',
                url: '/add/user?name=?&auth=?',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/user/add_user.html',
                        controller: 'AddUserController'
                    }
                },
            });
    })
    .controller('AddUserController',['$scope', '$stateParams','$state','$http','localStorageService','ServiceBaseURL',
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
            $scope.newName = "";
            $scope.newAuth = "";
            $scope.number = "";
            $scope.sex = "";
            $scope.dateIn = "";
            $scope.password = "";
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
            $scope.back = function(){
                $state.go('users');
            }
            $scope.addUser = function () {
                var authRs;
                if ($scope.newAuth == "经理") {
                    authRs = 1;
                } else if ($scope.newAuth == "服务员") {
                    authRs = 2;
                } else if ($scope.newAuth == "前台接待") {
                    authRs = 3;
                } else if ($scope.newAuth == "保洁") {
                    authRs = 4;
                }
                $http({
                    method: 'POST',
                    url:ServiceBaseURL+ '/api/users/add',
                    data: {
                        'firstName': $scope.newName,
                        'login': $scope.number,
                        'auth': authRs,
                        'sex': $scope.sex,
                        'password':$scope.password,
                        'resetDate':$scope.dateIn1
                    }
                }).success(function (data) {
                    if (data !== null) {
                        console.log(data);
                        var auth = data.auth;
                        var name = data.firstName;
                        $state.go('users');
                    }
                    else {
                        alert("修改失败");
                    }
                }).error(function () {
                    alert("网络故障");
                });
            }
        }]);
