# Сервис-ориентированная архитектура

## Лабораторная 1
Разработать веб-сервис на базе сервлета, реализующий управление коллекцией объектов, и клиентское веб-приложение, предоставляющее интерфейс к разработанному веб-сервису.

В коллекции необходимо хранить объекты класса Route, описание которого приведено ниже:

```
public class Route {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле не может быть null
    private float distance; //Значение поля должно быть больше 1
}

public class Coordinates {
    private Float x; //Максимальное значение поля: 327, Поле не может быть null
    private float y; //Значение поля должно быть больше -863
}

public class Location {
    private float x;
    private float y;
    private String name; //Длина строки не должна быть больше 367, Поле может быть null
}

public class Location {
    private Integer x; //Поле не может быть null
    private Double y; //Поле не может быть null
    private Double z; //Поле не может быть null
}
```

**Веб-сервис должен удовлетворять следующим требованиям:**

- API, реализуемый сервисом, должен соответствовать рекомендациям подхода RESTful.

- Необходимо реализовать следующий базовый набор операций с объектами коллекции: добавление нового элемента, получение элемента по ИД, обновление элемента, удаление элемента, получение массива элементов.

- Операция, выполняемая над объектом коллекции, должна определяться методом HTTP-запроса.

- Операция получения массива элементов должна поддерживать возможность сортировки и фильтрации по любой комбинации полей класса, а также возможность постраничного вывода результатов выборки с указанием размера и порядкового номера выводимой страницы.

- Все параметры, необходимые для выполнения операции, должны передаваться в URL запроса.

- Данные коллекции, которыми управляет веб-сервис, должны храниться в реляционной базе данных.

- Информация об объектах коллекции должна передаваться в формате `json`.

- В случае передачи сервису данных, нарушающих заданные на уровне класса ограничения целостности, сервис должен возвращать код ответа http, соответствующий произошедшей ошибке.

- Веб-сервис должен быть "упакован" в веб-приложение, которое необходимо развернуть на сервере приложений `Payara`.

**Помимо базового набора, веб-сервис должен поддерживать следующие операции над объектами коллекции:**

- Удалить все объекты, значение поля distance которого эквивалентно заданному.
- Вернуть один (любой) объект, значение поля distance которого является минимальным.
- Вернуть количество объектов, значение поля distance которых меньше заданного.

Эти операции должны размещаться на отдельных URL.

**Требования к клиентскому приложению:**

- Клиентское приложение может быть написано на любом веб-фреймворке, который можно запустить на сервере helios.

- Клиентское приложение должно обеспечить полный набор возможностей по управлению объектами коллекции, предоставляемых веб-сервисом, включая сортировку, фильтрацию и постраничный вывод.

- Клиентское приложение должно преобразовывать передаваемые сервисом данные в человеко-читаемый вид: параграф текста, таблицу и т.д.

- Клиентское приложение должно информировать пользователя об ошибках, возникающих на стороне сервиса, в частности, о том, что сервису были отправлены невалидные данные.

Веб-сервис и клиентское приложение должны быть развёрнуты на сервере helios.

## Лабораторная 2

**Доработать веб-сервис и клиентское приложение из лабораторной работы #1 следующим образом:**

- Отрефакторить сервис из лабораторной работы #1, переписав его на фреймворке `Spring MVC REST` с сохранением функциональности и API.
- **Набор функций, реализуемых сервисом, изменяться не должен!**
- Развернуть переработанный сервис на сервере приложений `Tomcat`.
- Разработать новый сервис, вызывающий API существующего.
- Новый сервис должен быть разработан на базе `JAX-RS` и развёрнут на сервере приложений `Payara`.
- Разработать клиентское приложение, позволяющее протестировать API нового сервиса.
- Доступ к обоим сервисам должен быть реализован по протоколу https с самоподписанным сертификатом сервера. Доступ к сервисам посредством http без шифрования должен быть запрещён.

**Новый сервис должен располагаться на URL /navigator и реализовывать следующие операции:**

- `/route/{id-from}/{id-to}/{shortest}`: найти самый короткий (или длинный) маршрут между указанными локациями
- `/route/add/{id-from}/{id-to}/{distance}`: добавить новый маршрут между указанными локациями

Оба веб-сервиса и клиентское приложение должны быть развёрнуты на сервере helios.