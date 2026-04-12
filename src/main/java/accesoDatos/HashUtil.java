package accesoDatos;

import java.security.MessageDigest;

public class HashUtil {

	///
	/// Esta clase se encarga de generar las contrasenas hash para el registro de usuario
	/// 
	
	public static String sha256(String password) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());
			
			StringBuilder hex = new StringBuilder();
			
			for (byte b : hash) {
				hex.append(String.format("%02x", b));
			}
			
			return hex.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
