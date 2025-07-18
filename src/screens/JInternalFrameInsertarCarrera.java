package screens;

import java.awt.Font;
import java.sql.Connection;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import models.Carrera;

public class JInternalFrameInsertarCarrera extends JInternalFrame{
    private JLabel lblId;
    private JLabel lblNombreCarrera;
    private JLabel lblMonto;
    private JTextField txtId;
    private JTextField txtNombreCarrera;
    private JTextField txtMonto;
    private JButton btnAceptar;
    private JButton btnCancelar;

    private final Connection conn;

    public JInternalFrameInsertarCarrera(Connection conn){
        super("Insertar carrera", 
              true,  // resizable
              true,  // closable
              true,  // maximizable
              true); // iconifiable (minimizable)
        this.conn = conn;
        this.setTitle("Insertar nueva carrera");
        this.setSize(400,400);
        initComponents();
    }

    private void initComponents(){
        // Creación de objetos
        lblId = new JLabel("Id:");
        lblNombreCarrera = new JLabel("Nombre de carrera:");
        lblMonto = new JLabel("Monto pagado:");
        txtId = new JTextField();
        txtNombreCarrera = new JTextField();
        txtMonto = new JTextField();
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        // Etiquetas
        lblId.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNombreCarrera.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblMonto.setFont(new Font("Tahoma", Font.BOLD, 16));
        //Botones 
        btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 16));

        btnAceptar.addActionListener(e -> insertarCarrera());
        btnCancelar.addActionListener(e -> this.dispose());

        GroupLayout layout = new GroupLayout(getContentPane());
        this.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblId)
                .addComponent(txtId)
                .addComponent(lblNombreCarrera)
                .addComponent(txtNombreCarrera)
                .addComponent(lblMonto)
                .addComponent(txtMonto)
                .addGroup(
                    layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addComponent(btnCancelar)
                )
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblId)
                .addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblNombreCarrera)
                .addComponent(txtNombreCarrera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblMonto)
                .addComponent(txtMonto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAceptar)
                        .addComponent(btnCancelar)
                )
        );
    }
    
    private void insertarCarrera(){
        int rows; 

        //Recuperar datos de la cajas de texto
        int id = Integer.parseInt(txtId.getText());
        String nombre = txtNombreCarrera.getText();
        double monto = Double.parseDouble(txtMonto.getText());

        //revisar que los campos no esten vacios
        if (id <= 0 || nombre.isEmpty() || monto <= 0){
            JOptionPane.showMessageDialog(this,
            "Porfavor, complete los campos correctamente", 
            "Error",
            JOptionPane.ERROR_MESSAGE);
        }
        else{
            //Crear un objeto carrera
            Carrera carrera = new Carrera(id, nombre, monto);
            
            //Crear un objeto CarreraDAO
            CarreraDAO carreraDAO = new CarreraDAO(this.conn);

            //insertar la carrera en la base de datos
            rows = carreraDAO.insertarCarrera(carrera);

            if (rows > 0) {
                JOptionPane.showMessageDialog(this,
                "Carrera insertada correctamente", 
                "Exito",
                JOptionPane.INFORMATION_MESSAGE);
            txtId.setText("");
            txtNombreCarrera.setText("");
            txtMonto.setText("");
                // Cerrar el internal frame
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al insertar la carrera", 
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}