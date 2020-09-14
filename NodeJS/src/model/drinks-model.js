'use strict';
import { conn } from '../../app';



export default class Drinks {
    constructor(drinkItem) {
        this.name = drinkItem.name;
        this.detail = drinkItem.detail;
        this.price = drinkItem.price;
        this.picture = drinkItem.picture;
    }
    
    static getAllDrinksItems(res) {
        conn.query(
            `SELECT * FROM drinks`,
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













    static addDrinkItem(newItem, res) {
        conn.query(
            'INSERT INTO `drinks` (`name`, `detail`, `price`, `picture`) ' +
                'VALUES (?, ?, ?, ?)',
            [
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

    static modifyDrinkItem(name, detail, price, picture, res) {
        conn.query(
            'UPDATE drinks SET name = ?, detail = ?, price = ?, picture = ? WHERE name = ?',
            [name, detail, price, picture, name],
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

    static deleteDrinksItemByName(name, res) {
        conn.query(
            'DELETE FROM drinks WHERE name = ?',
            [name],
            function(err, result) {
                if (err) {
                    res(err, null);
                } else {
                    res(null, result);
                }
            }
        );
    }
}