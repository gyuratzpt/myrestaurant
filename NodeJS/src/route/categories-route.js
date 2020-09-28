'use strict';

export default function(router) {
    const categories = require('../controller/categories-controller');

    router.get(
        '/categories',
        categories.read_all_categories
    );

}