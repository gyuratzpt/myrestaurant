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

    router.get(
        '/login', 
        users.login_user
    );

    router.post(
        '/registration', 
        users.create_user
    );

    router.put(
        '/users/put/:id',
        users.modify_users_item
    )
}