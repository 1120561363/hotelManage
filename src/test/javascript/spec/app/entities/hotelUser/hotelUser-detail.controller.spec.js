'use strict';

describe('Controller Tests', function() {

    describe('HotelUser Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHotelUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHotelUser = jasmine.createSpy('MockHotelUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HotelUser': MockHotelUser
            };
            createController = function() {
                $injector.get('$controller')("HotelUserDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelmanageApp:hotelUserUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
