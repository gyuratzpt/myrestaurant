'use strict';

export default function(router) {
    const orders = require('../controller/orders-controller');

    router.get(
        '/orders',
        orders.read_all_orders
    );

}