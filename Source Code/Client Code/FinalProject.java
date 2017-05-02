package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.text.PlainDocument;
import jsyntaxpane.syntaxkits.*;

public class FinalProject extends javax.swing.JFrame {

    //*****************variable declaration*************\\
    JMenuItem itemFont, itemFontColor, itemBackgroundColor;
//    JCheckBoxMenuItem wordWrap;
    String fileName = null;
    JFileChooser fileChooser;
    String fileContent;
    UndoManager undo;
    UndoAction undoAction;
    RedoAction redoAction;
    String findText;
    FontHelper font;
    int fnext = 1;
    int cursorPosition;
    boolean receiveBoolean = true;
//    public static EditMaster em;

    //******************variables for find and replace********\\
    StringBuffer sbufer;
    String findString;
    int ind = 0;

    //******************variables for networking*************\\
    String userName;
    private String saveFileName = null;
    private String saveAsFileName = null;
    private Socket clientSocket = null;
    // The output stream
    private PrintStream os = null;
    // The input stream
    private DataInputStream is = null;
    private BufferedReader br = null;
    private boolean closed = false;
    public ArrayList<String> users = new ArrayList<String>();
    private String serverIpAddress;

    public FinalProject(String serverIpAddress, String userName) throws IOException {
        this.serverIpAddress = serverIpAddress;
        initComponents();
        undo = new UndoManager();
        undoAction = new UndoAction();
        redoAction = new RedoAction();

        this.userName = userName;

        setTitle("Untitled - Edit Master");//sets the userName
        super.setBounds(240, 100, 920, 645);

        itemFont = new JMenuItem("Font");
        itemFont.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK + java.awt.event.InputEvent.SHIFT_MASK));
        itemFont.setFont(new java.awt.Font("Times New Roman", 0, 14));
        itemFont.setText("Font");
        menuFormat.add(itemFont);

        itemFontColor = new JMenuItem("Font Color");
        itemFontColor.setFont(new java.awt.Font("Times New Roman", 0, 14));
        itemFontColor.setText("Font Color");
        menuFormat.add(itemFontColor);

        itemBackgroundColor = new JMenuItem("Background Color");
        itemBackgroundColor.setFont(new java.awt.Font("Times New Roman", 0, 14));
        itemBackgroundColor.setText("Background Color");
        menuFormat.add(itemBackgroundColor);

//        wordWrap = new JCheckBoxMenuItem("Word Wrap");
//        wordWrap.setFont(new java.awt.Font("Times New Roman", 0, 14));
//        wordWrap.setText("Word Wrap");
//        menuFormat.add(wordWrap);
        fileChooser = new JFileChooser(".");

        font = new FontHelper();

        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        mainarea.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent ce) {
                int pos = mainarea.getCaretPosition();
                Element map = mainarea.getDocument().getDefaultRootElement();
                int row = map.getElementIndex(pos);
                Element lineElem = map.getElement(row);
                int col = pos - lineElem.getStartOffset();
                if (mainarea.getText().length() == 0) {
                    row = 0;
                    col = 0;
                }

                statusBar.setText("||       Ln " + (row + 1) + ", Col " + (col + 1));
            }
        });

        itemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                New();
            }
        });
        itemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Open();
            }
        });
        itemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    save(mainarea.getText());
                    String str = "sAvE" + saveFileName + " " + mainarea.getText();
                    send snd = new send(str);
                    snd.start();
                    snd.join();
                    String msg = "ChAt" + userName + " saved the file( " + saveFileName + " )!";
                    new send(msg).start();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FinalProject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        itemSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs();
                try {
                    String str = "sAvE" + saveFileName + " " + mainarea.getText();
                    send snd = new send(str);
                    snd.start();
                    snd.join();
                    String msg = "ChAt" + userName + " saved the file( " + saveFileName + " )!";
                    new send(msg).start();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FinalProject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        itemCut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainarea.cut();
            }
        });
        itemCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainarea.copy();
            }
        });
        itemPaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainarea.paste();
            }
        });
        mainarea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undo.addEdit(e.getEdit());
                undoAction.update();
                redoAction.update();
            }
        });

        itemFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                font.setVisible(true);
            }
        });
        font.getOk().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainarea.setFont(font.getfont());
                font.setVisible(false);
            }
        });
        itemFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                font.setVisible(false);
            }
        });

        itemFontColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(rootPane, "Choose Font Color", Color.BLACK);
                mainarea.setForeground(c);

            }
        });

        itemBackgroundColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(rootPane, "Choose Background Color", Color.BLACK);
                mainarea.setBackground(c);
            }
        });

