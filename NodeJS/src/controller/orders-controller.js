'use strict';
import Orders from "../model/orders-model";

export function read_all_orders(req, res) {
    Orders.getAllOrders(function(err, order) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ order });
            return;
        }
    });
}