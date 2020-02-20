import { conn } from '../app';

export default class Products {
    constructor(productItem) {
        this.itemName = productItem.itemName;
        this.itemName = productItem.itemDescription;
        this.itemName = productItem.itemPrice;
        
    }
    static getAllProductsItems(res) {
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
}