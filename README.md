# Laberinto

El juego consiste en diseñar una estrategia con el proyecto base y rellenar el método `calculateRoute()` de la clase `Game` para que el jugador P (Player)llegue al final del laberinto E (End). Se disponen de las siguientes clases:

  - **Coordinate:** Coordenada en el mapa con la posición horizontal y vertical
  - **Element:** Elemento que se encontrará en el mapa, a distinguir entre:
      - _Player_: Jugador del laberinto. Representado por una 'P'.
      - _End_: Fin del laberinto. Representado por una 'E'.
      - _Obstacle_: Obstáculo (muros) encontrado durante el recorrido del laberinto. Representado por símbolos como '-', '+' o '|'
      - _Void_: Hueco vacío no recorrido en el laberinto. Representado por un carácter vacío ' '.
      - _Travelled_: Hueco o casilla recorrida por el jugador. Representado por '*'.
  - **MovementDirection:** Movimiento realizado en una dirección que puede ser tanto recta como diagonal. Cada objeto de la clase irá acompañado por las coordenadas que modifican sobre la clase Coordinate. Los elementos de la clase serán *UP*, *UP_RIGHT*, *UP_LEFT*, *RIGHT*, *LEFT*, *DOWN*, *DOWN_LEFT*, *DOWN_RIGHT*.
  - **LabyrinthMap:** Mapa que implementa el laberinto a partir de un fichero del proyecto y que otorga todas las herramientas relacionadas al mismo para que el juego funcione correctamente.
  - **Game:** La clase que se ocupa del juego por completo implementando todas las clases anteriores. Tiene todas las herramientas básicas definidas a falta de la implementación del método que calcula la ruta.
  - **App:** Clase hecha con WindowBuilder y con la parte visual necesaria para ver el laberinto (formado por caracteres ASCII) y el recorrido una vez se ha realizado. Se avisará del tiempo necesitado para el cálculo de la ruta y de un tiempo estimado (que consta de una fórmula implementada) para saber, dada la ruta, cuánto tiempo se tardaría en llegar al final.

## Instrucciones de uso

1. Se podrá ver el código completo y el mapa, incluida una solución ejemplo de resolución del juego[^1], aunque por supuesto ésta no es la única.
2. Se requiere la escritura del método `calculateRoute()` que realice la lógica interna del jugador para alcanzar el final del laberinto en el menor tiempo posible.
3. Evidentemente, se pueden realizar muchas rutas de reconocimiento por el mapa para averiguar el camino más rápido, pero debe tenerse en cuenta que el método con la solución más óptima no siempre es aquella que realiza los cálculos más rápidos. Se requiere, por tanto, un equilibrio entre ambas.

## Ejemplo de laberinto a resolver
![Ejemplo de laberinto](https://github.com/JoseMartinBellido/Laberinto/blob/main/Laberinto.png)

### Notas al pie

[^1]: Anímate a resolverlo sin mirar la solución proporcionada, aunque siempre se le puede echar un vistazo para coger ideas :wink:
