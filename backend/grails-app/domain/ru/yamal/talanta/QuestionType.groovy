package ru.yamal.talanta

enum QuestionType {
    SELECT('select', 'Выбор из списка'),
    CHECKBOX('checkbox', 'Множественный выбор'),
    TEXT('text', 'Текст'),
    RADIO('radio', 'Радио-кнопка'),

    private QuestionType(String id, String name) {
        this.id = id
        this.name = name
    }

    final String id
    final String name
}
