import { conn } from '../../app';

export default class Drinks {
    constructor(drinkItem) {
        this.name = drinkItem.name;
        this.detail = drinkItem.detail;
        this.price = drinkItem.price;
        
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
            'INSERT INTO `drinks` (`name`, `detail`, `price`) ' +
                'VALUES (?, ?, ?)',
            [
                newItem.name,
                newItem.detail,
                newItem.price
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
}