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
