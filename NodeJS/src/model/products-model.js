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

    static getOneProduct(id ,res) {
        conn.query(
            `SELECT * FROM products WHERE id = ?`,
            [id],
            function(err, result) {
                if (err) {
                    console.log('Error üzenet száll a szélben: ', err);
                    res(err, null);
                } else {
                    console.log('Az %i tétel értékei: %s' ,id, result);
                    res(null, result[0]);
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
            function(err, result) {
                if (err) {
                    console.log('Error üzenet száll a szélben: ', err);
                    res(err, null);
                } else {
                    console.log('Hozzáadás sikeres, az új tétel ID-ja: ', result);
                    res(null, result.insertId);
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
                    console.log('Error üzenet száll a szélben: ', err);
                    res(err, null);
                } else {
                    console.log('A %i ID-jú tétel törlése sikeres! A result objektum tartalma: ', id, result);
                    res(null, result);
                }
            }
        );
    }

    static modifyItemByID(id, categoryID, name, detail, price, picture, res) {
        conn.query(
            'UPDATE products SET categoryID=?, name = ?, detail = ?, price = ?, picture = ? WHERE id = ?',
            [categoryID, name, detail, price, picture, id],
            function(err, result) {
                if (err) {
                    console.log('Error üzenet száll a szélben: ', err);
                    res(err, null);
                } else {
                    console.log('A %i ID-jú tétel módosítása sikeres! A result objektum tartalma: ', id, result.info);
                    res(null, result);
                }
            }
        );
    }
}