package ClientPackage;

import SharedPackage.Operation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class Client2 {

    public static void main(String[] args) {

        try {
            System.out.println("je suis un client pas encore connectée ");
            Socket socket = new Socket("localhost", 1234);
            System.out.println("je suis un client connecté");
            // Meme ordre que le serveur:sortie d'abord,entrée ensuite
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Scanner sc = new Scanner(System.in);
            sc.useLocale(Locale.US);//le point decimal est accepté(3.5)
            String encore;
            do {
                System.out.print("Premier opérande:");
                double x = sc.nextDouble();
                String op;
                do {
                    System.out.print("Operateur(+ - * /):");
                    op = sc.next();
                }while (!op.matches("[+\\-*/]"));
                System.out.print("Deuxiéme opérande:");
                double y = sc.nextDouble();
                //b)le client envoie un NOUVEL objet a chaque operation
                Operation operation = new Operation(x, op.charAt(0), y);
                oos.writeObject(operation);
                oos.flush();
                //g)le client lit et affiche le resultat
                String reponse = (String) ois.readObject();
                System.out.println("resultat :" + reponse);
                System.out.print("autre calcul ?(o/n):");
                encore = sc.next();
            } while (encore.equalsIgnoreCase("o"));
            oos.writeObject("FIN"); //prevenir le serveur
            oos.flush();
            System.out.println("Fin de la communication");
            sc.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("erreur coté client:" + e.getMessage());
        }
    }
}