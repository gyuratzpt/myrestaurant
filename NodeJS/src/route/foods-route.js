'use strict';

export default function(router) {
    const foods = require('../controller/foods-controller');

    router.get(
        '/foods',
        foods.read_all_food_items
    );

    router.post(
        '/foods',
        foods.create_food_item
    );

}