'use strict';
import { conn } from '../../app';



export default class Orders {
    constructor(orderItem) {
        this.orderid = orderItem.orderid;
        this.userid = orderItem.userid;
        this.productid = orderItem.productid;
        this.amount = orderItem.amount;
    }

    static getAllOrders(res) {
        conn.query(
            `SELECT * FROM orders LEFT JOIN products ON orders.productID = products.id LEFT JOIN users ON orders.userID = users.id`,
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
}