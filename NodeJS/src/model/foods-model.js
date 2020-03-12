import { conn } from '../../app';

export default class Foods {
    constructor(foodItem) {
        this.name = foodItem.name;
        this.detail = foodItem.detail;
        this.price = foodItem.price;
        
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
            'INSERT INTO `foods` (`name`, `detail`, `price`) ' +
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