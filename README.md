# PhoneApp

# White Box Utils




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

