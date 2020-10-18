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


export function finalizeOrderById(req, res) {
    Orders.finalizeOrderById(req.params.id, req.body.status, function(err, resp) {
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

    export function finalizeOrderById_Gettel(req, res) {
        Orders.finalizeOrderById_Gettel(req.params.id, function(err, resp) {
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