package SharedPackage;

import java.io.Serializable;

public class Operation implements Serializable {

    private static final long serialVersionUID=1L;//version de la classe,identique des deux cotés
    private double operande1;
    private char operateur;
    private double operande2;
    public Operation(double operande1,char operateur,double operande2){
        this.operande1=operande1;
        this.operande2=operande2;
        this.operateur=operateur;
    }

    public double getOperande1() {
        return operande1;
    }

    public double getOperande2() {
        return operande2;
    }

    public char getOperateur() {
        return operateur;
    }
}