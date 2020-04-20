package com.sparket.core.util.error;

import java.util.ListResourceBundle;

public class SystemErrorCodeBundle extends ListResourceBundle {
	static final Object[][] CONTENTS = {
			{ "SYSTEM_ERROR", "Error general. Contacte a soporte" },
			{ "PROGRAMMING", "Error de programacion" },
			{ "BAD_URL_FORMAT", "Malformed URL" },
			{ "WRONG_MULTIPART_ENCODING", "Request submitted is not form-mutipart" },
			{ "NOT_FOUND", "No encontrado" },
			{ "SERVICE_BUSY", "Error del sistema, intente nuevamente" },
			{ "IO_EXCEPTION", "Error de IO, intente nuevamente" },
			{ "NETWORK_EXCEPTION", "Error de network, intente nuevamente" },
			{ "NOT_IMPLEMENTED", "Esta funcion no ha sido implementada aun" },

	};

	@Override
	protected Object[][] getContents() {
		return CONTENTS;
	}

}
