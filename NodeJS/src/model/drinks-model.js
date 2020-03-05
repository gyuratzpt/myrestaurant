import { conn } from '../../app';

export default class Drinks {
    constructor(drinkItem) {
        this.itemName = drinkItem.itemName;
        this.itemName = drinkItem.itemDescription;
        this.itemName = drinkItem.itemPrice;
        
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
            'INSERT INTO `drinks` (`name`, `description`, `price`) ' +
                'VALUES (?, ?, ?, ?)',
            [
                newItem.name,
                newItem.description,
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