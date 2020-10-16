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
        '/orders/put/:id',
        orders.finalizeOrder
    );

    router.get(
        '/orders/get/:id',
        orders.finalizeOrderGettel
    );

}