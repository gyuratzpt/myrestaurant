import User from "../model/users-model";
import jwt from 'jsonwebtoken';
const config = require('../../config');
import validateFormData from '../utils/validator.utils';

export function read_all_users(req, res) {
    User.getAllUsers(function(err, user) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ user });
            return;
        }
    });
}

export function read_one_user(req, res) {
    User.getOneUser(req.params.email, function(err, user) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            res.json({ user });
            return;
        }
    });
}


export function login_user(req, res) {
    User.loginUserByEmailAndPassword(req, function(err, user) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            jwt.sign(
                {
                    id: user.id,
                    email: user.email,
                    is_admin: user.is_admin,
                    name: user.user_name,
                    address: user.address,
                    phonenumber: user.phonenumber                    
                },
                config.tokenKey,
                { expiresIn: config.tokenExpiration },
                (err, token) => {
                    res.json({ token });
                }
            );
            return;
        }
    });
}

export function create_user(req, res) {
    const errorMessages = validateFormData(
        req.body.email,
        req.body.password
    );
    if (errorMessages.length > 0) {
        res.status(401).send(errorMessages);
        return;
    } else {
        const newUser = new User(req.body);
        User.addUser(newUser, function(err, insertId) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.status(200).json({insertId})
                return;
            }
        });
    }
}

export function modify_users_item(req, res) {
    User.modifyUserByID(
        req.params.id,
        req.body.name,
        req.body.address,
        req.body.phonenumber,
        function(err, result) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.status(200).send({
                    message: `Data successfully updated for ${req.params.id}!`});
                return;
            }
        });
    } 