/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author alu2017454
 */
public class VeterinariaException extends Exception{
    
            public static final int NO_SUCH_USER = 0;
            public static final int DNI_INCORRECT_SIZE = 1;
	    public static final int DNI_INCORRECT_NUM = 2;
            public static final int MATRICULA_SIZE = 3;
            public static final int MATRICULA_U_EXISTS = 4;
            public static final int PASS_CONFIRM = 5;
            public static final int WRONG_TYPE = 6;
            public static final int NO_EXPEDIENTS = 7;
            public static final int NO_USERS = 8;
	    public static final int NO_EXPEDIENT = 9;
	   
	    
	    private int code;
	    
	    private final List<String> messages = Arrays.asList(
                    "<ERROR 001 : No hay ningun usuario que corresponda a esa matricula y password. Asegurese e intente de nuevo>",
	            "<ERROR 002 : El DNI debe tener 8 cifras>",
	            "<ERROR 003 : El DNI no puede contener caracteres no numericos>",
                    "<ERROR 004 : La matricula debe tener 3 caracteres>",
                    "<ERROR 005 : Ya existe otro usuario con esa matricula>",
	            "<ERROR 006 : El segundo password no es correcto>",
                    "<ERROR 007 : Identificador para tipo usuario no reconocido. Solo 1,2,3 se admiten>",
                    "<ERROR 008 : No existen expedientes en la base de datos>",
                    "<ERROR 009 : No existen usuarios en la base de datos>",
                    "<ERROR 010 : El ID introducido no corresponde a ningun expediente existente>"
	   );
	    
	           
	            
	    public VeterinariaException(int code) {
	        this.code = code;
	    }

	    @Override
	    public String getMessage() {
	        return messages.get(code);
	    }
}
