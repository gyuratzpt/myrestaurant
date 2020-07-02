import { conn } from '../../app';

export default class Users {
    constructor(usersItem) {
        this.email = usersItem.email;
        this.password = usersItem.password;
        this.updated_at = usersItem.updated_at;
        this.is_admin = usersItem.is_admin;
    }

        static getAllUsers(res) {
            conn.query(
                `SELECT * FROM users`,
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

        static loginUserByEmailAndPassword(req, res) {
            conn.query(
                'SELECT * FROM users WHERE email = ?',
                [req.body.email],
                function(err, result) {
                    if (err) {
                        return res(err, null);
                    }
                    if (result.length === 0) {
                        res(
                            'Wrong email address given',
                            null
                        );
                    } else {
                        if (result[0].password == req.body.password)
                            res(null, result[0]);
                        else res('Wrong user password given!', null);
                    }
                }
            );
        }

        static addUser(newUser, res) {
            conn.query(
                'SELECT * FROM users WHERE email = ?',
                [newUser.email],
                function(err, result) {
                    if (err) {
                        return res(err, null);
                    }
                    if (result.length > 0) {
                        res('Email address already in use!', null);
                    } else {
                        conn.query(
                            'INSERT INTO `users`( `email`, `password`, `updated_at`) ' +
                                'VALUES (?, ?, NOW())',
                            [
                                newUser.email,
                                newUser.password,
                                newUser.updated_at
                            ],
                            function(err, response) {
                                if (err) {
                                    console.log('Error: ', err);
                                    res(err, null, null);
                                } else {
                                    res(null, response.insertId, response.password);
                                }
                            }
                        );
                    }
                }
            );
        }
}