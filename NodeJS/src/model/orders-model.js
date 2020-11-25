'use strict';
import { response } from 'express';
import { conn } from '../../app';



export default class Order {
    constructor(order) {
        this.userid = order.userid;
        this.note = order.note;
    }



    static getAllOrders(res) {
        conn.query(
            `SELECT orders.*, users.user_name, users.address, users.phonenumber, orderitems.amount, products.product_name, products.price FROM orders
            LEFT JOIN users ON users.id = orders.user_id
            LEFT JOIN orderitems ON orderitems.order_id = orders.id
            LEFT JOIN products ON products.id = orderitems.product_id
            WHERE is_completed = 0`,
            [],
            function(err, result) {
                if (err) {
                    console.log('Error: ', err);
                    res(err, null);
                } else {
                    res(null, result);
                }
            }
        );
    }

    static finalizeOrderById(id, status, res) {
        conn.query(
            'UPDATE orders SET is_completed = ? WHERE id = ?',
            [status, id],
            function(err, result) {
                if (err) {
                    console.log('Error: ', err);
                    res(err, null);
                } else {
                    console.log('finalizeOrderById successfull: ' , result);
                    res(null, result);
                }
            }
        );
    }


    static addNewOrder(newOrder, res) {
        conn.query(
            'INSERT INTO `orders` (`user_id`,`note` , `order_date`) ' +
                'VALUES (?, ?, NOW())',
            [
                newOrder.userid,
                newOrder.note
            ],
            function(err, result) {
                if (err) {
                    console.log('Error: ', err);
                    res(err, null);
                } else {
                    res(null, result);
                }
            }
        );

    }

}