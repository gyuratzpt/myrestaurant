import { conn } from '../../app';

export default class Users {
    constructor(usersItem) {
        this.email = usersItem.email;
        this.is_admin = usersItem.is_admin;
        this.password = usersItem.password;
        this.username = usersItem.username;
        this.address = usersItem.address;
        this.phonenumber = usersItem.phonenumber;
        this.updated_at = usersItem.updated_at;

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
                        console.log('Length === 0');
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
        static loginUserByEmailAndPassword_original(req, res) {
            conn.query(
                'SELECT * FROM users WHERE email = ?',
                [req.body.email],
                function(err, result) {
                    console.log('Input: %s, %s',req.body.email , req.body.password);
                    if (err) {
                        return res(err, null);
                    }
                    if (result.length === 0) {
                        console.log('Length === 0');
                        res('Wrong email address given', null);
                    } else {
                        if (result[0].password == req.body.password){
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
                    if (result.length > 0) {
                        res('Email address already in use!', null);
                    } else {
                        conn.query(
                            'INSERT INTO `users`( `email`, `password`,`username`,`address`,`phonenumber`, `updated_at`) ' +
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