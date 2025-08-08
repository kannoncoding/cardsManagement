package gui;

import logica.ArbolBinarioBusqueda;
import modelo.Tarjeta;


public class MenuPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        initComponents();
        configurarUI(); 
        configurarEventos();
        
    }
    
    // ---------- Helpers de lectura/salida ----------
private Integer leerId() {
    try { return Integer.parseInt(txtId.getText().trim()); }
    catch (Exception e) { return null; }
}

private String leerDescripcion() {
    return txtDescripcion.getText().trim();
}

private String leerCategoria() {
    return (String) cmbCategoria.getSelectedItem();
}

private void mostrar(String msg) {
    txtResultados.append(msg + System.lineSeparator());
    // autoscroll
    txtResultados.setCaretPosition(txtResultados.getDocument().getLength());
}

private void limpiarResultados() {
    txtResultados.setText("");
}

private void limpiarEntradas() {
    txtId.setText("");
    txtDescripcion.setText("");
    if (cmbCategoria.getItemCount() > 0) {
        cmbCategoria.setSelectedIndex(0); // o -1 si quieres sin selección
    }
    txtId.requestFocusInWindow(); // comodidad para siguiente alta
}


// ---------- Conectar eventos de botones ----------
private void configurarEventos() {
    btnInsertar.addActionListener(e -> onInsertar());
    btnEliminar.addActionListener(e -> onEliminar());
    btnBuscar.addActionListener(e -> onBuscar());

    btnPreorden.addActionListener(e -> onRecorrido("PRE"));
    btnInorden.addActionListener(e -> onRecorrido("IN"));
    btnPostorden.addActionListener(e -> onRecorrido("POST"));

    btnGraficar.addActionListener(e -> panelGrafico.repaint());
    
    btnCantidadHV.addActionListener(e -> onCantidadHV());
    btnListarHojas.addActionListener(e -> onListarHojas());
    btnMayorMenor.addActionListener(e -> onMayorMenor());
}

// ---------- Acciones ----------
private void onInsertar() {
    limpiarResultados();
    Integer id = leerId();
    if (id == null) { mostrar("ERROR: Debes digitar un Id numérico."); return; }

    String desc = leerDescripcion();
    if (desc.isBlank()) { mostrar("ERROR: La descripción es obligatoria."); return; }

    String cat = leerCategoria();

    // Si tu Tarjeta no tiene este constructor, dímelo y lo cambio a setters.
    Tarjeta t = new Tarjeta(id, desc, cat);

    try {
        boolean ok = arbol.insertar(t);
        if (ok) {
            mostrar("Insertada tarjeta Id=" + id + " (" + cat + ")");
            panelGrafico.repaint();
            limpiarEntradas(); 
        } else {
            mostrar("No se pudo insertar: Id duplicado (" + id + ").");
        }
    } catch (Exception ex) {
        mostrar("ERROR al insertar: " + ex.getMessage());
    }
}

private void onEliminar() {
    limpiarResultados();
    Integer id = leerId();
    if (id == null) { mostrar("ERROR: Debes digitar un Id numérico."); return; }

    try {
        // TU árbol devuelve el mensaje de la operación
        String mensaje = arbol.eliminar(id);
        mostrar(mensaje);
        panelGrafico.repaint();
        limpiarEntradas(); 
    } catch (Exception ex) {
        mostrar("ERROR al eliminar: " + ex.getMessage());
    }
}

private void onBuscar() {
    limpiarResultados();
    Integer id = leerId();
    if (id == null) { mostrar("ERROR: Debes digitar un Id numérico."); return; }

    try {
        Tarjeta t = arbol.buscar(id);
        if (t != null) {
            mostrar("Encontrado -> Id: " + t.getId()
                    + " | Descripción: " + t.getDescripcion()
                    + " | Categoría: " + t.getCategoria());
        } else {
            mostrar("No existe una tarjeta con Id " + id + ".");
        }
    } catch (Exception ex) {
        mostrar("ERROR en búsqueda: " + ex.getMessage());
    }
}

private void onRecorrido(String tipo) {
    limpiarResultados();
    try {
        String lista;
        switch (tipo) {
            case "PRE"  -> lista = arbol.recorridoPreorden();
            case "IN"   -> lista = arbol.recorridoInorden();
            case "POST" -> lista = arbol.recorridoPostorden();
            default     -> throw new IllegalArgumentException("Tipo de recorrido inválido");
        }
        // El árbol devuelve con guiones y suele terminar en "-"
        if (lista == null) lista = "";
        if (lista.endsWith("-")) lista = lista.substring(0, lista.length()-1);
        mostrar("Recorrido " + tipo + ": " + lista);
    } catch (Exception ex) {
        mostrar("ERROR en recorrido: " + ex.getMessage());
    }
}

private void onCantidadHV() {
    limpiarResultados();
    try {
        int total = arbol.contarSuperheroesOVillanos();
        mostrar("Total de tarjetas 'Súper héroes' o 'Súper villanos': " + total);
    } catch (Exception ex) {
        mostrar("ERROR al contar: " + ex.getMessage());
    }
}

