package ru.yamal.talanta
import grails.gorm.services.Service

@Service(Study)
interface StudyService {
    Study save(Study interest)
}
