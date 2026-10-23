package ServerPackage;

import SharedPackage.Operation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur2 {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println("Je suis un serveur en attente de la connexion d'un client");

            Socket socket = serverSocket.accept();

            System.out.println("Un client est connecté.");

            // Ordre important : ObjectOutputStream d'abord, puis ObjectInputStream
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // c) Le serveur reçoit l'objet
            Object recu = ois.readObject();

            while (recu instanceof Operation) {

                Operation op = (Operation) recu;

                // d) Le serveur récupère les données de l'objet
                double a = op.getOperande1();
                char operateur = op.getOperateur();
                double b = op.getOperande2();

                System.out.println("Opération reçue : "
                        + a + " " + operateur + " " + b);

                // e) Le serveur effectue le calcul
                String reponse = calculer(a, operateur, b);

                // f) Le serveur renvoie le résultat
                oos.writeObject(reponse);
                oos.flush();

                System.out.println("Réponse envoyée : " + reponse);

                // Lire l'opération suivante ou le texte "FIN"
                recu = ois.readObject();
            }

            // Fermeture après la fin de la boucle
            System.out.println("Fin de communication");

            ois.close();
            oos.close();
            socket.close();
            serverSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur côté serveur : " + e.getMessage());
        }
    }

    static String calculer(double a, char operateur, double b) {
        double r = 0;

        switch (operateur) {
            case '+':
                r = a + b;
                break;

            case '-':
                r = a - b;
                break;

            case '*':
                r = a * b;
                break;

            case '/':
                if (b == 0) {
                    return "Erreur : division par zéro impossible";
                }
                r = a / b;
                break;

            default:
                return "Erreur : opérateur inconnu";
        }

        return formater(a) + " " + operateur + " "
                + formater(b) + " = " + formater(r);
    }

    static String formater(double v) {
        if (v == Math.rint(v) && !Double.isInfinite(v)) {
            return String.valueOf((long) v);
        }

        return String.valueOf(v);
    }
}
