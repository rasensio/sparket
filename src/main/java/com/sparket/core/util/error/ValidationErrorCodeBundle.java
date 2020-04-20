package com.sparket.core.util.error;

import java.util.ListResourceBundle;

public class ValidationErrorCodeBundle extends ListResourceBundle {
 static final Object[][] CONTENTS = {
	 {"READING_INPUT", "Error leyendo input"},
	 {"VALIDATION", "Error de validación"},
	 {"REQUIRED_INPUT", "Ingrese los campos requeridos"},
	 {"INPUT_MUST_BE_CERTAIN_VALUE", "El valor debe ser igual al de una lista"},
	 {"INPUT_TOO_SHORT", "Faltan caractéres"},
	 {"INPUT_TOO_LONG", "Demasiados caractéres"},
   {"INVALID_NUMBER", "Ingrese un número valido"},
   {"INVALID_FLOAT", "Ingrese un número decimal"},
   {"INVALID_INPUT", "Ingrese un dato valido"},
   {"INVALID_EMAIL", "Ingrese un email válido"},
   {"INVALID_DATE", "Ingrese una fecha valida"},
   {"INPUT_MUST_BE_EQUAL", "Los valores deben ser iguales"},
   {"INVALID_JSONP_CALLBACK", "El parametro de jsonp callback tiene que ser 'jsonpcallback'"},
 };
 
	@Override
	protected Object[][] getContents() {
		return CONTENTS;
	}

}
