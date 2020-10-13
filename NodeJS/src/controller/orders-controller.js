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

export function create_new_order(req, res) {
    const newOrder = new Orders(req.body);
    Orders.addNewOrder(newOrder, function(err, insertId) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.status(200).send(
            `Picked up new item with the ID: ${insertId}.`);
            return;
        }
    });
}


export function finalizeOrder(req, res) {
    Products.finalizeOrder(req.body.id, req.body.status, function(err, resp) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.status(200).send({
                    message: `Products successfully updated for ${req.body.name}!`});
                return;
            }
        });
    }
