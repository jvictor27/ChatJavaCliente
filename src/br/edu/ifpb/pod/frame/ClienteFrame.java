/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.frame;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import br.edu.ifpb.pod.bean.ChatMessage;
import br.edu.ifpb.pod.bean.ChatMessage.Action;
import br.edu.ifpb.pod.service.ClienteService;


public class ClienteFrame extends javax.swing.JFrame {

    private Socket socket;
    private ChatMessage message;
    private ClienteService service;
    
    private String[] clientesOnline;

    /**
     * Creates new form ClienteFrame
     */
    public ClienteFrame() {
        initComponents();
    }

    private class  ListenerSocket implements Runnable {

        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                this.input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            ChatMessage message = null;
            try {
                while ((message = (ChatMessage) input.readObject()) != null) {
                	String messageText = message.getText();
                    
                	System.out.println("DESGRAÇA");
                	System.out.println(message.getAction());
                    
                    Action action = message.getAction();
                    
                    if (action.equals(Action.CONNECT)) {
                        connected(message);
                    } else if (action.equals(Action.DISCONNECT)) {
                        disconnected();
                        socket.close();
                    } else if (action.equals(Action.SEND_ONE)) {
                        System.out.println("::: " + message.getText() + " :::");
                        receive(message);
                    } else if (action.equals(Action.USERS_ONLINE)) {
                        refreshOnlines(message);
                    } else if (action.equals(Action.COMMAND_ERO)) {
                        commandErro(message);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void commandErro(ChatMessage message) {
    	if (message.getText().equals("command_erro_111")) {
            JOptionPane.showMessageDialog(this, "Comando incorreto, exemplo do comando send -user <nomeDestinatário> <mensagem>");
            return;
        } else if (message.getText().equals("command_erro_999")) {
            JOptionPane.showMessageDialog(this, "Comando incorreto, exemplo do comando send -user <nomeDestinatário> <mensagem>");
            return;
        }
    }

    private void connected(ChatMessage message) {
        if (message.getText().equals("NO")) {
            this.txtName.setText("");
            JOptionPane.showMessageDialog(this, "Conexão não realizada!\nTente novamente com um nome diferente.");
            return;
        }

        this.message = message;
        this.btnConnectar.setEnabled(false);
        this.txtName.setEditable(false);

        this.btnSair.setEnabled(true);
        this.txtAreaSend.setEnabled(true);
        this.txtAreaReceive.setEnabled(true);
        this.btnEnviar.setEnabled(true);
        this.btnLimpar.setEnabled(true);
        this.listOnlines.setEnabled(true);

        JOptionPane.showMessageDialog(this, "Você está conectado no chat!");
    }

    private void disconnected() {

        this.btnConnectar.setEnabled(true);
        this.txtName.setEditable(true);

        this.btnSair.setEnabled(false);
        this.txtAreaSend.setEnabled(false);
        this.txtAreaReceive.setEnabled(false);
        this.btnEnviar.setEnabled(false);
        this.btnLimpar.setEnabled(false);
        String[] array = new String[0];
        this.listOnlines.setEnabled(false);
        this.listOnlines.setListData(array);
//        this.listOnlines.setEnabled(false);
        
        this.txtAreaReceive.setText("");
        this.txtAreaSend.setText("");

        JOptionPane.showMessageDialog(this, "Você saiu do chat!");
    }

    private void receive(ChatMessage message) {
        this.txtAreaReceive.append(message.getName() + " diz: " + message.getText() + "\n");
    }

    private void refreshOnlines(ChatMessage message) {
        System.out.println(message.getSetOnlines().toString());
        
        Set<String> names = message.getSetOnlines();
        
        names.remove(message.getName());
        
        String[] array = (String[]) names.toArray(new String[names.size()]);
        
        clientesOnline = array;
        
        System.out.println(message.getText());
        
        if (message.getText() != null && message.getText().equals("list")) {
        	for(String user : array) {
        		this.txtAreaReceive.append(user + "\n");
        	}     	
        } else {   
        	this.listOnlines.setListData(array);
        	this.listOnlines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        	this.listOnlines.setLayoutOrientation(JList.VERTICAL);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        btnConnectar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listOnlines = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaReceive = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaSend = new javax.swing.JTextArea();
        btnEnviar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Conectar"));

        btnConnectar.setText("Connectar");
        btnConnectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectarActionPerformed(evt);
            }
        });

        btnSair.setText("Sair");
        btnSair.setEnabled(false);
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConnectar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSair)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnConnectar)
                .addComponent(btnSair))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Onlines"));

        jScrollPane3.setViewportView(listOnlines);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtAreaReceive.setEditable(false);
        txtAreaReceive.setColumns(20);
        txtAreaReceive.setRows(5);
        txtAreaReceive.setEnabled(false);
        jScrollPane1.setViewportView(txtAreaReceive);

        txtAreaSend.setColumns(20);
        txtAreaSend.setRows(5);
        txtAreaSend.setEnabled(false);
        jScrollPane2.setViewportView(txtAreaSend);

