package ru.yamal.talanta

class DiscountImage {

    // байты
    byte[] image

    // тип изображения
    String contentType

    static belongsTo = [discont: Discount]

    static constraints = {
    }

    static mapping = {
        image sqlType: 'longblob'
    }
}
