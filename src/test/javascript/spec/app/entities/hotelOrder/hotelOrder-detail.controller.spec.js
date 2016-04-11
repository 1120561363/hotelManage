'use strict';

describe('Controller Tests', function() {

    describe('HotelOrder Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHotelOrder;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHotelOrder = jasmine.createSpy('MockHotelOrder');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HotelOrder': MockHotelOrder
            };
            createController = function() {
                $injector.get('$controller')("HotelOrderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelmanageApp:hotelOrderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
