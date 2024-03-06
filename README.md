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
- `Direction`
- `Juego2048`


### 3. Clases Senku
- `SenkuBoard`
- `Senku`
  

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

4. ItemMenu

ItemMenu es la representación de cada opción en el menú este está compuesto de un título, el id del imageResource y Class que es la actividad de la respectiva opcion. Esta clase tiene un método estático que devuelve todas las opciones
```java
public static ArrayList<ItemMenu> opciones() {
   ArrayList<ItemMenu> options = new ArrayList<ItemMenu>();
   options.add(new ItemMenu("2048", R.drawable.ic_2048, juego2048.class));
   options.add(new ItemMenu("Senku", R.drawable.ic_senku, Senku.class));
   options.add(new ItemMenu("Score", R.drawable.ic_score, ScoreActivity.class));
   options.add(new ItemMenu("Settings", R.drawable.ic_settings, Setting.class));
   return options;
}
```

5. Setting

Esta actividad representa los ajustes en la aplicación , en la parte de arriba puedes cambiar tu contraseña. Pones tu contraseña actual y tu nueva contraseña , y si tu contraseña actual es correcta te la deja cambiar y si la pones erronea te salta un alerta de contraseña incorrecta. 
![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/c471ebc4-d1d2-413c-a807-46e243778c39)


La parte en enmedio está la opción de cambiar la foto de perfil, esta no puede ser mayor a un 1MB. Ademas te sale al lado del boton tu foto actual, y si la cambias se actualiza. Esta foto de perfil se guarda en la database. 

![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/77d8ff6e-1b89-4f8c-b9cd-9eb451d06888)

Y después de cambiarla 

![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/8a9ba23c-2f28-4169-ac46-4e0edd2b2d63)

Y las opciones de abajo son Volver al menú principal que simplemente te lleva al menú principal y cerrar sesión que te lleva a gamecenter que es donde te logeas.También hay un botón de añadir datos , para tener datos ficticios guardados para hacer pruebas. 
![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/b2c30f41-2b7d-407f-ade6-e4c46affab99)

6. SplashActivity

Esta actividad es simplemente una imagen con una animación cada vez que se inicia la aplicación 

![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/5800d0a6-34ff-4733-b97e-63f5b5513d42)


## Clases 2048

1. Board2048

Esta clase representa la lógica del juego 2048. El tablero está representado en un array bidimensional de enteros donde van los números , y donde 0 es la representación de vacío. tenemos otro array bidimensional para guardar el estado anterior para poder deshacer un movimimento. En esta clase hacemos los movimientos , el movimiento sea en cualquier dirección , es llevar mover las piezas en ese movimiento si no hay ninguna otra pieza y poder fusionarse si la pieza de al lado es igual.

En esta clase comprobamos si está la partida terminada eso es cuando hay un "2048" donde es una victoria o si no se puede hacer ningun otro movimiento. Y tenemos el método undoMove para volver al estado anterior del tablero.

2. Direction

Esta clase es un enumerado que representa los posibles movimientos (arriba,abajo,izquierda,derecha)

3. Juego 2048

Esta clase es la actividad donde se va a jugar al 2048. Se crea una instancia del board2048 y un tableLayout 4x4 definido en el XML, y para cada casilla del board2048  se representa en cada su casilla del tablelayout. Para poder jugar necesitamos poder deslizar hacia un lado y hacer un movimiento en el juego ,eso se consigue con la clase "EscucharGestos"  donde vemos que gesto hace y llamamos a la función move, en esta vemos si el juego ya había terminado no hacemos nada, si el juego no ha terminado hacemos un sonido de movimiento, llamamos a que se mueva el board2048 y repintamos el tableLayout, despúes de hacer el movimiento vemos si ahora ha terminado y aqui si ha terminado lanzamos un sonido y una alerta para notificar que el juego ha terminado. Cada vez que se termina el juego se guarda la puntuación en la database, ganes o pierdas

En esta clase a parte del juego en sí en la parte superior tenemos el tiempo de juego de cada partida, tu puntuación actual y la puntuación máxima que has obtenido guardad en la base de datos. Y un botón de ir al menú principal.

En la parte de abajo tenemos un botón de hacer una partida nueva, que reinicia el boars2048,el timer y tu puntuación actual; y otro botón de Undo move que devuelve a un estado anterior

