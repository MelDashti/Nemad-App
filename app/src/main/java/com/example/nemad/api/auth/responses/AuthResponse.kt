package com.example.nemad.api.auth.responses

// kotlin class for json response

class RememberPassResult {
    var token: String? = null
}

class AuthenticationResult {
    var status: String? = null
    var message: String? = null
    var token: String? = null
}