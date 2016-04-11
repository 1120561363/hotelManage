'use strict';

angular.module('hotelmanageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hotelOrder', {
                parent: 'entity',
                url: '/hotelOrders',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'hotelmanageApp.hotelOrder.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hotelOrder/hotelOrders.html',
                        controller: 'HotelOrderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hotelOrder');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hotelOrder.detail', {
                parent: 'entity',
                url: '/hotelOrder/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'hotelmanageApp.hotelOrder.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hotelOrder/hotelOrder-detail.html',
                        controller: 'HotelOrderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hotelOrder');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HotelOrder', function($stateParams, HotelOrder) {
                        return HotelOrder.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hotelOrder.new', {
                parent: 'hotelOrder',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hotelOrder/hotelOrder-dialog.html',
                        controller: 'HotelOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    number: null,
                                    customerName: null,
                                    customerMobile: null,
                                    roomNumber: null,
                                    period: null,
                                    amount: null,
                                    createdDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('hotelOrder', null, { reload: true });
                    }, function() {
                        $state.go('hotelOrder');
                    })
                }]
            })
            .state('hotelOrder.edit', {
                parent: 'hotelOrder',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hotelOrder/hotelOrder-dialog.html',
                        controller: 'HotelOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HotelOrder', function(HotelOrder) {
                                return HotelOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hotelOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hotelOrder.delete', {
                parent: 'hotelOrder',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hotelOrder/hotelOrder-delete-dialog.html',
                        controller: 'HotelOrderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HotelOrder', function(HotelOrder) {
                                return HotelOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hotelOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
