package uj.java.pwj2020.spreadsheet;

class algebraicExpression{
    public String operation;
    public String firstOperand;
    public String secondOperand;
}

public class Operations {

    public String referenceToValue(int row, int column, String[][] array){
        OperationTypes type = OperationTypes.REFERENCE;
        String string;

        while(type != OperationTypes.VALUE){
            string = array[row][column];
            type = recognizeType(string);
            switch (type){
                case REFERENCE -> {
                    row = Integer.parseInt(string.substring(2))-1;
                    column = string.charAt(1)-'A';
                }
                case FORMULA -> array[row][column] = formulaToValue(row,column,array);
                default -> type = OperationTypes.VALUE;
            }
        }
        return array[row][column];
    }

    public String formulaToValue(int row, int column, String[][] array){
        String string = array[row][column];
        algebraicExpression expression = getElements(string);
        expression = referenceOperandsToValue(row,column,array,expression);

        return String.valueOf(doMath(Integer.parseInt(expression.firstOperand),Integer.parseInt(expression.secondOperand), expression.operation));
    }

    public algebraicExpression getElements(String string)
    {
        algebraicExpression expression = new algebraicExpression();
        expression.operation = string.substring(1,string.indexOf("("));
        string = string.substring(string.indexOf("(") + 1,string.indexOf(")"));
        expression.firstOperand = string.substring(0,string.indexOf(","));
        expression.secondOperand = string.substring(string.indexOf(",")+1);

        return expression;
    }

    public algebraicExpression referenceOperandsToValue(int row, int column, String[][] array, algebraicExpression expression){
        array[row][column] = expression.firstOperand;
        expression.firstOperand = referenceToValue(row,column,array);
        array[row][column] = expression.secondOperand;
        expression.secondOperand = referenceToValue(row,column,array);
        return expression;
    }

    public OperationTypes recognizeType(String input)
    {
        if (input.charAt(0) =='$')
            return OperationTypes.REFERENCE;
        else if (input.charAt(0) == '=')
            return OperationTypes.FORMULA;
        else
            return OperationTypes.VALUE;
    }

    public int doMath(int firstCell, int secondCell, String formula) {
        return switch (formula) {
            case ("ADD") -> firstCell + secondCell;
            case ("SUB") -> firstCell - secondCell;
            case ("MUL") -> firstCell * secondCell;
            case ("DIV") -> firstCell / secondCell;
            case ("MOD") -> firstCell % secondCell;
            default -> -1;
        };
    }
}
