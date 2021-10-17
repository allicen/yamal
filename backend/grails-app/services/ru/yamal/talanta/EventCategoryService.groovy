package ru.yamal.talanta

import grails.gorm.services.Service

@Service(EventCategory)
interface EventCategoryService {
    EventCategory save(EventCategory eventCategory)

    EventCategory get(Serializable id)

    List<EventCategory> list(Map args)

    EventCategory findName(String name)

    Long count()

    void delete(Serializable id)
}