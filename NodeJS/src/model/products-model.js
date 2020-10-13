'use strict';
import { conn } from '../../app';



export default class Products {
    constructor(productItem) {
        this.id = productItem.id
        this.categoryID = productItem.categoryID;
        this.name = productItem.name;
        this.detail = productItem.detail;
        this.price = productItem.price;
        this.picture = productItem.picture;
    }
    
    static getAllProducts(res) {
        conn.query(
            `SELECT * FROM products`,
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



    static getFilteredProducts(categoryID ,res) {
        conn.query(
            `SELECT * FROM products WHERE categoryID = ?`,
            [categoryID],
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

    static getProduct(id ,res) {
        conn.query(
            `SELECT * FROM products WHERE id = ?`,
            [id],
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

    static addNewItem(newItem, res) {
        conn.query(
            'INSERT INTO `products` (`categoryID`,`name`, `detail`, `price`, `picture`) ' +
                'VALUES (?, ?, ?, ?, ?)',
            [
                newItem.categoryID,
                newItem.name,
                newItem.detail,
                newItem.price,
                newItem.picture
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


    static deleteItemByID(id, res) {
        conn.query(
            'DELETE FROM products WHERE id = ?',
            [id],
            function(err, result) {
                if (err) {
                    res(err, null);
                } else {
                    res(null, result);
                }
            }
        );
    }

    static modifyItemByID(id, name, detail, price, picture, res) {
        conn.query(
            'UPDATE products SET name = ?, detail = ?, price = ?, picture = ? WHERE id = ?',
            [name, detail, price, picture, name, id],
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