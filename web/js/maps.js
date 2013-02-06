define(['async!https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false'], function () {

    // window.google.maps

    return new function () {

        var myMap;

        this.addMapToCanvas = function (mapCanvas) {
            var mapOptions = {
                center: new google.maps.LatLng(48.858165, 2.345186),
                zoom: 12,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                disableDoubleClickZoom: true
            };

            myMap = new google.maps.Map(mapCanvas, mapOptions);
        }

        this.addMarker = function (lat, lng) {
            var marker = new google.maps.Marker({
                position: new google.maps.LatLng(lat, lng),
                draggable: true,
                map: myMap,
                title: 'Hello'
            });

        }
    }

});





