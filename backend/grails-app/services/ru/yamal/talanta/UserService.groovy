package ru.yamal.talanta

import grails.gorm.services.Service

@Service(User)
interface UserService {
    User save(User user)
}