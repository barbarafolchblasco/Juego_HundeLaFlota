import java.util.Scanner;

public class PVFinal_HundirFlota
{

    public static void main(String[] args)
    {
        System.out.println("------------------------------------------------------");
        System.out.println("                         JUEGO                        ");
        System.out.println("                **** HUNDE LA FLOTA ****              ");
        System.out.println("------------------------------------------------------");

        System.out.println("------------------------------------------------------");
        System.out.println("          //////   ¡EL JUEGO EMPIEZA!   //////               ");
        System.out.println("------------------------------------------------------\n");


        int[] barcos = {5, 4, 3, 3, 2};


        int puntosParaGanar = sumaCeldas(barcos);


        char[][] tableroJugador = new char[10][10];
        char[][] tableroDisparosJugador = new char[10][10];
        char[][] tableroPC = new char[10][10];
        char[][] tableroDisparosPC = new char[10][10];


        inicializarTablero(tableroJugador);
        inicializarTablero(tableroPC);
        inicializarTablero(tableroDisparosJugador);
        inicializarTablero(tableroDisparosPC);


        colocarBarcosJugador(tableroJugador, tableroDisparosJugador, barcos);
        colocarBarcosPC(tableroPC, barcos);


        boolean finJuego = false;
        int puntosJugador = 0;
        int puntosPC = 0;

        while (finJuego == false)
        {
            borrarPantalla();

            System.out.println("\n+++++++++++++++++++ SIGUIENTE RONDA ++++++++++++++++++\n");

            System.out.println("------------------------------------------------------");
            System.out.println("PUNTOS JUGADOR: " + puntosJugador + " / " + puntosParaGanar +
                    "     ||      " + "PUNTOS PC: " + puntosPC + " / " + puntosParaGanar);
            System.out.println("------------------------------------------------------\n");


            visualizarTablero(tableroJugador, tableroDisparosJugador);

            boolean aciertoJugador = disparoJugador(tableroJugador, tableroDisparosJugador, tableroPC);

            if(aciertoJugador == true)
            {
                puntosJugador++;
            }

            if (puntosJugador >= puntosParaGanar)
            {
                System.out.println("------------------------------------------------------");
                System.out.println("            HAS HUNDIDO LA FLOTA DEL PC :)            ");
                System.out.println("                    FIN DEL JUEGO                     ");
                System.out.println("------------------------------------------------------");
                finJuego = true;
                break;
            }


            boolean aciertoPC = disparoPC(tableroPC, tableroDisparosPC, tableroJugador);

            System.out.println();
            if (aciertoPC == true)
            {
                puntosPC++;
            }

            if (puntosPC >= puntosParaGanar)
            {
                System.out.println("------------------------------------------------------");
                System.out.println("           EL PC TE HA HUNDIDO LA FLOTA :(            ");
                System.out.println("                    FIN DEL JUEGO                     ");
                System.out.println("------------------------------------------------------");
                finJuego = true;
            }
        }
    }




    public static boolean disparoJugador (char[][] tableroJugador, char[][] tableroDisparosJugador, char[][] tableroPC)
    {
        Scanner scanner = new Scanner(System.in);

        int fila = 0;
        int columna = 0;
        boolean coordenadaValida = false;
        String coordenada;


        System.out.println("\n----------------- TU TURNO DE DISPARO -----------------");
        System.out.println("-------------------------------------------------------");


        int partesIntactasJugador = 0;

        for(int i = 0; i < tableroJugador.length; i++) {
            for(int j = 0; j < tableroJugador[i].length; j++) {
                if(tableroJugador[i][j] == 'B') {
                    partesIntactasJugador++;
                }
            }
        }
        System.out.println("\n(AVISO: Te quedan " + partesIntactasJugador + " partes de barco intactas)\n");


        do {
            System.out.println("Introduce la coordenada de tu disparo: ");
            coordenada = scanner.nextLine();
            coordenada = coordenada.toUpperCase();

            if (coordenada.length() != 2)
            {
                System.out.println("ERROR: Debes escribir una letra y un número (Ej: A5)");
                continue;
            }


            char letra = coordenada.charAt(0);
            char numeroChar = coordenada.charAt(1);

            if (numeroChar < '0' || numeroChar > '9')
            {
                System.out.println("ERROR: El segundo carácter debe ser un número");
                continue;
            }


            fila = letra - 'A';
            columna = numeroChar - '0';

            if (fila < 0 || fila > 9 || columna < 0 || columna > 9)
            {
                System.out.println("ERROR: Coordenada fuera del tablero");
                continue;
            }

            if (tableroDisparosJugador[fila][columna] != '~')
            {
                System.out.println("ERROR: Ya has disparado ahí antes... Elige otra coordenada");
                continue;
            }

            coordenadaValida = true;

        }while (coordenadaValida == false);


        if (tableroPC[fila][columna] == 'B')
        {
            System.out.println("\n+++++++++++++++++ ¡IMPACTO! BARCO TOCADO ++++++++++++++++");
            tableroDisparosJugador[fila][columna] = 'X';
            tableroPC[fila][columna] = 'X';
            return true;

        } else {

            System.out.println("\n+++++++++++++++ ¡AGUA! DISPARO FALLIDO +++++++++++++++");
            tableroDisparosJugador[fila][columna] = '-';
            return false;
        }
    }




