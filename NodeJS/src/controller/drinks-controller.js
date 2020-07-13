import Drinks from "../model/drinks-model";

export function read_all_drink_items(req, res) {
    Drinks.getAllDrinksItems(function(err, drink) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ drink });
            return;
        }
    });
}
    export function create_drink_item(req, res) {
        const newItem = new Drinks(req.body);
        Drinks.addDrinkItem(newItem, function(err, insertId) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.status(200).send(
                    `Picked up new drink item with the ID: ${insertId}.`
                );
                return;
            }
        });
    }

    export function update_drink(req, res) {
        Drinks.modifyDrinkItem(
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
                        message: `Drinks successfully updated for ${req.body.name}!`,
                    });
                    return;
                }
            }
        );
    }

    export function delete_drinks_item(req, res) {
        Drinks.deleteDrinksItemByName(req.params.name, function(err, response) {
            if (err) {
                res.status(400).send(err);
                return;
            } else if (response.affectedRows === 0 && response.changedRows === 0) {
                res.status(404).send(errorMessages.get(404));
                return;
            } else {
                res.status(200).send(
                    `Drinks item with ${req.params.name} name successfully deleted!`
                );
                return;
            }
        });
    }