'use strict';

export default function(router) {
    const products = require('../controller/product-controller');

    // User role required
    router.get(
        '/products',
        products.read_all_product_items
    );

}