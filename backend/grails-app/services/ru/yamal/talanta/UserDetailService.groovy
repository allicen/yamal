package ru.yamal.talanta

import grails.gorm.services.Service

@Service(UserDetails)
interface UserDetailService {
    UserDetails save(UserDetails userDetails)
}