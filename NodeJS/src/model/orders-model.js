'use strict';
import { conn } from '../../app';



export default class Order {
    constructor(orderItem) {
        this.orderid = orderItem.orderid;
        this.userid = orderItem.userid;
        this.note = orderItem.note;
        this.date = orderItem.date;
    }

    static getAllOrders(res) {
        conn.query(
            `SELECT orders.*, users.user_name, users.is_admin, orderitems.amount, products.product_name, products.price FROM orders
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

    
        static getAllOrders_original(res) {
            conn.query(
                `SELECT * FROM orders
                LEFT JOIN products ON orders.product_id = products.id
                LEFT JOIN users ON orders.user_id = users.id
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


    static addNewOrder(newOrder, res) {
        conn.query(
            'INSERT INTO `orders` (`user_id`, `product_id`, `amount`) ' +
                'VALUES (?, ?, ?)',
            [
                newOrder.userid,
                newOrder.productid,
                newOrder.amount
            ],
            function(err, response) {
                if (err) {
                    console.log('Error: ', err);
                    res(err, null);
                } else {
                    res(null, response.insertId);
                }
            }
        );
    }

    static finalizeOrderById(id, status, res) {
        conn.query(
            'UPDATE orders SET is_completed = ? WHERE order_id = ?',
            [status, id],
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