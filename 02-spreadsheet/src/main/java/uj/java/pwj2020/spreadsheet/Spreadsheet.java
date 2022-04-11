package uj.java.pwj2020.spreadsheet;

public class Spreadsheet {

    public String[][] calculate(String[][] input) {
        Operations operation = new Operations();
        for (int i = 0; i < input.length; i++) {
            for (int k = 0; k < input[0].length; k++) {
                OperationTypes type = operation.recognizeType(input[i][k]);
                switch (type){
                    case REFERENCE -> input[i][k] = operation.referenceToValue(i,k,input);
                    case FORMULA -> input[i][k] = operation.formulaToValue(i,k,input);
                }
            }
        }
        return input;
    }
}
