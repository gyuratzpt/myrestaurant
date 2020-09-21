'use strict';
import Foods from "../model/foods-model";

export function read_all_food_items(req, res) {
    Foods.getAllFoodsItems(function(err, food) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ food });
            return;
        }
    });
}

export function create_food_item(req, res) {
    const newItem = new Foods(req.body);
    Foods.addFoodItem(newItem, function(err, insertId) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.status(200).send(
                `Picked up new food item with the ID: ${insertId}.`);
            return;
        }
    });
}

export function update_food(req, res) {
    Foods.modifyFoodItem(
        req.body.name,
        req.body.detail,
        req.body.price,
        req.body.picture,
        function(err, resp) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.status(200).send({
                    message: `Foods successfully updated for ${req.body.name}!`,});
                return;
            }
        });
    }

export function delete_foods_item(req, res) {
    Foods.deleteFoodsItemByName(req.params.name, function(err, response) {
        if (err) {
            res.status(400).send(err);
            return;
        } else if (response.affectedRows === 0 && response.changedRows === 0) {
            res.status(404).send(errorMessages.get(404));
            return;
        } else {
            res.status(200).send(
                `Foods item with ${req.params.name} name successfully deleted!`);
            return;
        }
    });
}


export function download_picture(req, res) {
    Foods.downloadpicture(function(err, resp){
        if(err) {
            res.status(400).send(err);
            return;
        } else if (resp.message){
            res.status(404).send(resp);
            return;
        } else { 
            res.type(resp.ImageMime)
                .status(200)
                .send(resp.imageData);
            return;
        }
    });
}