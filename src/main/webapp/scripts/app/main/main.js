'use strict';

angular.module('hotelmanageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'MainController'
                    }
                }
            });
    })
    .controller('MainController', ['$scope', '$state', '$http','localStorageService','ServiceBaseURL',
        function ($scope, $state, $http,localStorageService,ServiceBaseURL) {
            $scope.gs = function (){
                $state.go('rooms',
                    {
                        "name":"aaa",
                        "auth":1
                    });
            }
            $scope.error={
                userError:null,
                passwordError :null
            }
            $scope.number = "";
            $scope.password = "123456";
            $scope.user = null;
            $scope.loginUser = function () {
                var HotelUser = {
                    'number':  $scope.number,
                    'password': $scope.password
                }
                $http({
                    method: 'POST',
                    url: ServiceBaseURL+'/api/login',
                    data: {
                        'login': $scope.number,
                        'password': $scope.password
                    }
                }).success(function (data) {
                    if (data !== null) {
                        var auth = data.auth;
                        var name = data.firstName;
                        localStorageService.set("user",data);
                        $state.go('dashbord');
                    }
                    else {
                    }
                })
                    .error(function(error,status){
                        if(status ==500){
                            if(error.message == 'password wrong'){
                               $scope.error.passwordError = "密码错误";
                            }
                            if(error.message == 'No value present'){
                                $scope.error.userError = "用户不存在"
                            }
                        }
                    })
                ;
            }




        }]);
