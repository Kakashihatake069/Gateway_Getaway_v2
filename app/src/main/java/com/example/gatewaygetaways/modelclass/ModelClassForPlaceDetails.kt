package com.example.gatewaygetaways.modelclass

class ModelClassForPlaceDetails {


    lateinit var image: String
    lateinit var name: String
    lateinit var rateing: String
    lateinit var amount: String
    lateinit var info: String

    constructor(image: String,name: String,rateing: String,amount: String,info: String) {
        this.image = image
        this.name = name
        this.rateing = rateing
        this.amount = amount
        this.info = info
    }

    constructor() {

    }
}