        btnEnviar.setText("Enviar");
        btnEnviar.setEnabled(false);
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        btnLimpar.setText("Limpar");
        btnLimpar.setEnabled(false);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnviar)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnviar)
                    .addComponent(btnLimpar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectarActionPerformed
    	if (this.txtName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Conexão não realizada!\nDigite um nick de usuário, não use espaço. ex: de nick => Victor.");
            return;
        }
    	
        String name = this.txtName.getText();

        if (!name.isEmpty()) {
        	String nameSplit[] = name.split(" ");
        	if(nameSplit.length > 1) {
        		JOptionPane.showMessageDialog(this, "Conexão não realizada!\nO nick não pode ter espaço.");
                return;
        	}
            this.message = new ChatMessage();
            this.message.setAction(Action.CONNECT);
            this.message.setName(name);

            this.service = new ClienteService();
            this.socket = this.service.connect();

            new Thread(new ListenerSocket(this.socket)).start();

            this.service.send(message);
        }
    }//GEN-LAST:event_btnConnectarActionPerformed
    
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        ChatMessage message = new ChatMessage();
//        message.setName(this.message.getName());
        message.setName(this.txtName.getText());
        message.setAction(Action.DISCONNECT);
        this.service.send(message);
        disconnected();
    }//GEN-LAST:event_btnSairActionPerformed   

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        this.txtAreaSend.setText("");
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
    	Date date = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		Calendar data = Calendar.getInstance();
		int horas = data.get(Calendar.HOUR_OF_DAY);
		int minutos = data.get(Calendar.MINUTE);
//        String name = this.message.getName();
        String name = this.txtName.getText();
        System.out.println(name + '\n');
        String text = socket.getInetAddress().getHostAddress() + ":"
				+ socket.getPort() + "/~" + name + ": " + this.txtAreaSend.getText() + " " + horas
				+ "h" + minutos + "m " + formatador.format(date);
    	
    	
        String[] textSplit = this.txtAreaSend.getText().split(" ");  
        
//        if (this.txtAreaSend.getText().startsWith("send -user")) {
//        	
//        	text = this.txtAreaSend.getText();
//
//        }
        
        if (this.txtAreaSend.getText().startsWith("rename") && textSplit.length == 2) {
        	
        	this.message.setAction(Action.SEND_ALL);

        	
        	name = (message.getName() + " " + textSplit[1]);
        	this.txtName.setText(textSplit[1]);
        	text = this.txtAreaSend.getText();

        }
        
    	if (!this.txtAreaSend.getText().isEmpty() && this.txtAreaSend.getText().equals("bye")) {
	    	ChatMessage message = new ChatMessage();
	        message.setName(this.message.getName());
	        message.setAction(Action.DISCONNECT);
	        this.service.send(message);
	        disconnected();
	        return;
    	}
    	
    	this.message = new ChatMessage();
    	if (!this.txtAreaSend.getText().isEmpty() && this.txtAreaSend.getText().equals("list")) {
    		this.message.setName(name);
    		this.txtAreaReceive.append("Você disse: " + text + "\n");
    		this.message.setAction(Action.SEND_ONE);
    		this.message.setText(this.txtAreaSend.getText());
            this.service.send(this.message);
	        return;
    	}
    	
    	
        
//        this.message = new ChatMessage();
        
        if (this.listOnlines.getSelectedIndex() > -1 && !this.txtAreaSend.getText().startsWith("rename") && !this.txtAreaSend.getText().startsWith("send -user")) {
            this.message.setNameReserved((String) this.listOnlines.getSelectedValue());
            this.message.setAction(Action.SEND_ONE);
            this.listOnlines.clearSelection();
        } else if (this.txtAreaSend.getText().startsWith("send -user") && !this.txtAreaSend.getText().startsWith("rename")) { 
        	System.out.println("Aqui \n");
        	if (textSplit.length < 3) {
        		System.out.println("Aqui1 \n");
        		JOptionPane.showMessageDialog(this, "Comando incorreto, exemplo do comando send -user <nomeDestinatário> <mensagem>");
                return;
        	} else {
        		System.out.println("Aqui2 \n");
        		Boolean clienteExiste = false;
        		for (String c : clientesOnline) {
                    if (c.equals(textSplit[2])) {
                    	clienteExiste = true;
                    }
        		}
        		if(clienteExiste) {
        			this.message.setNameReserved((String) textSplit[2]);
        			this.message.setAction(Action.SEND_ONE);
        		} else {
        			JOptionPane.showMessageDialog(this, textSplit[2] + " não está online");
                    return;
        		}
        	}
        } else {
            this.message.setAction(Action.SEND_ALL);
        }
        
        if (!text.isEmpty()) {
        	System.out.println(name + '\n');
            this.message.setName(name);
//        	this.message.setName(this.txtName.getText());
            System.out.println(this.message.getName() + '\n');
            this.message.setText(text);

            this.txtAreaReceive.append("Você disse: " + text + "\n");

            
            this.service.send(this.message);
            
        }
        
        this.txtAreaSend.setText("");
    }//GEN-LAST:event_btnEnviarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnectar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSair;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList listOnlines;
    private javax.swing.JTextArea txtAreaReceive;
    private javax.swing.JTextArea txtAreaSend;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
