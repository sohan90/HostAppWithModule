package com.example.sohan.customcalender.model

class ImageResponseModel(val image: Image) {

    class Image(val url: String)
    class Formats()
    class Thumbnail(val url: String)
    class Large(val url: String)
    class Medium(val url: String)
    class Small(val url: String)
}