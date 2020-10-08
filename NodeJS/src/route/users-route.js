'use strict';

export default function(router) {
    const users = require('../controller/users-controller');

    router.get(
        '/users',
        users.read_all_users
    );

    router.get(
        '/users/:email',
        users.read_one_user
    );

    router.post(
        '/register', 
        users.create_user
    );
    router.post(
        '/login', 
        users.login_user
    );
}