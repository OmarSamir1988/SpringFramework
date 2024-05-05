package fiscal;

import fiscal.service.ComputeService;
import fiscal.service.NameAndSurnameComputationService;
import fiscal.view.DataPanel;

import javax.swing.*;

public class FiscalCodeCalculator {

    public static void main(String[] args) throws SecurityException {
        JFrame frame1 = new JFrame();
        NameAndSurnameComputationService computationService = new NameAndSurnameComputationService();
        ComputeService computeService = new ComputeService(computationService);

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DataPanel dp = new DataPanel(computeService);
        frame1.getContentPane().add(dp);
        frame1.setLocation(200, 100);
        frame1.pack();
        frame1.setVisible(true);
    }
}
