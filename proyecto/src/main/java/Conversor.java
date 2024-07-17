import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Conversor {

    public static void mostrarMenu() {
        System.out.println("Bienvenido al Conversor de Monedas");
        System.out.println("Selecciona una opción:");
        System.out.println("1. Dólar a Peso Argentino");
        System.out.println("2. Peso Argentino a Dólar");
        System.out.println("3. Real Brasileño a Dólar");
        System.out.println("4. Dólar a Real Brasileño");
        System.out.println("5. Euro a Dólar");
        System.out.println("6. Dólar a Euro");
        System.out.println("7. Salir");
    }

    public static double obtenerTasaDeCambio(String monedaOrigen, String monedaDestino) throws Exception {
        String apikey = "TU_API_KEY"; // Reemplaza con tu API Key
        String url = "https://api.exchangerateapi.com/v4/latest/" + monedaOrigen;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("apikey", apikey);
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parsear la respuesta JSON
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

            // Extraer la tasa de cambio
            double tasaDeCambio = jsonObject.getAsJsonObject("rates").get(monedaDestino).getAsDouble();

            // Devolver la tasa de cambio
            return tasaDeCambio;
        } else {
            throw new Exception("Error al obtener la tasa de cambio: " + responseCode);
        }
    }

    public static void main(String[] args) {
        mostrarMenu();

        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();

        double cantidadPesosArgentinos;
        double cantidadDolares;
        double cantidadRealesBrasileños;
        double cantidadEuros;

        switch (opcion) {
            case 1:
                // Código para convertir de Dólar a Peso Argentino
                System.out.print("Ingresa la cantidad de dólares a convertir: ");
                cantidadDolares = scanner.nextDouble();
                double tasaDeCambio = obtenerTasaDeCambio("USD", "ARS");
                cantidadPesosArgentinos = cantidadDolares * tasaDeCambio;
                System.out.println(cantidadDolares + " dólares equivalen a " + cantidadPesosArgentinos + " pesos argentinos.");
                break;
            case 2:
                // Código para convertir de Peso Argentino a Dólar
                System.out.print("Ingresa la cantidad de pesos argentinos a convertir: ");
                cantidadPesosArgentinos = scanner.nextDouble();
                tasaDeCambio = obtenerTasaDeCambio("ARS", "USD");
                cantidadDolares = cantidadPesosArgentinos / tasaDeCambio;
                System.out.println(cantidadPesosArgentinos + " pesos argentinos equivalen a " + cantidadDolares + " dólares.");
                break;
            case 3:
                // Código para convertir de Real Brasileño a Dólar
                System.out.print("Ingresa la cantidad de reales brasileños a convertir: ");
                cantidadRealesBrasileños = scanner.nextDouble();
                tasaDeCambio = obtenerTasaDeCambio("BRL", "USD");
                cantidadDolares = cantidadRealesBrasileños / tasaDeCambio;
                System.out.println(cantidadRealesBrasileños + " reales brasileños equivalen a " + cantidadDolares + " dólares.");
                break;

            case 4:
                // Código para convertir de Dólar a Real Brasileño
                System.out.print("Ingresa la cantidad de dólares a convertir: ");
                cantidadDolares = scanner.nextDouble();
                tasaDeCambio = obtenerTasaDeCambio("USD", "BRL");
                cantidadRealesBrasileños = cantidadDolares * tasaDeCambio;
                System.out.println(cantidadDolares + " dólares equivalen a " + cantidadRealesBrasileños + " reales brasileños.");
                break;
            case 5:
                // Código para convertir de Euro a Dólar
                System.out.print("Ingresa la cantidad de euros a convertir: ");
                cantidadEuros = scanner.nextDouble();
                tasaDeCambio = obtenerTasaDeCambio("EUR", "USD");
                cantidadDolares = cantidadEuros * tasaDeCambio;
                System.out.println(cantidadEuros + " euros equivalen a " + cantidadDolares + " dólares.");
                break;
            case 6:
                // Código para convertir de Dólar a Euro
                System.out.print("Ingresa la cantidad de dólares a convertir: ");
                cantidadDolares = scanner.nextDouble();
                tasaDeCambio = obtenerTasaDeCambio("USD", "EUR");
                cantidadEuros = cantidadDolares / tasaDeCambio;
                System.out.println(cantidadDolares + " dólares equivalen a " + cantidadEuros + " euros.");
                break;
            case 7:
                System.out.println("¡Hasta luego!");
                break;
            default:
                System.out.println("Opción inválida.");
                break;
        }
        scanner.close();
    }
}

