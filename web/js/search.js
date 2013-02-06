define(['jquery', 'bootstrap'], function ($, bootstrap) {

    return new function () {

        var artists = [];

        $('#typeahead').typeahead({
            items: 10,

            source: function (search, typeahead) {
                var artist = {
                    query: {
                    multi_match: {
                        query: search.toLowerCase(),
                            fields: [
                                'name^4',
                                'tracks.title^2',
                                'tracks.release'
                            ]
                        }
                    }
                }



                return $.get('http://ec2-54-246-72-133.eu-west-1.compute.amazonaws.com:9200/_search', artist, function (data) {
                    var i = 0,
                        terms = [];
                    for (i = 0; i < data.hits.hits.length; i++) {
                        var artist = data.hits.hits[i]._source;
                        artists.push(artist);
                        terms.push(artist.name);
                    }


                    return typeahead(terms);
                });
            },

            updater: function (item) {
                $('#typeahead').val(item);
                console.log(item);
            }

        });

    };

});