//        wordWrap.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (wordWrap.isSelected()) {
////                    mainarea.set
////                    mainarea.setLineWrap(true);
////                    mainarea.setWrapStyleWord(true);
//                } else {
////                    mainarea.setLineWrap(false);
////                    mainarea.setWrapStyleWord(false);
//                }
//            }
//        });
        itemFind.addActionListener(FINDEDIT);
        itemReplace.addActionListener(REPLACEDIT);
        itemGoto.addActionListener(GOTOEDIT);
        itemTime.addActionListener(TIMEDIT);
        itemDelete.addActionListener(DELETEDIT);
        itemPrint.addActionListener(PRINT);

        //*************Syntax Highlighting*************\\
        //C
        itemCformating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int choice = JOptionPane
                        .showConfirmDialog(
                                null,
                                "This will clear the current text mnEditor. Do you want to save the file?",
                                "Warning", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    save(mainarea.getText());
                }
                setTitle("Untitled - Edit Master");

                mainarea.setBackground(Color.white);
                mainarea.setFont((new java.awt.Font("Consolas", 0, 18)));
                CSyntaxKit.initKit();
                mainarea.setContentType("text/c");
            }
        });
        //C++
        itemcplusplusformating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int choice = JOptionPane
                        .showConfirmDialog(
                                null,
                                "This will clear the current text mnEditor. Do you want to save the file?",
                                "Warning", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    save(mainarea.getText());
                }
                setTitle("Untitled - Edit Master");
                mainarea.setBackground(Color.white);
                mainarea.setFont((new java.awt.Font("Consolas", 0, 18)));
                CSyntaxKit.initKit();
                mainarea.setContentType("text/cpp");
            }
        });
        //Java
        itemJavaFormatting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int choice = JOptionPane
                        .showConfirmDialog(
                                null,
                                "This will clear the current text mnEditor. Do you want to save the file?",
                                "Warning", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    save(mainarea.getText());
                }
                mainarea.setBackground(Color.white);
                mainarea.setFont((new java.awt.Font("Consolas", 0, 18)));
                CSyntaxKit.initKit();
                mainarea.setContentType("text/java");
            }
        });
        //python
        itemPython.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int choice = JOptionPane
                        .showConfirmDialog(
                                null,
                                "This will clear the current text mnEditor. Do you want to save the file?",
                                "Warning", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    save(mainarea.getText());
                }
                setTitle("Untitled - Edit Master");
                mainarea.setBackground(Color.white);
                mainarea.setFont((new java.awt.Font("Consolas", 0, 18)));
                CSyntaxKit.initKit();
                mainarea.setContentType("text/python");
            }
        });

        //******************* 
        clientSocket = new Socket("localhost", 5555);
        is = new DataInputStream(clientSocket.getInputStream());
        os = new PrintStream(clientSocket.getOutputStream());
        new recieve().start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem8 = new javax.swing.JMenuItem();
        chatLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        chatField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        userList = new javax.swing.JTextArea();
        logOutButton = new javax.swing.JButton();
        statusBar = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        mainarea = new javax.swing.JEditorPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        itemNew = new javax.swing.JMenuItem();
        itemOpen = new javax.swing.JMenuItem();
        itemSave = new javax.swing.JMenuItem();
        itemSaveAs = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemPrint = new javax.swing.JMenuItem();
        itemTime = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        itemExit = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        editMenu = new javax.swing.JMenu();
        itemUndo = new javax.swing.JMenuItem();
        itemRedo = new javax.swing.JMenuItem();
        itemCut = new javax.swing.JMenuItem();
        itemCopy = new javax.swing.JMenuItem();
        itemPaste = new javax.swing.JMenuItem();
        itemDelete = new javax.swing.JMenuItem();
        itemFind = new javax.swing.JMenuItem();
        itemReplace = new javax.swing.JMenuItem();
        itemGoto = new javax.swing.JMenuItem();
        menuFormat = new javax.swing.JMenu();
        SyntaxHighlighting = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        itemCformating = new javax.swing.JMenuItem();
        itemcplusplusformating = new javax.swing.JMenuItem();
        itemJavaFormatting = new javax.swing.JMenuItem();
        itemPython = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenuItem8.setText("jMenuItem8");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 255, 255));

        chatLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        chatLabel.setText("                          CHAT BOX                  ");

        chatArea.setBackground(new java.awt.Color(51, 51, 255));
        chatArea.setColumns(20);
        chatArea.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        chatArea.setForeground(new java.awt.Color(255, 255, 255));
        chatArea.setRows(5);
        chatArea.setTabSize(4);
        chatArea.setCaretColor(new java.awt.Color(255, 255, 255));
        chatArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        chatArea.setFocusable(false);
        jScrollPane1.setViewportView(chatArea);

        sendButton.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        sendButton.setText("SEND");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        chatField.setBackground(new java.awt.Color(204, 204, 204));
        chatField.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        chatField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chatFieldKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("    ACTIVE USERS");

        userList.setBackground(new java.awt.Color(0, 102, 102));
        userList.setColumns(20);
        userList.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        userList.setForeground(new java.awt.Color(255, 255, 255));
        userList.setRows(5);
        userList.setCaretColor(new java.awt.Color(255, 255, 255));
        userList.setFocusable(false);
        jScrollPane2.setViewportView(userList);

        logOutButton.setBackground(new java.awt.Color(204, 204, 204));
        logOutButton.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        logOutButton.setForeground(new java.awt.Color(153, 0, 102));
        logOutButton.setText("LOG OUT");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        statusBar.setBackground(new java.awt.Color(0, 0, 0));

        mainarea.setBackground(new java.awt.Color(51, 0, 51));
        mainarea.setFont(new java.awt.Font("Consolas", 2, 36)); // NOI18N
        mainarea.setForeground(new java.awt.Color(255, 255, 255));
        mainarea.setCaretColor(new java.awt.Color(255, 255, 255));
        mainarea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mainareaKeyReleased(evt);
            }
        });
        mainarea.getDocument().putProperty(PlainDocument.tabSizeAttribute, 4);
        jScrollPane4.setViewportView(mainarea);

        jMenuBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jMenuBar1.setForeground(new java.awt.Color(240, 240, 240));
        jMenuBar1.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        jMenuBar1.setMaximumSize(new java.awt.Dimension(58, 100));

        fileMenu.setText("File");
        fileMenu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        itemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        itemNew.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemNew.setText("New");
        itemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNewActionPerformed(evt);
            }
        });
        fileMenu.add(itemNew);

        itemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        itemOpen.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemOpen.setText("Open");
        fileMenu.add(itemOpen);

        itemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        itemSave.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemSave.setText("Save");
        itemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSaveActionPerformed(evt);
            }
        });
        fileMenu.add(itemSave);

        itemSaveAs.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemSaveAs.setText("Save As");
        fileMenu.add(itemSaveAs);
        fileMenu.add(jSeparator1);

        itemPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        itemPrint.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemPrint.setText("Print");
        fileMenu.add(itemPrint);

        itemTime.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemTime.setText("Time & Date");
        fileMenu.add(itemTime);
        fileMenu.add(jSeparator2);

        itemExit.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemExit.setText("Exit");
        fileMenu.add(itemExit);

        jMenuBar1.add(fileMenu);
        jMenuBar1.add(jMenu5);

        editMenu.setText("Edit");
        editMenu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        itemUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        itemUndo.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemUndo.setText("Undo");
        itemUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemUndoActionPerformed(evt);
            }
        });
        editMenu.add(itemUndo);

        itemRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        itemRedo.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemRedo.setText("Redo");
        itemRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRedoActionPerformed(evt);
            }
        });
        editMenu.add(itemRedo);

        itemCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        itemCut.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemCut.setText("Cut");
        editMenu.add(itemCut);

        itemCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        itemCopy.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemCopy.setText("Copy");
        itemCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCopyActionPerformed(evt);
            }
        });
        editMenu.add(itemCopy);

        itemPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        itemPaste.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemPaste.setText("Paste");
        itemPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPasteActionPerformed(evt);
            }
        });
        editMenu.add(itemPaste);

        itemDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        itemDelete.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemDelete.setText("Delete");
        itemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemDeleteActionPerformed(evt);
            }
        });
        editMenu.add(itemDelete);

        itemFind.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        itemFind.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemFind.setText("Find");
        itemFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFindActionPerformed(evt);
            }
        });
        editMenu.add(itemFind);

        itemReplace.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        itemReplace.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemReplace.setText("Replace");
        itemReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemReplaceActionPerformed(evt);
            }
        });
        editMenu.add(itemReplace);

        itemGoto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        itemGoto.setText("Go To");
        editMenu.add(itemGoto);

        jMenuBar1.add(editMenu);

        menuFormat.setText("Format");
        menuFormat.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jMenuBar1.add(menuFormat);

        SyntaxHighlighting.setText("Highlighting");
        SyntaxHighlighting.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jMenuItem1.setText("Plane Text");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        SyntaxHighlighting.add(jMenuItem1);

        itemCformating.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemCformating.setText("C");
        itemCformating.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCformatingActionPerformed(evt);
            }
        });
        SyntaxHighlighting.add(itemCformating);

        itemcplusplusformating.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemcplusplusformating.setText("C++");
        itemcplusplusformating.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemcplusplusformatingActionPerformed(evt);
            }
        });
        SyntaxHighlighting.add(itemcplusplusformating);

        itemJavaFormatting.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemJavaFormatting.setText("JAVA");
        SyntaxHighlighting.add(itemJavaFormatting);

        itemPython.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        itemPython.setText("Python");
        SyntaxHighlighting.add(itemPython);

        jMenuBar1.add(SyntaxHighlighting);

        helpMenu.setText("Help");
        helpMenu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jMenuItem2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jMenuItem2.setText("About");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem2);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chatLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(44, 44, 44)
                                .addComponent(logOutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chatField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logOutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(chatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                    .addComponent(chatField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jScrollPane4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void itemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSaveActionPerformed

    }//GEN-LAST:event_itemSaveActionPerformed

    private void itemCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCopyActionPerformed

    }//GEN-LAST:event_itemCopyActionPerformed

    private void itemPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPasteActionPerformed

    }//GEN-LAST:event_itemPasteActionPerformed

    private void itemFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemFindActionPerformed

    private void itemReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemReplaceActionPerformed

    }//GEN-LAST:event_itemReplaceActionPerformed

    private void itemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNewActionPerformed

    }//GEN-LAST:event_itemNewActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        if (chatArea.getText().trim() != "") {
            chatArea.setText(chatArea.getText().trim() + "\n  " + userName + ": " + chatField.getText());
            String msg = "ChAt" + userName + ": " + chatField.getText();
            new send(msg).start();

            chatField.setText("");
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    //**************************************Enter key Pressed****************************

    private void chatFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chatFieldKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER && chatArea.getText().trim() != "") {
            chatArea.setText(chatArea.getText().trim() + "\n  " + userName + ": " + chatField.getText());
            String s1;
            s1 = chatField.getText();
            if (s1.length() != 0) {
                s1 = "ChAt" + userName + ": " + s1;
            }
            new send(s1).start();
            this.chatField.setText("");
        }
    }//GEN-LAST:event_chatFieldKeyPressed

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        try {
            
            int option = JOptionPane.showConfirmDialog(this, "Do you want to save the file?");
            System.out.println(option);
            if (option == 0) {
                receiveBoolean = false;
                save(mainarea.getText());

                String str = "sAvE" + saveFileName + " " + mainarea.getText();
                send snd = new send(str);
                snd.start();
                snd.join();
                String msg = "ChAt" + userName + " saved the file( " + saveFileName + " )!";
                send snd1 = new send(msg);
                snd1.start();
                snd1.join();
                send snd2 = new send("/quit");
                snd2.start();
                new Online(serverIpAddress).setVisible(true);
                this.dispose();
            } else if (option == 1) {
                receiveBoolean = false;
                send snd2 = new send("/quit");
                snd2.start();
                new Online(serverIpAddress).setVisible(true);
                this.dispose();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(FinalProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_logOutButtonActionPerformed

    private void itemUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemUndoActionPerformed
        undoAction.actionPerformed(evt);
    }//GEN-LAST:event_itemUndoActionPerformed

    private void itemRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRedoActionPerformed
        redoAction.actionPerformed(evt);
    }//GEN-LAST:event_itemRedoActionPerformed

    private void itemcplusplusformatingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemcplusplusformatingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemcplusplusformatingActionPerformed

    private void mainareaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mainareaKeyReleased
        String text = mainarea.getText();
        cursorPosition = mainarea.getCaretPosition();
        text = "TeXt" + text;
        new send(text).start();
    }//GEN-LAST:event_mainareaKeyReleased

    private void itemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemDeleteActionPerformed


    }//GEN-LAST:event_itemDeleteActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int choice = JOptionPane
                .showConfirmDialog(
                        null,
                        "This will clear the current text mnEditor. Do you want to save the file?",
                        "Warning", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            save(mainarea.getText());
        }
        setTitle("Untitled - Edit Master");
        mainarea.setBackground(new java.awt.Color(51, 0, 51));
        mainarea.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        mainarea.setForeground(new java.awt.Color(255, 255, 255));
        mainarea.setCaretColor(new java.awt.Color(255, 255, 255));
        mainarea.setContentType("text/txt");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void itemCformatingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCformatingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemCformatingActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new frameAbout().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * ***************two threads class*************
     */
    class recieve extends Thread {

        recieve() {

        }

        public void run() {
            try {
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (receiveBoolean) {
                    String msg = "";
                    // System.out.println(br);
                    msg = readUntilNull(br);
                    //System.out.println("Message from Server: Amitttttttttt " + msg);
                    //System.out.println(getCommand(msg));
                    if (getCommand(msg).equals("chat")) {
                        msg = msg.substring(4);
                        chatArea.setText(chatArea.getText().trim() + "\n  " + msg);
                    } else if (getCommand(msg).equals("text")) {
                        msg = msg.substring(4);
                        mainarea.setText(msg);
                        // mainarea.setCaretPosition(cursorPosition);
                    } else if (getCommand(msg).equals("list")) {
                        getUserList(msg);
                    } else if (getCommand(msg).equals("logout")) {

                    } else if (getCommand(msg).equals("save")) {

                        romoteSave(msg);
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + ", file name is found null!!");
            }
        }
    }

    /**
     * ******************extra method*****************\
     */
    public String readUntilNull(BufferedReader reader) {
        StringBuilder sb = new StringBuilder();
        String message = "";
        try {
            int ch;
            while ((ch = reader.read()) != -1) {
                if (ch == 0) {
                    message = sb.toString();
                    break;
                } else if (ch == 10) {
                    sb.append("\n");
                } else {
                    sb.append((char) ch);
                }
            }
        } catch (InterruptedIOException ex) {
            System.err.println("timeout!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("messgare useruserName : " + message);
        return message;
    }

    String getCommand(String msg) {
       // System.out.println("GetCOmmand " + msg);
        if (msg.startsWith("ChAt")) {
            return "chat";
        } else if (msg.startsWith("LiSt")) {
            return "list";
        } else if (msg.startsWith("TeXt")) {
            return "text";
        } else if (msg.startsWith("/quit")) {
            return "logOut";
        } else if (msg.startsWith("sAvE")) {
            return "save";
        }
        return "code";
    }

    private void getUserList(String str) { //decodes string containing users and adds it to users array list
        str = str.substring(4);
        if (!str.startsWith("|") || !str.endsWith("|") || str.equals("|") || str.equals(null)) {
            return;
        }

        String s = str.substring(1, str.length() - 1); //eg: from "|user1|user2|user3|" to "user1|user2|user3"
        String[] arr = s.split("\\|"); //eg: from "user1|user2|user3" to "user1", "user2", "user3"
        users.clear();

        for (String usr : arr) {
            users.add(usr);
        }
        String finalList = "";
        for (int i = 0; i < users.size(); i++) {
            //System.out.println(users.get(i) + "eeeeeeeeeeeee");
            finalList += "  * " + users.get(i) + "\n";
        }
        //System.out.println(finalList);
        userList.setText(finalList);

    }

    class send extends Thread {

        String str;

        public send(String str) {
            this.str = str;
        }

        public void run() {
            try {
                os.print(str);
                os.write(0x00);
                os.flush();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //************************** SAVE **************************\\
    public void save(String s) {
        PrintWriter fout = null;
        try {
            if (fileName == null) {
                saveAs();
            } else {
                fout = new PrintWriter(new FileWriter(fileName));
                //String s = mainarea.getText();
                StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
                while (st.hasMoreElements()) {
                    fout.println(st.nextToken());
                }
                JOptionPane.showMessageDialog(this, "File Saved");
                fileName = fileChooser.getSelectedFile().getName();
                //setTitle(fileName + " - Edit Master");
                fileContent = mainarea.getText();
            }
        } catch (IOException e) {
        } finally {
            if (fout != null) {
                fout.close();
            }
        }
    }

    public void romoteSave(String s) {
        PrintWriter fout = null;
        s = getActualFile(s);
       // System.out.println(s);
        try {
//            if (fileChooser.getSelectedFile().exists()) {
//                fout = new PrintWriter(new FileWriter("conflicted copy-" + fileName));
//
//            } else {

            fout = new PrintWriter(new FileWriter(fileName));
            //}
            //System.out.println(fout+" rsssssss" + fileChooser);
            //String s = mainarea.getText();
            StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
            while (st.hasMoreElements()) {
                String str = st.nextToken();
                fout.println(str);
                //  System.out.println(str);
            }
            //JOptionPane.showMessageDialog(this, "File Saved");

            //fileName = fileChooser.getSelectedFile().getName();
            //System.out.println(fileName);
            saveFileName = fileName;
            //setTitle(fileName + " - Edit Master");
            fileContent = s;
        } catch (IOException e) {
        } finally {
            if (fout != null) {
                fout.close();
            }
        }
    }

    String getActualFile(String msg) {
        msg = msg.substring(4);
        int i = 0;
        while (msg.charAt(i) != ' ') {
            i++;
            System.out.println(i);
        }
        fileName = msg.substring(0, i);
        //System.out.println("get "+ fileName);
        msg = msg.substring(i + 1);
        return msg;
    }

    //************************** SAVE AS **************************\\
    public void saveAs() {
        PrintWriter fout = null;
        int retval = -1;
        try {
            retval = fileChooser.showSaveDialog(this);// to check whether user decides to save the file or not
            if (retval == JFileChooser.APPROVE_OPTION) {

                if (fileChooser.getSelectedFile().exists()) {
                    int option = JOptionPane.showConfirmDialog(this, "Do you want to replace this file?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
                    if (option == 0) {
                        fout = new PrintWriter(new FileWriter(fileChooser.getSelectedFile()));
                        String s = mainarea.getText();
                        StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
                        while (st.hasMoreElements()) {
                            fout.println(st.nextToken());
                        }
                        JOptionPane.showMessageDialog(this, "File Saved");
                        fileName = fileChooser.getSelectedFile().getName();
                        saveFileName = fileName;
                        //setTitle(fileName + " - Edit Master");
                        fileContent = mainarea.getText();
                    } else {
                        saveAs();
                    }

                } else {
                    fout = new PrintWriter(new FileWriter(fileChooser.getSelectedFile()));
                    String s = mainarea.getText();
                    StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
                    while (st.hasMoreElements()) {
                        fout.println(st.nextToken());
                    }
                    JOptionPane.showMessageDialog(this, "File Saved");
                    fileName = fileChooser.getSelectedFile().getName();
                    saveFileName = fileName;
                    //setTitle(fileName + " - Edit Master");
                    fileContent = mainarea.getText();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null) {
                fout.close();
            }
        }
    }

    //************************** OPEN **********************\\
    public void Open() {
        int retval = -1;
        try {
            retval = fileChooser.showOpenDialog(this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                mainarea.setText("");
                Reader in = new FileReader(fileChooser.getSelectedFile());
                char[] buffer = new char[100000];
                int nch;
                while ((nch = in.read(buffer, 0, buffer.length)) != -1) {
                    mainarea.setText(new String(buffer, 0, nch));
                }
                fileName = fileChooser.getSelectedFile().getName();

                setTitle(fileName + " - Edit Master");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //************************** NEW *************************\\
    public void New() {
        if (!mainarea.getText().equals("") && !mainarea.getText().equals(fileContent)) {
            if (fileName == null) {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save the changes?");
                if (option == 0) {
                    saveAs();
                    clear();
                } else {
                    clear();
                }

            } else {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save the changes?");
                if (option == 0) {
                    save(mainarea.getText());
                    clear();
                } else {
                    clear();
                }
            }
        } else {
            clear();
        }
    }

    public void clear() {
        mainarea.setText(null);

        setTitle("Untitled - Edit Master");

        fileName = null;
        fileContent = null;
    }

    //***********************PRINT************************\\
    Action PRINT = new AbstractAction("Print") {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
            if (mainarea.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Text Area is empty.");
            } else {
                print(createBuffer());
            }

        }
    };

    public String createBuffer() {
        String buffer;
        buffer = mainarea.getText();
        return buffer;
    }

    private void print(String s) {
        StringReader sr = new StringReader(s);
        LineNumberReader lnr = new LineNumberReader(sr);
        Font typeface = new Font("Monospaced", Font.PLAIN, 12);
        Properties p = new Properties();
        PrintJob pjob = getToolkit().getPrintJob(this, "Print report", p);

        if (pjob != null) {
            Graphics pg = pjob.getGraphics();
            if (pg != null) {
                FontMetrics fm = pg.getFontMetrics(typeface);
                int margin = 20;
                int pageHeight = pjob.getPageDimension().height - margin;
                int fontHeight = fm.getHeight();
                int fontDescent = fm.getDescent();
                int curHeight = margin;

                String nextLine;
                pg.setFont(mainarea.getFont());

                try {
                    do {
                        nextLine = lnr.readLine();
                        if (nextLine != null) {
                            if ((curHeight + fontHeight) > pageHeight) { // New Page
                                pg.dispose();
                                pg = pjob.getGraphics();
                                curHeight = margin;
                            }

                            curHeight += fontHeight;

                            if (pg != null) {
                                pg.setFont(typeface);
                                pg.drawString(nextLine, margin, curHeight - fontDescent);
                            }
                        }
                    } while (nextLine != null);

                } catch (EOFException eof) {
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            pg.dispose();
        }
        if (pjob != null) {
            pjob.end();
        }
    }

    Action TIMEDIT = new AbstractAction("Time/Date") {
        public void actionPerformed(ActionEvent e) {
            Date currentDate;
            SimpleDateFormat formatter;
            String dd;
            formatter = new SimpleDateFormat("KK:mm aa MMMMMMMMM dd yyyy", Locale.getDefault());
            currentDate = new java.util.Date();
            dd = formatter.format(currentDate);

            try {
                mainarea.getDocument().insertString(mainarea.getCaretPosition(), dd, null);
            } catch (BadLocationException ex) {
                Logger.getLogger(FinalProject.class.getName()).log(Level.SEVERE, null, ex);
            }
            //force sending to all other client
            os.print("TeXt" + mainarea.getText());
            os.write(0x00);
            os.flush();

        }
    };

    Action DELETEDIT = new AbstractAction("Delete") {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
            mainarea.replaceSelection(null);
            os.print("TeXt" + mainarea.getText());
            os.write(0x00);
            os.flush();
        }
    };

    Action FINDEDIT = new AbstractAction("Find") {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
            try {
                sbufer = new StringBuffer(mainarea.getText());
                findString = JOptionPane.showInputDialog(null, "Find");
                ind = sbufer.indexOf(findString);
                mainarea.setCaretPosition(ind);
                mainarea.setSelectionStart(ind);
                mainarea.setSelectionEnd(ind + findString.length());
            } catch (IllegalArgumentException npe) {
                JOptionPane.showMessageDialog(null, "String not found");
            } catch (NullPointerException nfe) {
            }
        }
    };

    Action REPLACEDIT = new AbstractAction("Replace") {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
            try {
                String replace = JOptionPane.showInputDialog(null, "Replace");
                mainarea.replaceSelection(replace);
                os.print("TeXt" + mainarea.getText());
                os.write(0x00);
                os.flush();
            } catch (NumberFormatException nfe) {
            }
        }
    };

    Action GOTOEDIT = new AbstractAction("Go To") {
        public void actionPerformed(ActionEvent e) {
            try {
                Element root = mainarea.getDocument().getDefaultRootElement();
                Element paragraph = root.getElement(Integer.parseInt(JOptionPane.showInputDialog(null, "Go to line")));
                mainarea.setCaretPosition(paragraph.getStartOffset() - 1);
            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null, "Invalid line number");
            } catch (NumberFormatException nfe) {

            }
        }
    };

//    public static JTextArea getArea() {
//       // return mainarea;
//    }
    //*************** UNDO ACTION **********************\\
    class UndoAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (undo.canUndo()) {
                try {

                    undo.undo();
                } catch (CannotUndoException ex) {
                    ex.printStackTrace();
                }
                update();
                redoAction.update();
            }
        }

        protected void update() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, "Undo");
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }

    }

    //************************ REDO ACTION *************************\\
    class RedoAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (undo.canRedo()) {
                try {
                    undo.redo();
                } catch (CannotRedoException ex) {
                    ex.printStackTrace();
                }
            }
            update();
            undoAction.update();
        }

        protected void update() {
            if (undo.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, "Redo");
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }

    }

    public static void main(String args[]) throws Exception {
        // new FinalProject("AMIT").setVisible(true);
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FinalProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FinalProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FinalProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FinalProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu SyntaxHighlighting;
    private javax.swing.JTextArea chatArea;
    private javax.swing.JTextField chatField;
    private javax.swing.JLabel chatLabel;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem itemCformating;
    private javax.swing.JMenuItem itemCopy;
    private javax.swing.JMenuItem itemCut;
    private javax.swing.JMenuItem itemDelete;
    private javax.swing.JMenuItem itemExit;
    private javax.swing.JMenuItem itemFind;
    private javax.swing.JMenuItem itemGoto;
    private javax.swing.JMenuItem itemJavaFormatting;
    private javax.swing.JMenuItem itemNew;
    private javax.swing.JMenuItem itemOpen;
    private javax.swing.JMenuItem itemPaste;
    private javax.swing.JMenuItem itemPrint;
    private javax.swing.JMenuItem itemPython;
    private javax.swing.JMenuItem itemRedo;
    private javax.swing.JMenuItem itemReplace;
    private javax.swing.JMenuItem itemSave;
    private javax.swing.JMenuItem itemSaveAs;
    private javax.swing.JMenuItem itemTime;
    private javax.swing.JMenuItem itemUndo;
    private javax.swing.JMenuItem itemcplusplusformating;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JButton logOutButton;
    private javax.swing.JEditorPane mainarea;
    private javax.swing.JMenu menuFormat;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel statusBar;
    private javax.swing.JTextArea userList;
    // End of variables declaration//GEN-END:variables
}
