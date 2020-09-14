'use strict';

export default function(router) {
    const foods = require('../controller/foods-controller');
    
    router.get(
        '/foods',
        foods.read_all_food_items
    );

    router.get(
        '/foodkebab',
        foods.read_food_item_kebab
    );

    router.get('/foods/picture',
                foods.download_picture
    );

    router.post(
        '/foods',
        foods.create_food_item
    );
    router
        .put(
            '/foods/:name',
            foods.update_food
        );
    router.delete(
        '/foods/items/:name',
        foods.delete_foods_item
    );
}