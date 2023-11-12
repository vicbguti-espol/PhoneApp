# PhoneApp

# White Box Utils

**Metodología de trabajo**

[MobilePhone para agregar, remover y editar contactos](#mobilephone-class)   
[Contact y findAttributes para encontrar atributos de un mismo tipo](#contact-class)      

**Conciencia de la estructura**      

[¿Qué atributos sí o sí tendrán los contactos?](#agregar-un-contacto)

## Metodología de trabajo

## MobilePhone: Class
- `MobilePhone: Class` es prácticamente usuario principal. Es decir `MobilePhone: Class` tiene los atributos necesarios para representar quien maneja la aplicación, quien tiene el jar, quien ejecuta el proyecto. Por ello en esta clase existe únicamente por el momento una lista de contactos, `static List<Contact> getContactList()`.
- Además se cuenta con otros métodos estáticos para poder agregar un contacto `static void addContact(Contact: c)` y eliminar un contacto `static void removeContact(Contact: c)`. Hacer uso de estos métodos es importante al momento de agregar, remover o incluso editar contactos. Interesante en editar un contacto, porque en el mejor de los casos sería remover el contacto previo y agregar luego el contacto que cuenta con las modificaciones. Se recomienda hacer uso de estos métodos a diferencia de acceder directamente a los métodos de la lista que se obtiene con `static List<Contact> getContactList()`, puesto que los métodos mencionados anteriormente hacen uso de la serialización de la lista cada que se agrega o elimina algún elemento.

## Contact: Class
- Todos las clases hijas de `Contact: Abstract Class` tienen un mismo constructor, es decir `Contact.Person: Class` y `Contact.Company: Class` se construyen a partir de pedir un único atributo, `Attribute.PhoneNumber: Abstract Class`, donde en este se tiene que ingresar un `Attribute.PhoneNumber.CompanyPhone: Class` o un `Attribute.PhoneNumber.PersonPhone: Class` dependiendo del caso.
- Cuenta con el método `List<Attribute> findAttributes(Comparator<Attribute> cmp, Attribute attribute)`. 

    Este método el cual entre sus usos se encuentra el obtener una lista de atributos de un tipo en específico (por ejemplo de `Attribute.PhoneNumber` o `Attribute.Location`). Pues existe un `ComparatorUtil.cmpByAttribute` el cual se encarga de actuar de comparador, y en `Attribute attribute` prácticamente va una instanciación vacía de el atributo a buscar. 

    Por ejemplo, si me gustaría buscar todos los números de teléfono de un contacto en específico `pepito: Contact.Person`, entonces se haría uso del método de la siguiente manera: `pepito.findAttributes(ComparatorUtil.cmpByAttribute, new PhoneNumber())`.

## SourceType: Enum
- A partir del enum `SourceType: Enum` se puede conocer de, un `Attribute.Email: Class` (correo electrónico), `Attribute.Location.PersonLocation: Class` (ubicación de una persona) o `Attribute.PhoneNumber.PersonPhone: Class` (teléfono de una persona), si es de tipo `SourceType.WORK` (trabajo) o de tipo `SourceType.PERSONAL` (personal).

## Conciencia de la estructura

## Agregar un contacto 
Agregar un contacto tiene campos obligatorios, algo que hace que siempre existan esos atributos. A continuación se muestra una tabla con estos atributos que siempre existirán en un tipo de contacto en específico.

| **Contact.Person**                | **Contact.Company**                |
|-----------------------------------|------------------------------------|
| Attribute.PhoneNumber.PersonPhone | Attribute.PhoneNumber.CompanyPhone |
| Attribute.Name.PersonName         | Attribute.Name.CompanyName         |
| Attribute.Location.PersonLocation | Attribute.Location.CompanyLocation |
| Attribute.Reminder.Birthday       | Attribute.CompanyDescription       |
|                                   | Attribute.CompanyWebPage           |
| Attribute.Image                   | Attribute.Image                    |

** De comienzo la multiplicidad de `Attribute.Image: Class` es de `1..*`, esto puesto que se pueden recibir varias imágenes.   
** El uso de `GeneralClass.ParticularClass` es usado para representar herencia entre clases.



# Changes 11/10/2023

- Ahora objetos de tipo `Image` y `Contact` se reconocen por un identificador único que se obtiene a partir del método `getRefId()`.
- Creación de clases `Person` y `Company` como hijas de `Contact`, en resumen no tienen atributos propios, pero sirve para diferenciar entre una **persona** y una **empresa**.
- Abstracción de `PersonName` y `CompanyName` como nuevas clases que heredan de una clase `Name`. Se considera a `Name` contarlo como un solo atributo, no como antes que se tenía `Name` y `LastName` a una persona. `Name` y `LastName` ahora son atributos de `PersonName`. 
- La clase `Birthday` que hereda de `Reminder` necesita ser `getRemainingDays()` sobreescrito de `Birthday`, puesto que en el constructor se está recibiendo un `LocalDate` de la fecha de nacimiento de la persona.
- Se elimina el método `getAttributes()`, puesto que ahora la lista `attributes` es un atributo público el cual se puede acceder desde una instancia. Con este cambio se pueden acceder a métodos como `add()`, en modificación directa a la lista. Es decir si tengo un `Contact contacto` puedo hacer uso de la lista mediante `contacto.attributes`.
- Se hizo la implementación de el método `find()` en `Contact`, directamente para la lista `attributes` donde se hace uso de un comparador y un objeto de tipo `Attribute`. Se espera que en inicio cuando se haga uso de este método el comparador implemente una solución de tal forma que la comparación salga igual a `0` una vez que entre dos `Attribute` sean iguales en tipo de clase, es decir hacer uso del método `getAttributeName()` en ambos objetos de tal forma que al hacer uso del método `equals()` salga verdadero.
- Creación de la clase `GenericReminder` que hereda de `Reminder`. Se diferencia de demás `Reminder` porque es un `Reminder` y además incluye un atributo `description: String`.

## Demo implementación Agregar Persona

https://github.com/vicbguti-espol/PhoneApp/assets/133924738/9b0674f8-dc8c-4ffe-b4c0-c9c8609afe10

