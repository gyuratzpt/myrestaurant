'use strict';

export default function(router) {
    const drinks = require('../controller/drinks-controller');

    router.get(
        '/drinks',
        drinks.read_all_drink_items
    );

    router.post(
        '/drinks',
        drinks.create_drink_item
    );

    router
        .put(
            '/drinks/:name',
            drinks.update_drink
        );
        
    router.delete(
        '/drinks/items/:name',
        drinks.delete_drinks_item
    );
}