package com.example.jumbechat

class User {
    var email : String? = null
    var name : String? = null
    var uId : String? = null

    constructor()

    constructor(name: String?, email: String?, uId: String?){
        this.email= email
        this.name= name
        this.uId= uId
    }
}