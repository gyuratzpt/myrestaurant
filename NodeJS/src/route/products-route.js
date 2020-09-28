'use strict';

export default function(router) {
    const products = require('../controller/products-controller');

    router.get(
        '/products',
        products.read_all_products
    );

    router.post(
        '/products',
        products.create_new_item
    );

    router.delete(
        '/products/items/:id',
        products.delete_products_item
    );


    /*
    router
        .put(
            '/products/:name',
            products.update_drink
        );
        

    */
}