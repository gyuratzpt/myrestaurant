'use strict';
import { conn } from '../../app';



export default class Categories {
    constructor(categoryItem) {
        this.id = categoryItem.id;
        this.name = categoryItem.name;
    }

    static getCategories(res) {
        conn.query(
            `SELECT * FROM categories WHERE id = 2`,
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