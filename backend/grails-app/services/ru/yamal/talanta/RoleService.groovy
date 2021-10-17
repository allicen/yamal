package ru.yamal.talanta

import grails.gorm.services.Service

@Service(Role)
interface RoleService {
    Role save(String authority)
    Role findByAuthority(String authority)
}
