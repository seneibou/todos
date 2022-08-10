package sn.ept.git.seminaire.cicd.demo;

import java.util.Random;

public  class CurrencyService {

    Random r = new Random();

    public double convert(Currency from, Currency to, double value){
        return from.equals(to) ? value : r.nextDouble()*value;
    }

}
