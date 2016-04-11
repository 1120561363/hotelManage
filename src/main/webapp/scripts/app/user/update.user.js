'use strict';

angular.module('hotelmanageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('edit_user', {
                parent: 'site',
                url: '/edit/user?name=?&auth=?&newName=?&newAuth=?&number=?&sex=?&dateIn=?',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/user/update.user.html',
                        controller: 'UpdateUserController'
                    }
                },
            });
    })
    .controller('UpdateUserController', ['$scope', '$stateParams', '$state','$http','localStorageService','ServiceBaseURL',
        function ($scope, $stateParams, $state,$http,localStorageService,ServiceBaseURL) {
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
            $scope.newName = $stateParams.newName;
            $scope.newAuth = $stateParams.newAuth;
            $scope.number = $stateParams.number;
            $scope.sex = $stateParams.sex;
            $scope.dateIn = $stateParams.dateIn;
            $scope.newName1 = $stateParams.newName;
            $scope.newAuth1 = $stateParams.newAuth;
            $scope.number1 = $stateParams.number;
            $scope.sex1 = $stateParams.sex;
            $scope.dateIn1 = $stateParams.dateIn;
            $scope.flag1 = false;
            $scope.flag2 = false;
            $scope.flag3 = false;
            $scope.changeFlag1 = function () {
                if ($scope.flag1 == false) {
                    $scope.flag1 = true;
                } else if ($scope.flag1 == true) {
                    $scope.flag1 = false;
                }
            }
            $scope.changeFlag2 = function () {
                if ($scope.flag2 == false) {
                    $scope.flag2 = true;
                } else if ($scope.flag2 == true) {
                    $scope.flag2 = false;
                }
            }
            $scope.changeFlag3 = function () {
                if ($scope.flag3 == false) {
                    $scope.flag3 = true;
                } else if ($scope.flag3 == true) {
                    $scope.flag3 = false;
                }
            }
            $scope.goToHtml = function (url) {
                $state.go(url);
            }


            $scope.goToPage = function (url) {
                alert(url);
                $state.go(url);
            }
            $scope.back = function(){
                $state.go('users');
            }
            $scope.editThisUser = function () {
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
                    method: 'PUT',
                    url: ServiceBaseURL+'/api/users',
                    data: {
                        'firstName': $scope.newName,
                        'login': $scope.number,
                        'auth': authRs,
                        'sex': $scope.sex
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
