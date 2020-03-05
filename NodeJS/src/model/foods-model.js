import { conn } from '../../app';

export default class Foods {
    constructor(foodItem) {
        this.itemName = foodItem.itemName;
        this.itemName = foodItem.itemDescription;
        this.itemName = foodItem.itemPrice;
        
    }
    static getAllFoodsItems(res) {
        conn.query(
            `SELECT * FROM foods`,
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

    static addFoodItem(newItem, res) {
        conn.query(
            'INSERT INTO `foods` (`name`, `description`, `price`) ' +
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