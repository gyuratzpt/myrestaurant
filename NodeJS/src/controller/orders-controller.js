'use strict';
import Order from "../model/orders-model";

export function read_all_orders(req, res) {
    Order.getAllOrders(function(err, orderlist) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ orderlist});
            return;
        }
    });
}


export function read_all_orders_original(req, res) {
    Order.getAllOrders(function(err, orderlist) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ orderlist });
            return;
        }
    });
}


export function create_new_order(req, res) {
    const newOrder = new Order(req.body);
    Order.addNewOrder(newOrder, function(err, insertId) {
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


export function finalizeOrderById(req, res) {
    Order.finalizeOrderById(req.params.id, req.body.status, function(err, resp) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.status(200).send({
                    message: `Order successfully closed!`});
                return;
            }
        });
    }