'use strict';

export default function(router) {
    const orders = require('../controller/orders-controller');

    router.get(
        '/orders',
        orders.read_all_orders
    );

    router.post(
        '/orders',
        orders.create_new_order
    );

    router.put(
        '/orders/:id',
        orders.finalizeOrder
    );

}