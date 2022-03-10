package dms.project;

import javax.swing.JFrame;

/**
 *
 * @author merna ashraf
 */
public class DMSProject {

    
    public static int index,index2;
    public static String email ;
    public static void fullAccount() {
        account rgf = new account();
        rgf.setVisible(true);
        rgf.pack();
        rgf.setLocationRelativeTo(null);
        rgf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rgf.Email.setText(DatabaseConnection.readSpecificData(email, 1));
        rgf.password.setText(DatabaseConnection.readSpecificData(email, 2));
        rgf.usarName.setText(DatabaseConnection.readSpecificData(email, 3));
        rgf.address.setText(DatabaseConnection.readSpecificData(email, 4));
        rgf.phoneNumber1.setText(DatabaseConnection.readSpecificData(email, 5));
        rgf.phoneNumber2.setText(DatabaseConnection.readSpecificData(email, 6));
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new sign_up().setVisible(true);
        }); 
    }
}
