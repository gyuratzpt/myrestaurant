import Users from "../model/users-model";
import jwt from 'jsonwebtoken';
const config = require('../../config');
import validateFormData from '../utils/validator.utils';

export function read_all_users(req, res) {
    Users.getAllUsers(function(err, user) {
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
    Users.getOneUser(req.params.email, function(err, user) {
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
    Users.loginUserByEmailAndPassword(req, function(err, user) {
        if (err) {
            res.status(400).send(err);
            return;
        } else {
            jwt.sign(
                {
                    id: user.id,
                    email: user.email,
                    is_admin: user.is_admin,
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
        const newUser = new Users(req.body);
        Users.addUser(newUser, function(err, insertId) {
            if (err) {
                res.status(400).send(err);
                return;
            } else {
                res.status(200).send(
                    `Inserted new user with the ID: ${insertId}`
                );
                return;
            }
        });
    }
}