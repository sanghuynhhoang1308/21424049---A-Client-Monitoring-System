/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ankur
 */
package pkg21424049_clientmonitoring;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Calendar;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class StartServer extends javax.swing.JFrame {

    /**
     * Creates new form StartServer
     */
    ServerSocket ss;
    Socket s;
    DataOutputStream dtout;
    DataInputStream dtin;

    public StartServer() {
        initComponents();
        this.getContentPane().setBackground(new Color(233, 210, 180));
    }

    public class HandleServer extends Thread {

        public class StartSending extends Thread {

            public String name;
            public DataOutputStream dout;
            public DataInputStream din;

            public StartSending(String name, DataOutputStream dout, DataInputStream din) {
                this.name = name;
                this.dout = dout;
                this.din = din;
            }

            public void run() {
                String str = "";
                while (true) {
                   
                    try {
                        dout.writeUTF(str);
                    } catch (IOException ex) {
                        Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        public class StartReceiving extends Thread {

            public String name;
            public DataOutputStream dout;
            public DataInputStream din;

            public StartReceiving(String name, DataOutputStream dout, DataInputStream din) {
                this.name = name;
                this.dout = dout;
                this.din = din;
            }

            public void run() {
                String str = "";
                while (true) {
                    try {
                        str = din.readUTF();
                        if (str.equals("Exit")) {
                            textStatus.setText(textStatus.getText().trim() + "\n<" + name + " Disconnected>");
                            this.finalize();
                        } else {
                            
                            
                            textStatus.setText(textStatus.getText().trim() + "\n" + name + " :- " + str);
                            
                            
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Throwable ex) {
                        JOptionPane.showMessageDialog(null, "Couldnt Stop the thread");
                    }

                }
            }
        }

        public void run() {
            String str;
            try {
                ServerSocket ss = new ServerSocket(StartSystem.portNumber);
                while (true) {
                    Socket s = ss.accept();
                    dtin = new DataInputStream(s.getInputStream());
                    dtout = new DataOutputStream(s.getOutputStream());
                    str = dtin.readUTF();
                    textStatus.setText(textStatus.getText() + "\n<" + str + " Connected >");
                    new StartSending(str, dtout, dtin).start();
                    
                    new StartReceiving(str, dtout, dtin).start();

                }
            } catch (Exception e) {
                try {
                    JOptionPane.showMessageDialog(null, "Could Not Establish server");
                    SeverPage h = new SeverPage();
                    new StartServer().setVisible(false);
                    h.setVisible(true);
                } catch (Exception e2) {

                }
            }
        }
    }

    public void go() {
        this.setVisible(true);
        textStatus.setText("<Server Started>");
        new HandleServer().start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        msg_exit = new javax.swing.JButton();
        textStatus2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textStatus = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        msg_exit.setBackground(new java.awt.Color(255, 102, 102));
        msg_exit.setText("Exit");
        msg_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_exitActionPerformed(evt);
            }
        });

        textStatus2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        textStatus2.setForeground(new java.awt.Color(51, 102, 0));
        textStatus2.setText("Connected");
        textStatus2.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Server Notepad");
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        textStatus.setEditable(false);
        textStatus.setColumns(20);
        textStatus.setFont(new java.awt.Font("MS Gothic", 0, 14)); // NOI18N
        textStatus.setRows(5);
        jScrollPane3.setViewportView(textStatus);

        jButton2.setText("Browse");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Button");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(1, 1, 1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textStatus2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(msg_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jScrollPane3))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(msg_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textStatus2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msg_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_exitActionPerformed
        if (JOptionPane.showConfirmDialog(null, "All clients will be disconnected.Do you want to exit?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == 0) {
            System.exit(0);
        }

    }//GEN-LAST:event_msg_exitActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
       /* WatchService watchservice;
        try {
            watchservice = FileSystems.getDefault().newWatchService();
            Path direct = Paths.get("C:\\");
        WatchKey watchKey =direct.register(watchservice,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY
                );
        while(true)
        {
        for(WatchEvent<?> event : watchKey.pollEvents()){
            System.out.println(event.kind());
            Path file = direct.resolve((Path) event.context());
            System.out.println(file + "was last modified at " + file.toFile().lastModified());
            
        }
        }
        } catch (IOException ex) {
            Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        JFileChooser chooser=new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        String fileName=JOptionPane.showInputDialog("Enter File Name (With Extension) ");
        
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
                String str=chooser.getSelectedFile().getAbsolutePath();
        }
            else {
              JOptionPane.showMessageDialog(null,"No Selection ");
              }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(StartServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartServer().go();
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    javax.swing.JButton msg_exit;
    javax.swing.JTextArea textStatus;
    javax.swing.JLabel textStatus2;
    // End of variables declaration//GEN-END:variables
}
