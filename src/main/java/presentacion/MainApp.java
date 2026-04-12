package presentacion;

import logicaNegocio.SistemaService;
import accesoDatos.JPAUtil;


public class MainApp {
    public static void main(String[] args) {
        // Una sola instancia del service para toda la app
    	JPAUtil.startEntityManagerFactory(); //necesario cuando se inicia la app
        SistemaService service = new SistemaService();
        new LoginFrame(service).setVisible(true);
    }
}
