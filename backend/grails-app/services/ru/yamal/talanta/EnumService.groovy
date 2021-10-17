package ru.yamal.talanta

import grails.gorm.transactions.Transactional

@Transactional
class EnumService {

    // получить значение enum
    static def getValue (enumList, id) {
        for (item in enumList.values()) {
            if (item.id == id) {
                return item
            }
        }
        throw new IllegalArgumentException()
    }
}
