'use strict';
import Products from "../model/products-model";

export function read_all_products(req, res) {
    Products.getAllProducts(function(err, product) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ product });
            return;
        }
    });
}

export function read_filtered_products(req, res) {
    Products.getFilteredProducts(req.params.categoryID, function(err, product) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ product });
            return;
        }
    });
}

export function read_one_product(req, res) {
    Products.getOneProduct(req.params.id, function(err, product) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ product });
            return;
        }
    });
}

    export function create_new_item(req, res) {
        const newItem = new Products(req.body);
        Products.addNewItem(newItem, function(err, insertId) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.status(200).send(
                `Picked up new item with the ID: ${insertId}.`);
                return;
            }
        });
    }

    export function delete_products_item(req, res) {
        Products.deleteItemByID(req.params.id, function(err, response) {
            if (err) {
                res.status(400).send(err);
                return;
            } else if (response.affectedRows === 0 && response.changedRows === 0) {
                res.status(404).send(errorMessages.get(404));
                return;
            } else {
                res.status(200).send(
                    `Drinks item with ${req.params.id} name successfully deleted!`);
                return;
            }
        });
    
    }

export function modify_products_item(req, res) {
    Products.modifyItemByID(
        req.params.id,
        req.body.name,
        req.body.detail,
        req.body.price,
        req.body.picture,
        function(err, result) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.status(200).send({
                    message: `Products successfully updated for ${req.body.name}!`});
                return;
            }
        });
    }
