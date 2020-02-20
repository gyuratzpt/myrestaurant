import Products from "../model/product-model";

export function read_all_product_items(req, res) {
    Products.getAllProductsItems(function(err, product) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ product });
            return;
        }
    });
}