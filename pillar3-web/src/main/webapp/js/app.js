require.config({
    baseUrl: 'js',
    
    paths: {
        'jquery': [
            'http://cdnjs.cloudflare.com/ajax/libs/jquery/1.9.0/jquery.min',
            'vendor/jquery-1.9.1.min'
        ],

        'pubsub': 'vendor/pubsub',

        'handlebars': [
            'http://cdnjs.cloudflare.com/ajax/libs/handlebars.js/1.0.rc.2/handlebars.min',
            'vendor/handlebars'
        ],

        'underscore': [
            'http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.4.4/underscore-min',
            'vendor/underscore'
        ],

        'bootstrap': 'vendor/bootstrap.min',

        'text': 'vendor/text',

        'async': 'vendor/async'
    },

    shim: {
        'pubsub': {
            exports: 'PubSub'
        },

        'handlebars': {
            exports: 'Handlebars'
        },

        'underscore': {
            exports: '_'
        }
    },
    
    waitSeconds: 15
});

require(['jquery', 'maps', 'search'], function($, maps, search) {
    var mapCanvas = $('#map_canvas').get(0);
    maps.addMapToCanvas(mapCanvas);
    maps.addMarker(48.858165, 2.345186);


//    $('#typeahead').bind('keydown', function(event) {
//        search.update($(this).val());
//    });

});
