package com.example.gatewaygetaways.modelclass

class UserModelClass {
    var firstName: String? =null
    var lastName: String? =null
    var email: String? =null
    var uid: String? =null
    var image: String?=null

    constructor(){}

    constructor(firstName:String?,lastName:String?,email:String?,uid:String?,image: String?){
        this.firstName=firstName
        this.lastName=lastName
        this.email=email
        this.uid=uid
        this.image=image
    }
}
