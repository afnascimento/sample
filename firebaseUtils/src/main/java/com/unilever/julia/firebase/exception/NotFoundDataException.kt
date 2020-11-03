package com.unilever.julia.firebase.exception

class NotFoundDataException : Exception {

    constructor() {}

    constructor(message: String) : super(message) {}

    constructor(cause: Throwable) : super(cause) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}
}
