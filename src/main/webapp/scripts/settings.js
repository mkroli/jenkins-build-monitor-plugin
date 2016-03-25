angular.
    module('buildMonitor.settings', [ 'buildMonitor.services', 'uiSlider']).

    controller('controlPanel', ['$scope', 'cookieJar', 'townCrier',
        function ($scope, cookieJar, townCrier) {
            'use strict';

            if(!$scope.settings || !$scope.settings.fullScreen) {
                $scope.settings.fontSize        = cookieJar.get('fontSize',        1);
                $scope.settings.numberOfColumns = cookieJar.get('numberOfColumns', 2);
                $scope.settings.colourBlind     = cookieJar.get('colourBlind',     0);
            }

            angular.forEach($scope.settings, function(value, name) {
                $scope.$watch('settings.' + name, function(currentValue) {
                    cookieJar.put(name, currentValue);
                });
            });

            // that's the minimum viable product .. at its tiniest
            townCrier.uponNewVersion(function() {
                $scope.newVersionAvailable = true;
            });
        }]);