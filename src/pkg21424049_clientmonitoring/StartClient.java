/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pkg21424049_clientmonitoring;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Huynh Hoang Sang
 */
public class StartClient extends javax.swing.JFrame {

    /**
     * Creates new form StartClient
     */
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    static String ip;
    static String publicIp;
   List<String> listClient;
   List<String> listAction;
    private final String[] columnNames = new String[]{
        "STT", "IP", "Time", "Action"};
    public StartClient() {
        initComponents();
        this.getContentPane().setBackground(new Color(180, 223, 233));
    }

    class HandleClient extends Thread {

        StartClient c;

        public HandleClient(StartClient c) {
            this.c = c;
        }

        public void run() {
            
            try {
                Socket s = new Socket(ip, ClientPage.portNumber);
                //InetAddress a=new InetAddress(publicIp);

                //Socket s = new Socket(InetAddress.getByName(publicIp), ClientPage.portNumber, InetAddress.getByName(ip), ClientPage.portNumber);
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                dout.writeUTF(ClientPage.name);
//                InputStream is=s.getInputStream();
//                BufferedReader br=new BufferedReader(new InputStreamReader(is));

//                OutputStream os = s.getOutputStream();
//                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
//                bw.write(ClientPage.name);
//                bw.newLine();
//                bw.flush();
                String str = "";
                int i = 0;
                 listAction = new ArrayList<>();
                //while (str != "Exit") {
                //str = din.readUTF();
                //if (str == "Exit") {
                //  msg_area.setText(str);
                // } else {
                //   msg_area.setText(str);
                //}
                // }
                WatchService watchservice;
                try {
                    watchservice = FileSystems.getDefault().newWatchService();
                    Path direct = Paths.get("C:\\");

                    WatchKey watchKey = direct.register(watchservice,
                            StandardWatchEventKinds.ENTRY_CREATE,
                            StandardWatchEventKinds.ENTRY_DELETE,
                            StandardWatchEventKinds.ENTRY_MODIFY
                    );

                    while (true) {

                        for (WatchEvent<?> event : watchKey.pollEvents()) {
                            Path file = direct.resolve((Path) event.context());

                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();

                            String dateString = dtf.format(now);
                            String actionFile = file + ";" + event.kind() + ";" + dateString;
                            
                            sendMessage(actionFile);
                            
                            listAction.add(actionFile);
                            
                            int size = listAction.size();
                            
                            if (size > 0) {
                                Object[][] action = new Object[size][4];
                                for (int j = 0; j < size;j++) {

                                   String[] arrOfStr = listAction.get(j).split(";");
                                 
                                    action[j][0] = j;
                                    action[j][1] = arrOfStr[0];
                                    action[j][2] = arrOfStr[2];
                                    action[j][3] = arrOfStr[1].replaceAll("ENTRY_", "");

                                }
                                jTable1.setModel(new DefaultTableModel(action, columnNames));
                            }
                            
                            
                                
                        }
                        
                    }
                } catch (IOException ex) {
                    Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error in Connection", JOptionPane.ERROR_MESSAGE);
                c.setVisible(false);
                new ClientPage().setVisible(true);
                System.out.println(e.toString());
            }
        }
    }

    public void sendMessage(String text) {
        String str = text;

        textQuery.setText(null);
        try {
            dout.writeUTF(str);
            str = str.replaceAll(";", " ");

            textStatus.setText(textStatus.getText() + "\n You :- " + str);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Could not send ");
        } 
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void go() {
        this.setVisible(true);
        ip = ClientPage.ip;
        publicIp = "localhost";
        textMainLabel.setText(ClientPage.name);
        new HandleClient(this).start();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textQuery = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        textStatus2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        msg_exit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textMainLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textStatus = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textQuery.setToolTipText("Write Query Here");
        textQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textQueryActionPerformed(evt);
            }
        });

        jButton2.setText("Send Query");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        textStatus2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        textStatus2.setForeground(new java.awt.Color(0, 204, 0));
        textStatus2.setText("Connected");
        textStatus2.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jButton3.setText("Browse/ Save Log");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        msg_exit.setBackground(new java.awt.Color(255, 0, 0));
        msg_exit.setText("Exit");
        msg_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_exitActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Client Notepad");
        jLabel1.setBorder(new javax.swing.border.MatteBorder(null));

        textMainLabel.setBorder(javax.swing.BorderFactory.createTitledBorder("Connected as"));

        textStatus.setEditable(false);
        textStatus.setColumns(20);
        textStatus.setRows(5);
        jScrollPane2.setViewportView(textStatus);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "IP", "Time", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textStatus2, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                        .addGap(29, 29, 29)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(msg_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(textQuery, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textMainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textMainLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(textQuery, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(msg_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textStatus2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Directory to Save File");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        String fileName = JOptionPane.showInputDialog("Enter File Name (With Extension) ");

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String str = chooser.getSelectedFile().getAbsolutePath();
            try {
                FileWriter fout;
                fout = new FileWriter(str + "/" + fileName);
                PrintWriter p = new PrintWriter(fout);
                String words[]=textStatus.getText().split("\n");

                for(String word : words){
                 p.println(word);
                 }
                p.close();
                fout.close();
                JOptionPane.showMessageDialog(null, "File Successfully written");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(StartClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StartClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No Selection ");
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void msg_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_exitActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == 0) {
            try {
                // TODO add your handling code here:
                dout.writeUTF("Exit");
            } catch (IOException ex) {
                Logger.getLogger(StartClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }

    }//GEN-LAST:event_msg_exitActionPerformed

    private void textQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textQueryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textQueryActionPerformed

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
            java.util.logging.Logger.getLogger(StartClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartClient().go();

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    static javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton msg_exit;
    private javax.swing.JLabel textMainLabel;
    private javax.swing.JTextField textQuery;
    private javax.swing.JTextArea textStatus;
    static javax.swing.JLabel textStatus2;
    // End of variables declaration//GEN-END:variables
}
