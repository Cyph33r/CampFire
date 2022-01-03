package com.cyphertech.games.campfire

class User {
    constructor() {}
    constructor(name: String?, email: String?, uid: Long?) {
        this.name = name
        this.email = email
        this.uid = uid
    }

    var name: String? = null
    var email: String? = null
    var uid: Long? = null
}