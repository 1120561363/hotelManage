'use strict';

angular.module('hotelmanageApp')
    .factory('HotelUser', function ($resource, DateUtils) {
        return {
            login: function (HotelUser) {
                return $http({
                    method: 'POST',
                    url: 'localhost/api/login',
                    params: {HotelUser:HotelUser}
                });
            },
        }


        /*
        * $resource('api/hotelUsers/:id', {}, {
         'query': { method: 'GET', isArray: true},
         'get': {
         method: 'GET',
         transformResponse: function (data) {
         data = angular.fromJson(data);
         data.dateIn = DateUtils.convertLocaleDateFromServer(data.dateIn);
         return data;
         }
         },
         'update': {
         method: 'PUT',
         transformRequest: function (data) {
         data.dateIn = DateUtils.convertLocaleDateToServer(data.dateIn);
         return angular.toJson(data);
         }
         },
         'save': {
         method: 'POST',
         transformRequest: function (data) {
         data.dateIn = DateUtils.convertLocaleDateToServer(data.dateIn);
         return angular.toJson(data);
         }
         }
         });
        * */
    });
