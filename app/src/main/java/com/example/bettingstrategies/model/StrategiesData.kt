package com.example.bettingstrategies.model

class StrategiesData {

    //Название стратегии
    var name :String? = null
    //Краткое описание стратегии
    var info :String? = null
    //Картинка заставки стратегии
    var img :String? = null
    //Полная информация о стратегии
    var Full :String? = null
    //Фотка для полной информация о стратегии
    var ImgFull :String? = null
    //Флаг о нахождении в избраном
    var favourites : String? = null

    constructor(){}

    constructor(name:String?,
                info:String?,
                img:String?,
                Full:String?,
                ImgFull:String?,
                favourites:String?
    ){
        this.name = name
        this.info = info
        this.img = img
        this.Full = Full
        this.ImgFull = ImgFull
        this.favourites = favourites
    }
}