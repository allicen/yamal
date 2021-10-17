package ru.yamal.talanta

import grails.gorm.services.Service

@Service(Interest)
interface InterestService {
    Interest save(Interest interest)
}
