'use strict';
import Order from "../model/orders-model";
import OrderItem from "../model/orderitems-model";

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

export function finalizeOrderById(req, res) {
    Order.finalizeOrderById(req.params.id, req.body.status, function(err, response) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.status(200).send({
                    message: `Order for ${req.params.id} successfully closed!`});
                return;
            }
        });
    }

    export function create_new_order(req, res) {
        const newOrder = new Order(req.body);
        Order.addNewOrder(newOrder, function(err, result) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.json({result});
                return;
            }
        });
    }

    export function create_new_orderitem(req, res) {
        const newOrderItem = new OrderItem(req.body);
        OrderItem.addNewOrderItem(newOrderItem, function(err, result) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.json({result});
                return;
            }
        });
    }