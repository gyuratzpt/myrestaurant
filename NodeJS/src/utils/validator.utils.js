'use strict';

const validator = require('validator');

export default function validateFormData(email, password) {
    let errorMessage = '';

    !validator.isEmail(email)
        ? (errorMessage += 'Email address has to be in correct email form. \n')
        : (errorMessage += '');

    !validator.isLength(password, { min: 8, max: 100 })
        ? (errorMessage +=
              'Password has to be minimum 8 and maximum 100 characters long. \n')
        : (errorMessage += '');

    !validator.isAlphanumeric(password, ['en-US'])
        ? (errorMessage +=
              'Name can only contain number and letters in English (GB). \n')
        : (errorMessage += '');

    return errorMessage;
}
