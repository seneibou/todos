package sn.ept.git.seminaire.cicd.demo;


public  class CurrencyConverter {

      private final CurrencyService  converter;

      public CurrencyConverter(CurrencyService converter) {
            this.converter = converter;
      }

      public  double convert(Currency from , Currency to, double value){
            return converter.convert(from,to,value);

      }
}
