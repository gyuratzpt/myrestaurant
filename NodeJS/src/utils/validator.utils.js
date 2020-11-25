'use strict';

const validator = require('validator');

export default function validateFormData(email, password) {
    let errorMessage = '';

    !validator.isEmail(email)
        ? (errorMessage +=
            'Az email cím formátuma nem megfelelő!\n')
        : (errorMessage += '');

    !validator.isLength(password, { min: 8, max: 16 })
        ? (errorMessage +=
              'A jelszó minimum 8, maximum 16 karakter hosszú lehet.\n')
        : (errorMessage += '');

    !validator.isAlphanumeric(password, ['en-US'])
        ? (errorMessage +=
              'A jelszó csak betűket és számokat tartalmazhat!\n')
        : (errorMessage += '');

    return errorMessage;
}