    public static boolean disparoPC(char[][] tableroPC, char[][] tableroDisparosPC, char[][] tableroJugador)
    {
        System.out.println("\n-------------------- TURNO DEL PC --------------------");
        System.out.println("------------------------------------------------------");


        int partesIntactasPC = 0;

        for(int i = 0; i < tableroPC.length; i++) {
            for(int j = 0; j < tableroPC[i].length; j++) {
                if(tableroPC[i][j] == 'B') {
                    partesIntactasPC++;
                }
            }
        }

        System.out.println("\n(AVISO: Al PC le quedan " + partesIntactasPC + " partes de su flota)\n");


        int fila;
        int columna;
        boolean casillaValidaPC = false;

        do {
            fila = (int)(Math.random() * 10);
            columna = (int)(Math.random() * 10);

            if (tableroJugador[fila][columna] != 'X' && tableroJugador[fila][columna] != '-') {
                casillaValidaPC = true;
            } else {
                casillaValidaPC = false;
            }

        }while (casillaValidaPC == false);


        char letraFila = (char)('A' + fila);
        System.out.println("El PC ha disparado en la coordenada: " + letraFila + columna);


        if (tableroJugador[fila][columna] == 'B') {
            System.out.println("\n+++++++++++++++++ ¡IMPACTO! BARCO TOCADO ++++++++++++++++");
            tableroDisparosPC[fila][columna] = 'X';
            tableroJugador[fila][columna] = 'X';
            return true;
        }
        else {
            System.out.println("\n+++++++++++++++ ¡AGUA! DISPARO FALLIDO +++++++++++++++");
            tableroJugador[fila][columna] = '-';
            return false;
        }
    }




    public static void inicializarTablero(char[][] tablero)
    {
        for (int filas = 0; filas < tablero.length; filas++)
        {
            for (int columnas = 0; columnas < tablero[filas].length; columnas++)
            {
                tablero[filas][columnas] = '~';
            }
        }
    }




