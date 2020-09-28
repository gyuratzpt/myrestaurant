'use strict';
import Categories from "../model/categories-model";

export function read_all_categories(req, res) {
    Categories.getCategories(function(err, category) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ category });
            return;
        }
    });
}