'use strict';

angular.module('hotelmanageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hotelUser', {
                parent: 'entity',
                url: '/hotelUsers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'hotelmanageApp.hotelUser.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hotelUser/hotelUsers.html',
                        controller: 'HotelUserController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hotelUser');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hotelUser.detail', {
                parent: 'entity',
                url: '/hotelUser/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'hotelmanageApp.hotelUser.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hotelUser/hotelUser-detail.html',
                        controller: 'HotelUserDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hotelUser');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HotelUser', function($stateParams, HotelUser) {
                        return HotelUser.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hotelUser.new', {
                parent: 'hotelUser',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hotelUser/hotelUser-dialog.html',
                        controller: 'HotelUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    password: null,
                                    auth: null,
                                    number: null,
                                    dateIn: null,
                                    sex: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('hotelUser', null, { reload: true });
                    }, function() {
                        $state.go('hotelUser');
                    })
                }]
            })
            .state('hotelUser.edit', {
                parent: 'hotelUser',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hotelUser/hotelUser-dialog.html',
                        controller: 'HotelUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HotelUser', function(HotelUser) {
                                return HotelUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hotelUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hotelUser.delete', {
                parent: 'hotelUser',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hotelUser/hotelUser-delete-dialog.html',
                        controller: 'HotelUserDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HotelUser', function(HotelUser) {
                                return HotelUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hotelUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
