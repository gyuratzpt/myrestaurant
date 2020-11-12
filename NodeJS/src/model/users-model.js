import { conn } from '../../app';

export default class User {
    constructor(userItem) {
        this.email = userItem.email;
        this.is_admin = userItem.is_admin;
        this.password = userItem.password;
        this.username = userItem.username;
        this.address = userItem.address;
        this.phonenumber = userItem.phonenumber;
        this.updated_at = userItem.updated_at;

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

        static getOneUser(email, res) {
            conn.query(
                'SELECT * FROM users WHERE email = ?',
                [email],
                function(err, result) {
                    if (err) {
                        return res(err, null);
                    }
                    else {
                        res(null, result[0]);

                    }
                }
            );
        }

        static loginUserByEmailAndPassword(req, res) {
            conn.query(
                'SELECT * FROM users WHERE email = ?',
                [req.query.email],
                function(err, result) {
                    console.log('Input: %s, %s',req.query.email , req.query.password);
                    if (err) {
                        return res(err, null);
                    }
                    if (result.length === 0) {
                        console.log('No such registered email address, result length === 0');
                        res('Wrong email address given', null);
                    } else {
                        if (result[0].password == req.query.password){
                            console.log('Login OK');
                            res(null, result[0]);
                        }else{
                            console.log('Passord not matches');
                            res('Wrong user password given!', null);
                        }
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
                    else if (result.length > 0) {
                        res('Email address already in use!', null);
                        return;
                    } else {
                        conn.query(
                            'INSERT INTO `users`( `email`, `password`,`user_name`,`address`,`phonenumber`, `updated_at`) ' +
                                'VALUES (?, ?, ?, ?, ?, NOW())',
                            [
                                newUser.email,
                                newUser.password,
                                newUser.username,
                                newUser.address,
                                newUser.phonenumber,
                                newUser.updated_at
                            ],
                            function(err, response) {
                                if (err) {
                                    console.log('/addUser/ Error üzenet száll a szélben: ', err);
                                    res(err, null);
                                } else {
                                    console.log('/addUser/ Sikeres adatbázis művelet: ', response.insertId);
                                    res(null, response.insertId);
                                }
                            }
                        );
                    }
                }
            );
        }

        static addUser_original(newUser, res) {
            conn.query(
                'SELECT * FROM users WHERE email = ?',
                [newUser.email],
                function(err, result) {
                    if (err) {
                        return res(err, null);
                    }
                    else if (result.length > 0) {
                        res('Email address already in use!', null);
                        return;
                    } else {
                        conn.query(
                            'INSERT INTO `users`( `email`, `password`,`user_name`,`address`,`phonenumber`, `updated_at`) ' +
                                'VALUES (?, ?, ?, ?, ?, NOW())',
                            [
                                newUser.email,
                                newUser.password,
                                newUser.username,
                                newUser.address,
                                newUser.phonenumber,
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