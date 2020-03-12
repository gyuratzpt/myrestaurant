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
                `Picked up new food item with the ID: ${insertId}.`
            );
            return;
        }
    });
}