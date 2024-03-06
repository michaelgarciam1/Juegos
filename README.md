### JUEGOS
## Introducción
Este documento es la documentación de la aplicación de "Juegos" , como práctica final de aplicaciones móviles. Hecha completamente en Android Studio con java en la parte de código, y XML en la parte de diseño.
La aplicación se centra en un game center , es decir, una aplicación donde se alojan varios juegos y poder guardar en un único sitio los scores y datos de cada uno. En este gamecenter tenemos dos juegos : el 2048
y el senku game.

El 2048 es un juego que se basa en combinar números para alcanzar la cifra de 2048. Se juega en un tablero de 4x4 donde cada casilla contiene un número que es potencia de 2, empezando por 2. El jugador realiza movimientos deslizando las fichas en cuatro direcciones (arriba, abajo, izquierda o derecha). Cuando dos fichas con el mismo número chocan al deslizarse en la misma dirección, se combinan en una sola casilla con el doble del valor. El objetivo es alcanzar la casilla con el número 2048


El Senku es un juego donde se inicia con un tablero preestablecido con fichas dispuestas según un patrón específico, dejando una ficha central vacía. El objetivo es eliminar las fichas saltando sobre ellas en direcciones adyacentes, eliminando así las saltadas. El juego continúa hasta que no es posible realizar más movimientos, y la meta final es dejar solo una ficha en el tablero.

## Resumen de clases

### 1. Clases Menu
- `Gamecenter`
- `MainActivity`
- `MenuAdapter`
- `ItemMenu`
- `Setting`
- `SplashActivity`


### 2. Clases 2048
- `Board2048`
- `Juego2048`
- `Direction`

### 3. Clases Senku
- `Senku`
- `SenkuBoard`

### 4. Datos
- `Database`
- `Score`
- `ScoreActivity`
- `ScoreAdapter`

### 5. Otras clases
- `Util`


## Clases Menu

1. GameCenter

Esta clase es una actividad que representa el login en la aplicación. Cuenta de dos entradas de texto , la cual una es el Username del usuario y el otro es la contraseña. Y de dos botones , "Login" para entrar y "Crear Usuario" para crear un usuario. Los Usernames y las contraseñas de los usuarios se guardan en la database interna y es por ello que el username debe ser único. Esto se demuestra al darle al boton de crear usuario te sale una alerta por si intentas crear un usuario con el mismo nombre. Para el login, se comprueban los campos correspondientes y se mira en la database si está registrado, si lo está te lleva al menú principal y si no te sale una alerta de usuario no registrado. Esta actividad cuenta con música de fondo.
   
   ![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/4101088c-e918-41a6-8ec6-aa2e75eff0a8) 

2. MainActivity

Esta actividad representa el menú principal de la aplicación. En la parte de arriba te muestra el usuario que ha iniciado sesión y tu foto de perfil, te pone una por defecto, y abajo te muestra un recyclerView que hace como las diferentes opciones que hay en la aplicación, que en este caso cuenta con 4 opciones: 2048,Senku,Score,Settings. Y esta actividad cuenta con música de fondo.

![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/8352689d-51c4-4f0e-91fd-4f6cc26a5d63)

3. MenuAdapter

Esta clase es el adaptador del recyclerView para el menu. Para ello le pasamos un arrayList de ItemMenu ,que es la clase que representa las opciones de menu. He utilizado el mismo adaptador que hemos usado en clase con la diferencia que ItemMenu guarda su actividad , y al hacer click en el recyclerView va a su actividad correspondiente.




   






