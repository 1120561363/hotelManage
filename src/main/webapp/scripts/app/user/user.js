'use strict';

angular.module('hotelmanageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('users', {
                parent: 'site',
                url: '/users?name=?&auth=?',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/user/users.html',
                        controller: 'UserController'
                    }
                },
            });
    })
    .controller('UserController',['$scope', '$stateParams','$state','$http','localStorageService','ServiceBaseURL',
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
            $scope.editUser = function(user){
                console.log(user);
                $state.go('edit_user',{
                    "newName":user.name,
                    "newAuth":user.auth,
                    "number":user.number,
                    "sex":user.sex,
                    "dateIn":user.dateIn
                });
            }
            $scope.users = [];
            $scope.result = [];
            $scope.init = function () {
                $http({
                    method: 'GET',
                    url:ServiceBaseURL+ '/api/users',
                    data: {
                        'page': 0,
                        'size': 7
                    }
                }).success(function (data) {
                    $scope.result = data;
                    for(var i=0;i<$scope.result.length;i++){
                        var rs = $scope.result[i] ;
                        var user = {
                            name:"",
                            auth:"",
                            number:"",
                            dateIn:"",
                            sex:""
                        };
                        var ss = rs.auth;
                        console.log(rs);
                        var auth ;
                        if(ss == 1){
                            auth = "经理";
                        }else if(ss == 2){
                            auth = "服务员";
                        }else if(ss == 3){
                            auth = "前台接待";
                        }else if(ss == 4){
                            auth = "保洁";
                        }
                        user.name = rs.firstName;
                        user.auth = auth;
                        user.number = rs.login;
                        user.dateIn = rs.createdDate.toString().substr(0,10);
                        user.sex = rs.sex;
                        $scope.users.push(user);
                    }
                });
            }
            $scope.init();
        }]);
