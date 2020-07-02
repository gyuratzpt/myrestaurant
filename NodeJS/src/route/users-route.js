'use strict';

export default function(router) {
    const users = require('../controller/users-controller');

    router.get(
        '/users',
        users.read_all_users_items
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