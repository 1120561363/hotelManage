 'use strict';

angular.module('hotelmanageApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-hotelmanageApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-hotelmanageApp-params')});
                }
                return response;
            }
        };
    });
