# Tabla de contenidos
-  [Introducción](#introducción)
-  [API](#api-de-la-aplicación-bicico)
  - [Recurso Bicycle](#recurso-bicycle)
    - [GET /bicycles](#GET-/bicycles)
    - [GET /bicycles/{id}](#GET-/bicycles/{id})
    - [POST /bicycles](#POST-/bicycles)
    - [PUT /bicycles/{id}](#PUT-/bicycles/{id})
    - [DELETE /bicycles/{id}](#DELETE-/bicycles/{id})
    - [GET bicycles/{bicyclesid}/photoAlbum](#GET-bicycles/{bicyclesid}/photoAlbum)
    - [GET bicycles/{bicyclesid}/photoAlbum/{photoAlbumid}](#GET-bicycles/{bicyclesid}/photoAlbum/{photoAlbumid})
    - [POST bicycles/{bicyclesid}/photoAlbum/{photoAlbumid}](#POST-bicycles/{bicyclesid}/photoAlbum/{photoAlbumid})
    - [PUT bicycles/{bicyclesid}/photoAlbum](#PUT-bicycles/{bicyclesid}/photoAlbum)
    - [DELETE bicycles/{bicyclesid}/photoAlbum/{photoAlbumid}](#DELETE-bicycles/{bicyclesid}/photoAlbum/{photoAlbumid}])
  - [Recurso Category](#recurso-category)
    - [GET /categorys](#GET-/categorys)
    - [GET /categorys/{id}](#GET-/categorys/{id})
    - [POST /categorys](#POST-/categorys)
    - [PUT /categorys/{id}](#PUT-/categorys/{id})
    - [DELETE /categorys/{id}](#DELETE-/categorys/{id})
    - [GET categorys/{categorysid}/bicycle](#GET-categorys/{categorysid}/bicycle)
    - [GET categorys/{categorysid}/bicycle/{bicycleid}](#GET-categorys/{categorysid}/bicycle/{bicycleid})
    - [POST categorys/{categorysid}/bicycle/{bicycleid}](#POST-categorys/{categorysid}/bicycle/{bicycleid})
    - [PUT categorys/{categorysid}/bicycle](#PUT-categorys/{categorysid}/bicycle)
    - [DELETE categorys/{categorysid}/bicycle/{bicycleid}](#DELETE-categorys/{categorysid}/bicycle/{bicycleid}])
  - [Recurso Brand](#recurso-brand)
    - [GET /brands](#GET-/brands)
    - [GET /brands/{id}](#GET-/brands/{id})
    - [POST /brands](#POST-/brands)
    - [PUT /brands/{id}](#PUT-/brands/{id})
    - [DELETE /brands/{id}](#DELETE-/brands/{id})
    - [GET brands/{brandsid}/bicycle](#GET-brands/{brandsid}/bicycle)
    - [GET brands/{brandsid}/bicycle/{bicycleid}](#GET-brands/{brandsid}/bicycle/{bicycleid})
    - [POST brands/{brandsid}/bicycle/{bicycleid}](#POST-brands/{brandsid}/bicycle/{bicycleid})
    - [PUT brands/{brandsid}/bicycle](#PUT-brands/{brandsid}/bicycle)
    - [DELETE brands/{brandsid}/bicycle/{bicycleid}](#DELETE-brands/{brandsid}/bicycle/{bicycleid}])

# API Rest
## Introducción
La comunicación entre cliente y servidor se realiza intercambiando objetos JSON. Para cada entidad se hace un mapeo a JSON, donde cada uno de sus atributos se transforma en una propiedad de un objeto JSON. Todos los servicios se generan en la URL /bicico.api/api/. Por defecto, todas las entidades tienen un atributo `id`, con el cual se identifica cada registro:

```javascript
{
    id: '',
    attribute_1: '',
    attribute_2: '',
    ...
    attribute_n: ''
}
```

Cuando se transmite información sobre un registro específico, se realiza enviando un objeto con la estructura mencionada en la sección anterior.
La única excepción se presenta al solicitar al servidor una lista de los registros en la base de datos, que incluye información adicional para manejar paginación de lado del servidor en el header `X-Total-Count` y los registros se envían en el cuerpo del mensaje como un arreglo.

La respuesta del servidor al solicitar una colección presenta el siguiente formato:

```javascript
[{}, {}, {}, {}, {}, {}]
```

## API de la aplicación bicico
### Recurso Bicycle
El objeto Bicycle tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo */
}
```

#### Representación Detail
```javascript
{
    // todo lo de la representación Minimum más los objetos Minimum con relación simple.
    brand: {
    id: '' /*Tipo Long*/,
    name: '' /*Tipo */    },
    category: {
    id: '' /*Tipo Long*/,
    name: '' /*Tipo */    }
}
```



#### GET /bicycles

Retorna una colección de objetos Bicycle en representación Detail.
Cada Bicycle en la colección tiene embebidos los siguientes objetos: Brand, Category.

#### Parámetros

#### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de [representaciones Detail](#recurso-bicycle)
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### GET /bicycles/{id}

Retorna una colección de objetos Bicycle en representación Detail.
Cada Bicycle en la colección tiene los siguientes objetos: Brand, Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Bicycle a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Bicycle en [representaciones Detail](#recurso-bicycle)
404|No existe un objeto Bicycle con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### POST /bicycles

Es el encargado de crear objetos Bicycle.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto Bicycle que será creado|Sí|[Representación Detail](#recurso-bicycle)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Bicycle ha sido creado|[Representación Detail](#recurso-bicycle)
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo crear el objeto Bicycle|Mensaje de error

#### PUT /bicycles/{id}

Es el encargado de actualizar objetos Bicycle.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Bicycle a actualizar|Sí|Integer
body|body|Objeto Bicycle nuevo|Sí|[Representación Detail](#recurso-bicycle)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Bicycle actualizado|[Representación Detail](#recurso-bicycle)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar el objeto Bicycle|Mensaje de error

#### DELETE /bicycles/{id}

Elimina un objeto Bicycle.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Bicycle a eliminar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error


#### GET bicycles/{bicyclesid}/photoAlbum

Retorna una colección de objetos PhotoAlbum asociados a un objeto Bicycle en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Bicycle a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de objetos PhotoAlbum en [representación Detail](#recurso-photoalbum)
500|Error consultando photoAlbum |Mensaje de error

#### GET bicycles/{bicyclesid}/photoAlbum/{photoAlbumid}

Retorna un objeto PhotoAlbum asociados a un objeto Bicycle en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
bicyclesid|Path|ID del objeto Bicycle a consultar|Sí|Integer
photoAlbumid|Path|ID del objeto PhotoAlbum a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto PhotoAlbum en [representación Detail](#recurso-photoalbum)
404|No existe un objeto PhotoAlbum con el ID solicitado asociado al objeto Bicycle indicado |Mensaje de error
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### POST bicycles/{bicyclesid}/photoAlbum/{photoAlbumid}

Asocia un objeto PhotoAlbum a un objeto Bicycle.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
bicyclesid|PathParam|ID del objeto Bicycle al cual se asociará el objeto PhotoAlbum|Sí|Integer
photoAlbumid|PathParam|ID del objeto PhotoAlbum a asociar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto PhotoAlbum asociado|[Representación Detail de PhotoAlbum](#recurso-photoalbum)
500|No se pudo asociar el objeto PhotoAlbum|Mensaje de error

#### PUT bicycles/{bicyclesid}/photoAlbum

Es el encargado de actualizar un objeto PhotoAlbum asociada a un objeto Bicycle.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
bicyclesid|Path|ID del objeto Bicycle cuya colección será remplazada|Sí|Integer
body|body|Colección de objetos PhotoAlbum|Sí|[Representación Detail](#recurso-photoalbum)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Se actualizo el objeto|Objeto PhotoAlbum en [Representación Detail](#recurso-photoalbum)
500|No se pudo actualizar|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### DELETE bicycles/{bicyclesid}/photoAlbum/{photoAlbumid}

Remueve un objeto PhotoAlbum asociado a un objeto Bicycle.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
bicyclesid|Path|ID del objeto Bicycle asociado al objeto PhotoAlbum|Sí|Integer
photoAlbumid|Path|ID del objeto PhotoAlbum a remover|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto removido|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error


[Volver arriba](#tabla-de-contenidos)
### Recurso Category
El objeto Category tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo */
}
```




#### GET /categorys

Retorna una colección de objetos Category en representación Detail.

#### Parámetros

#### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de [representaciones Detail](#recurso-category)
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### GET /categorys/{id}

Retorna una colección de objetos Category en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Category a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Category en [representaciones Detail](#recurso-category)
404|No existe un objeto Category con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### POST /categorys

Es el encargado de crear objetos Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto Category que será creado|Sí|[Representación Detail](#recurso-category)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Category ha sido creado|[Representación Detail](#recurso-category)
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo crear el objeto Category|Mensaje de error

#### PUT /categorys/{id}

Es el encargado de actualizar objetos Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Category a actualizar|Sí|Integer
body|body|Objeto Category nuevo|Sí|[Representación Detail](#recurso-category)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Category actualizado|[Representación Detail](#recurso-category)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar el objeto Category|Mensaje de error

#### DELETE /categorys/{id}

Elimina un objeto Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Category a eliminar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### GET categorys/{categorysid}/bicycle

Retorna una colección de objetos Bicycle asociados a un objeto Category en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Category a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de objetos Bicycle en [representación Detail](#recurso-bicycle)
500|Error consultando bicycle |Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### GET categorys/{categorysid}/bicycle/{bicycleid}

Retorna un objeto Bicycle asociados a un objeto Category en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
categorysid|Path|ID del objeto Category a consultar|Sí|Integer
bicycleid|Path|ID del objeto Bicycle a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Bicycle en [representación Detail](#recurso-bicycle)
404|No existe un objeto Bicycle con el ID solicitado asociado al objeto Category indicado |Mensaje de error
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### POST categorys/{categorysid}/bicycle/{bicycleid}

Asocia un objeto Bicycle a un objeto Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
categorysid|PathParam|ID del objeto Category al cual se asociará el objeto Bicycle|Sí|Integer
bicycleid|PathParam|ID del objeto Bicycle a asociar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Bicycle asociado|[Representación Detail de Bicycle](#recurso-bicycle)
500|No se pudo asociar el objeto Bicycle|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### PUT categorys/{categorysid}/bicycle

Es el encargado de remplazar la colección de objetos Bicycle asociada a un objeto Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
categorysid|Path|ID del objeto Category cuya colección será remplazada|Sí|Integer
body|body|Colección de objetos Bicycle|Sí|[Representación Detail](#recurso-bicycle)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Se remplazó la colección|Colección de objetos Bicycle en [Representación Detail](#recurso-bicycle)
500|No se pudo remplazar la colección|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### DELETE categorys/{categorysid}/bicycle/{bicycleid}

Remueve un objeto Bicycle de la colección en un objeto Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
categorysid|Path|ID del objeto Category asociado al objeto Bicycle|Sí|Integer
bicycleid|Path|ID del objeto Bicycle a remover|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto removido|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error



[Volver arriba](#tabla-de-contenidos)
### Recurso Brand
El objeto Brand tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo */
}
```




#### GET /brands

Retorna una colección de objetos Brand en representación Detail.

#### Parámetros

#### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de [representaciones Detail](#recurso-brand)
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### GET /brands/{id}

Retorna una colección de objetos Brand en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Brand a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Brand en [representaciones Detail](#recurso-brand)
404|No existe un objeto Brand con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### POST /brands

Es el encargado de crear objetos Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto Brand que será creado|Sí|[Representación Detail](#recurso-brand)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Brand ha sido creado|[Representación Detail](#recurso-brand)
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo crear el objeto Brand|Mensaje de error

#### PUT /brands/{id}

Es el encargado de actualizar objetos Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Brand a actualizar|Sí|Integer
body|body|Objeto Brand nuevo|Sí|[Representación Detail](#recurso-brand)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Brand actualizado|[Representación Detail](#recurso-brand)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar el objeto Brand|Mensaje de error

#### DELETE /brands/{id}

Elimina un objeto Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Brand a eliminar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### GET brands/{brandsid}/bicycle

Retorna una colección de objetos Bicycle asociados a un objeto Brand en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Brand a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de objetos Bicycle en [representación Detail](#recurso-bicycle)
500|Error consultando bicycle |Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### GET brands/{brandsid}/bicycle/{bicycleid}

Retorna un objeto Bicycle asociados a un objeto Brand en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
brandsid|Path|ID del objeto Brand a consultar|Sí|Integer
bicycleid|Path|ID del objeto Bicycle a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Bicycle en [representación Detail](#recurso-bicycle)
404|No existe un objeto Bicycle con el ID solicitado asociado al objeto Brand indicado |Mensaje de error
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### POST brands/{brandsid}/bicycle/{bicycleid}

Asocia un objeto Bicycle a un objeto Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
brandsid|PathParam|ID del objeto Brand al cual se asociará el objeto Bicycle|Sí|Integer
bicycleid|PathParam|ID del objeto Bicycle a asociar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Bicycle asociado|[Representación Detail de Bicycle](#recurso-bicycle)
500|No se pudo asociar el objeto Bicycle|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### PUT brands/{brandsid}/bicycle

Es el encargado de remplazar la colección de objetos Bicycle asociada a un objeto Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
brandsid|Path|ID del objeto Brand cuya colección será remplazada|Sí|Integer
body|body|Colección de objetos Bicycle|Sí|[Representación Detail](#recurso-bicycle)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Se remplazó la colección|Colección de objetos Bicycle en [Representación Detail](#recurso-bicycle)
500|No se pudo remplazar la colección|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### DELETE brands/{brandsid}/bicycle/{bicycleid}

Remueve un objeto Bicycle de la colección en un objeto Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
brandsid|Path|ID del objeto Brand asociado al objeto Bicycle|Sí|Integer
bicycleid|Path|ID del objeto Bicycle a remover|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto removido|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error



[Volver arriba](#tabla-de-contenidos)