private void onListarHojas() {
    limpiarResultados();
    try {
        String lista = arbol.listarFrasesIconicasHojas(); // asumo viene formateado (ej: separados por saltos o comas)
        if (lista == null || lista.isBlank()) {
            mostrar("No hay nodos hoja de la categoría 'Frases icónicas'.");
        } else {
            mostrar("Hojas (Frases icónicas): " + lista);
        }
    } catch (Exception ex) {
        mostrar("ERROR al listar hojas: " + ex.getMessage());
    }
}

private void onMayorMenor() {
    limpiarResultados();
    try {
        Tarjeta menor = arbol.obtenerTarjetaMenorId();
        Tarjeta mayor = arbol.obtenerTarjetaMayorId();

        if (menor == null && mayor == null) {
            mostrar("El árbol está vacío.");
            return;
        }
        if (menor != null) {
            mostrar("Menor Id → Id: " + menor.getId() +
                    " | Descripción: " + menor.getDescripcion() +
                    " | Categoría: " + menor.getCategoria());
        } else {
            mostrar("No se encontró tarjeta con menor Id.");
        }

        if (mayor != null) {
            mostrar("Mayor Id → Id: " + mayor.getId() +
                    " | Descripción: " + mayor.getDescripcion() +
                    " | Categoría: " + mayor.getCategoria());
        } else {
            mostrar("No se encontró tarjeta con mayor Id.");
        }
    } catch (Exception ex) {
        mostrar("ERROR al obtener menor/mayor: " + ex.getMessage());
    }
}
    
    private void configurarUI() {
    // categorías válidas
    String[] categorias = {
        "Civiles", "Equipos", "Súper héroes", "Objetos", "Súper villanos",
        "Paneles", "Cara a cara", "Frases icónicas", "Película especial"
    };
    cmbCategoria.removeAllItems();
    for (String c : categorias) cmbCategoria.addItem(c);

    txtResultados.setEditable(false);
    txtDescripcion.setLineWrap(true);
    txtDescripcion.setWrapStyleWord(true);
    txtResultados.setLineWrap(true);
    txtResultados.setWrapStyleWord(true);

    aplicarFiltroNumerico(txtId);
}
    
    

private void aplicarFiltroNumerico(javax.swing.JTextField campo) {
    javax.swing.text.AbstractDocument doc = (javax.swing.text.AbstractDocument) campo.getDocument();
    doc.setDocumentFilter(new javax.swing.text.DocumentFilter() {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
                throws javax.swing.text.BadLocationException {
            if (string != null && string.matches("\\d+")) {
                super.insertString(fb, offset, string, attr);
            }
        }
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
                throws javax.swing.text.BadLocationException {
            if (text != null && text.matches("\\d+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    });
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbCategoria = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        btnInsertar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnPreorden = new javax.swing.JButton();
        btnInorden = new javax.swing.JButton();
        btnPostorden = new javax.swing.JButton();
        btnGraficar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtResultados = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        panelGrafico = new javax.swing.JPanel();
        btnCantidadHV = new javax.swing.JButton();
        btnListarHojas = new javax.swing.JButton();
        btnMayorMenor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Id:");

        jLabel2.setText("Categoria:");

        cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoriaActionPerformed(evt);
            }
        });

        jLabel3.setText("Descripcion");

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        btnInsertar.setText("Insertar");

        btnEliminar.setText("Eliminar");

        btnBuscar.setText("Buscar");

        btnPreorden.setText("Preorden");

        btnInorden.setText("Inorden");
        btnInorden.setToolTipText("");

        btnPostorden.setText("Postorden");

        btnGraficar.setText("Graficar");

        jLabel4.setText("Panel de Resultados");
        jLabel4.setToolTipText("");

        txtResultados.setColumns(20);
        txtResultados.setRows(5);
        jScrollPane2.setViewportView(txtResultados);

        jLabel5.setText("Panel Grafico");

        javax.swing.GroupLayout panelGraficoLayout = new javax.swing.GroupLayout(panelGrafico);
        panelGrafico.setLayout(panelGraficoLayout);
        panelGraficoLayout.setHorizontalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelGraficoLayout.setVerticalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );

        btnCantidadHV.setText("Cantidad Heroes/Villanos");

        btnListarHojas.setText("Listar Hojas (Frases iconicas)  ");

        btnMayorMenor.setText("Mayor/Menor Id");
        btnMayorMenor.setActionCommand("Mayor/Menor Id");
        btnMayorMenor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMayorMenorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelGrafico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnInorden, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPostorden, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(btnGraficar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPreorden, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCantidadHV)
                        .addGap(18, 18, 18)
                        .addComponent(btnListarHojas)
                        .addGap(18, 18, 18)
                        .addComponent(btnMayorMenor, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsertar)
                    .addComponent(btnEliminar)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPreorden)
                    .addComponent(btnInorden)
                    .addComponent(btnPostorden)
                    .addComponent(btnGraficar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCantidadHV)
                    .addComponent(btnListarHojas)
                    .addComponent(btnMayorMenor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCategoriaActionPerformed

    private void btnMayorMenorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMayorMenorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMayorMenorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }
    
    // --- Lógica del árbol ---
private final ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCantidadHV;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnInorden;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnListarHojas;
    private javax.swing.JButton btnMayorMenor;
    private javax.swing.JButton btnPostorden;
    private javax.swing.JButton btnPreorden;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelGrafico;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextArea txtResultados;
    // End of variables declaration//GEN-END:variables
}
