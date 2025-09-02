package Views;

import Controllers.CajeroController;
import Models.CajeroModel;

public class CajeroAutomatico {
    public void main(String[] args) {
        CajeroModel model = new CajeroModel();
        CajeroView view = new CajeroView();
        CajeroController controller = new CajeroController(model, view);
        controller.iniciarSistema();
    }
}
