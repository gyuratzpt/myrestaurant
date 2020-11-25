'use strict';

export default function(router) {
    const orders = require('../controller/orders-controller');

    router.get(
        '/orders',
        orders.read_all_orders
    );

    router.put(
        '/orders/put/:id',
        orders.finalizeOrderById
    );

        router.post(
        '/orders',
        orders.create_new_order
    );

    router.post(
        '/orders/items',
        orders.create_new_orderitem
    );

}