    public static void visualizarTablero(char[][] tablero, char[][] tableroDisparos)
    {
        char[] numColumnas = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] letrasFilas = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};


        System.out.println("    [  TU FLOTA  ]                 [  TUS DISPAROS  ]");
        System.out.println("---------------------            ---------------------");

        System.out.print("  ");
        for (int i = 0; i < numColumnas.length; i++)
        {
            System.out.print(numColumnas[i] + " ");
        }


        System.out.print("     |     ");


        System.out.print("  ");
        for (int i = 0; i < numColumnas.length; i++)
        {
            System.out.print(numColumnas[i] + " ");
        }

        System.out.println(" ");



        for (int filas = 0; filas < tablero.length; filas++)
        {
            System.out.print(letrasFilas[filas] + " ");
            for (int columnas = 0; columnas < tablero[filas].length; columnas++)
            {
                System.out.print(tablero[filas][columnas] + " ");
            }


            System.out.print("     |     ");


            System.out.print(letrasFilas[filas] + " ");
            for (int columnas = 0; columnas < tableroDisparos[filas].length; columnas++)
            {
                System.out.print(tableroDisparos[filas][columnas] + " ");
            }

            System.out.println();
        }
    }




    public static void borrarPantalla()
    {
        for (int i = 0; i < 20; i++)
        {
            System.out.println();
        }
    }




    public static int sumaCeldas(int[] unVector)
    {
        int sumaCeldasVector = 0;

        for (int i = 0; i < unVector.length; i++)
        {
            sumaCeldasVector += unVector[i];
        }

        return sumaCeldasVector;
    }




    public static void colocarBarcosJugador(char[][] tablero, char[][] tableroDisparos, int[] barcos)
    {
        Scanner scanner = new Scanner(System.in);

        int barcosYaColocados = 0;
        boolean[] barcosUsados = new boolean[5];

        while (barcosYaColocados < 5)
        {
            int indiceBarcoElegido = -1;
            int longitudBarco = 0;
            int orientacion = -1;
            int fila = -1;
            int columna = -1;


            visualizarTablero(tablero, tableroDisparos);
            System.out.println("\n------------------------------------------------------");
            System.out.println("                      " + barcosYaColocados + " / 5 barcos         ");
            System.out.println("               Se han colocado por ahora                ");
            System.out.println("------------------------------------------------------\n");


            boolean barcoValido = false;
            while (barcoValido == false)
            {
                String opcionA = "A) Portaaviones (Ocupa 5 celdas)";
                String opcionB = "B) Acorazado (Ocupa 4 celdas)";
                String opcionC = "C) Submarino nº1 (Ocupa 3 celdas)";
                String opcionD = "D) Submarino nº2 (Ocupa 3 celdas)";
                String opcionE = "E) Lancha (Ocupa 2 celdas)\n";


                if (barcosUsados[0] == true) {
                    opcionA = "A) Portaaviones (¡UPS! Barco ya colocado...)";
                }

                if (barcosUsados[1] == true) {
                    opcionB = "B) Acorazado (¡UPS! Barco ya colocado...)";
                }

                if (barcosUsados[2] == true) {
                    opcionC = "C) Submarino nº1 (¡UPS! Barco ya colocado...)";
                }

                if (barcosUsados[3] == true) {
                    opcionD = "D) Submarino nº2 (¡UPS! Barco ya colocado...)";
                }

                if (barcosUsados[4] == true) {
                    opcionE = "E) Lancha (¡UPS! Barco ya colocado...)";
                }


                System.out.println("Elige el tipo de barco para colocarlo en el tablero: ");
                System.out.println(opcionA);
                System.out.println(opcionB);
                System.out.println(opcionC);
                System.out.println(opcionD);
                System.out.println(opcionE);
                System.out.println("------------------------------------------------------");
                System.out.println("Opción: ");


                String letraBarcoElegido = scanner.nextLine();

                if (letraBarcoElegido.length() != 1) {
                    System.out.println("\nERROR: Introduce solo las opciones A, B, C, D o E\n");
                    continue;
                }

                letraBarcoElegido = letraBarcoElegido.toUpperCase();
                char letra = letraBarcoElegido.charAt(0);

                if (letra < 'A' || letra > 'E') {
                    System.out.println("\nERROR: Introduce solo las opciones A, B, C, D o E\n");
                    continue;
                }


                if (letra == 'A')
                {
                    indiceBarcoElegido = 0;
                    longitudBarco = barcos[indiceBarcoElegido];

                } else if (letra =='B') {
                    indiceBarcoElegido = 1;
                    longitudBarco = barcos[indiceBarcoElegido];

                } else if (letra =='C') {
                    indiceBarcoElegido = 2;
                    longitudBarco = barcos[indiceBarcoElegido];

                } else if (letra =='D') {
                    indiceBarcoElegido = 3;
                    longitudBarco = barcos[indiceBarcoElegido];

                } else if (letra =='E') {
                    indiceBarcoElegido = 4;
                    longitudBarco = barcos[indiceBarcoElegido];

                }else {
                    System.out.println("\nERROR: Opción no válida\n");
                    continue;
                }


                if (barcosUsados[indiceBarcoElegido] == true)
                {
                    System.out.println("\nERROR: Barco ya colocado. Elige otro...\n");
                }else {
                    barcoValido = true;
                }
            }



            boolean orientacionValida = false;
            while (orientacionValida == false)
            {
                System.out.println("\nIntroduce la orientación para que se coloque el barco \n(NOTA: 0 para horizontal, 1 para vertical): ");
                String entradaOrientacion = scanner.nextLine();

                if (entradaOrientacion.equals("0")) {
                    orientacion = 0;
                    orientacionValida = true;

                } else if (entradaOrientacion.equals("1")) {
                    orientacion = 1;
                    orientacionValida = true;

                } else {
                    System.out.println("\nDato introducido no válido. Prueba otra vez...");
                }
            }



            boolean coordenadaValida = false;
            while (coordenadaValida == false)
            {
                System.out.println("\nIntroduce la coordenada deseada \n(NOTA: Indicar 1º fila (letra) || 2º columna (número)): ");
                String coordenadaIntroducida = scanner.nextLine().toUpperCase();

                if (coordenadaIntroducida.length() != 2)
                {
                    System.out.println("\nCoordenada no válida :( Vuelve a intentarlo...");
                    continue;
                }


                char letraFila = coordenadaIntroducida.charAt(0);
                char numeroColumna = coordenadaIntroducida.charAt(1);

                if (numeroColumna < '0' || numeroColumna > '9')
                {
                    System.out.println("\nERROR: El segundo carácter debe ser un número");
                    continue;
                }


                fila = letraFila - 'A';
                columna = numeroColumna - '0';

                if (fila < 0 || fila > 9 || columna < 0 || columna > 9)
                {
                    System.out.println("\nERROR: Fuera de rango (A-J, 0-9)");
                    continue;
                }

                coordenadaValida = true;
            }


            boolean barcoColocadoValido = colocarBarco(tablero, longitudBarco, fila, columna, orientacion, true);

            if (barcoColocadoValido == true) {
                barcosUsados[indiceBarcoElegido] = true;
                barcosYaColocados++;
                System.out.println("\n++++++++++++ BARCO COLOCADO CORRECTAMENTE ++++++++++++\n");

            }else{
                System.out.println("ERROR: No se pudo colocar (se sale o choca). Inténtalo de nuevo...\n");
            }
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("           TODA LA FLOTA HA SIDO COLOCADA :)            ");
        System.out.println("------------------------------------------------------\n");
    }




    public static void colocarBarcosPC(char[][] tablero, int[] barcos)
    {
        System.out.println("\n------------------ TURNO DEL PC ----------------------");

        for (int i = 0; i < barcos.length; i++)
        {
            boolean barcoColocado = false;

            do {
                int fila = (int)(Math.random() * 10);
                int columna = (int)(Math.random() * 10);
                int orientacion = (int)(Math.random() * 2);

                barcoColocado = colocarBarco(tablero, barcos[i], fila, columna, orientacion, false);

            }while (barcoColocado == false);
        }

        System.out.println("       El PC ya ha acabado de colocar su flota        ");
        System.out.println("------------------------------------------------------");
        System.out.println();
    }



    public static boolean hayColision(char[][] tablero, int longitudBarco, int fila, int columna, int orientacion)
    {
        boolean existeChoque = false;

        if (orientacion == 0) {
            for (int i = 0; i < longitudBarco; i++)
            {
                if (tablero[fila][columna + i] != '~')
                {
                    existeChoque = true;
                }
            }
        }else{
            for (int i = 0; i < longitudBarco; i++)
            {
                if (tablero[fila + i][columna] != '~')
                {
                    existeChoque = true;
                }
            }
        }

        return existeChoque;
    }




    public static boolean colocarBarco(char[][] tablero, int longitudBarco, int fila, int columna, int orientacion, boolean jugador)
    {
        boolean barcoSePuedeColocar = true;

        if (orientacion == 0)
        {
            if (columna + longitudBarco > 10)
            {
                barcoSePuedeColocar = false;

                if (jugador == true)
                {
                    System.out.println("\nAVISO: El barco se sale por la derecha\n");
                }
            }
        }else{

            if (fila + longitudBarco > 10)
            {
                barcoSePuedeColocar = false;

                if (jugador == true)
                {
                    System.out.println("\nAVISO: El barco se sale por por abajo\n");
                }
            }
        }


        if (barcoSePuedeColocar == true)
        {
            boolean existeChoque = hayColision(tablero, longitudBarco, fila, columna, orientacion);

            if (existeChoque == true)
            {
                barcoSePuedeColocar = false;
                if (jugador == true)
                {
                    System.out.println("\nAVISO: Ya hay un barco en esa posición\n");
                }
            }
        }


        if (barcoSePuedeColocar == true)
        {
            char letraBarco = 'B';

            if (orientacion == 0)
            {
                for (int i = 0; i < longitudBarco; i++)
                {
                    tablero[fila][columna + i] = letraBarco;
                }
            }else{
                for (int i = 0; i < longitudBarco; i++)
                {
                    tablero[fila + i][columna] = letraBarco;
                }
            }

            return true;

        }else{

            return false;
        }
    }
}