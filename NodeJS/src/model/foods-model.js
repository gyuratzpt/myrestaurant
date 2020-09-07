'use strict';
import { conn } from '../../app';
import getFilesizeInBytes from '../image/file-size-reader';
import { getImageMimeType } from '../image/image-mimetype';

export default class Foods {
    constructor(foodItem) {
        this.name = foodItem.name;
        this.detail = foodItem.detail;
        this.price = foodItem.price;
        this.picture = foodItem.picture;
        
    }

    static getAllFoodNames(res) {
        conn.query(
            `SELECT * FROM foods WHERE WHERE name = 'Kebab'`,
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

    static downloadpicture(res){
        conn.query(`SELECT picture FROM foods`,
        [],
        function (error, blob){
            if(error){
                res(error, null);
            } else if (blob[0]['picture'] !== null){
                const imageData = blob[0]['picture'];
                const imageMime = getImageMimeType(imageData);
                res(null, {imageData, imageMime});
            } else {
                res(null, { message: 'No picture of that'});
            }
        }
        );
    }

    static addFoodItem(newItem, res) {
        conn.query(
            'INSERT INTO `foods` (`name`, `detail`, `price`, `picture`) ' +
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

    static modifyFoodItem(name, detail, price, picture, res) {
        conn.query(
            'UPDATE foods SET name = ?, detail = ?, price = ?, picture = ? WHERE name = ?',
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

    static deleteFoodsItemByName(name, res) {
        conn.query(
            'DELETE FROM foods WHERE name = ?',
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