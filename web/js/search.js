define(['jquery', 'bootstrap'], function ($, bootstrap) {

    return new function () {
            var options = {
            source: ['Matthieu', 'Thomas', 'Nicolas']
        };

        $('#typeahead').typeahead(options);
    };

});