![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/1020f9ed-d6e3-4dd8-a6c6-53d8e760175f)

Alerta de fin de juego: 

![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/fddc130c-dbfa-4ad8-9bb0-b9bef2f5b212)



## Clases Senku

1. SenkuBoard

Esta clase representa la lógica del senkugame. Tiene un arraybidimensional que representa el tablero, este puede tener tres estados. 1 si hay una casilla ocupada,0 si está vacía y -1 las casillas ocultas. El movimiento en el senku viene determinado por dos casillas presionadas , la primera está ocupada y la segunda vacía, y ademas la de enmedio de las 2 tiene que estar ocupada, todo movimiento diferente a este es un movimiento erroneo. El juego se acaba cuando no hay más movimientos posibles, y se mira si hay casillas activas , cuando hay 1 se gana y si hay más es que has perdido. Tenemos una función de undo que devuelve el tablero a un estado anterior.

2. Senku

Esta clase es donde se va a jugar al senku, para ello necesitamos una instancia del senku board y para representarlo graficamente se usa un gridLayout donde en cada posicion va un círculo azul si esta ocupada o un círculo blanco si está vacia, cuando se elige una casilla se pone amarilla para identificar cual has presionado y despues de elegir la segunda casilla se quita, y se repinta el tablero. En cada movimiento, si es un movimiento erroneo, vibra el dispositivo y crea un toast para indicarlo. Y se mira si ha acabado el juego , si ganas te guarda la puntuación en la base de datos, el tiempo que tardas en acabar, y si pierdes no te guarda la puntuación. Ganes o pierdas te sale una alerta y un sonido para decirte que acaba el juego.

![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/ad187a6c-05dc-4f05-8cdb-3f78de2f2aeb) 

Alerta de fin de juego:


![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/f1bcc8a3-7e40-457b-aa7e-af9bb097e078)

## Clases Datos

1. Database

Esta clase representa la base de datos de la aplicación.Tenemos 3 tablas , Users que tiene un name,password y photo ; que son los datos de los usuarios registrados en la aplicación. Score2048 y scoreSenku son las tablas donde se guardan las puntuaciones de los usuarios , que tiene el nombre del usuario y la puntuación que ha hecho.

   ```java
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Score2048(name TEXT, score INT)");
        DB.execSQL("create Table ScoreSenku(name TEXT, score INT)");
        DB.execSQL("create Table Users(name TEXT, password TEXT,photo BLOB)");
    }
   ````
   
Ademas de crear las tablas tenemos los inserts para cada usuario o puntuación. El update para cambiar la foto o la contraseña del usuario y los selects para ver los datos.

2. Score

Esta clase representa una puntuación con un String para el nombre y un int para la puntuación en si. Destacar que en el senku se guarda el tiempo , los segundos, por ello en esta clase hay un convertidor de un int de segundos a un string de tiempo

3. ScoreActivity

Esta actividad vemos los registros guardados de las puntuaciones para ellos tenemos 2 recyclersview ,uno para cada juego. Tenemos 2 chechboxes si queremos ver las puntuaciones de todos los usuarios y otro checkbox para ver las puntuaciones ordenadas. Un boton para aplicar los cambios de la chechbox, un botón para resetear todos los datos. y un botón para volver al menú 



Solo tus puntuaciones: 

![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/d29a1dae-54d8-49dc-a0a0-d46fbd4428d6) 


Todas las puntuaciones:  
 
![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/87bd2f91-f055-43b3-9071-d559f8caff8b) 



Y las puntuaciones ordenadas: 

![image](https://github.com/michaelgarciam1/Juegos/assets/114613053/3082b320-3a1c-4e6f-958b-b940873e4a9b) 


4. ScoreAdapter

Esta clase es el adaptador del recyclerView para los scores de cada juego , para ello necesitamos un arraylist de un juego. y Se mostrará una lista de los scores.

## Otras clases 

1. Util

Clase creada para poner todos los métodos que se utilizan en mas de una clase y no tener código repetido. Como las alertas donde se crea un AlertDialog con el mensaje que le pasas. Getusername , devuelve el usuario actual guardado en sharedPreference , y un formatedTime para cambiar los segundos a un string con formato 00:00











   






