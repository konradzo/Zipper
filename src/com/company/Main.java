package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main extends JFrame {

    public Main() {
        super("Zipper");
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int appHeight = screenHeight / 4;
        int appWidth = screenWidth / 4;
        this.setBounds((screenWidth - appWidth) / 2, (screenHeight - appHeight) / 2, appWidth, appHeight);

        initComponents();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private DefaultListModel listModel = new DefaultListModel() {
        @Override
        public void addElement(Object element) {
            super.addElement(((File) element).getName());
            arrayList.add(element);
        }

        @Override
        public Object get(int index) {
            return arrayList.get(index);
        }

        @Override
        public Object remove(int index) {
            list.remove(index);
            return super.remove(index);
        }

        ArrayList arrayList = new ArrayList();
    };
    private JList list = new JList(listModel);
    private JButton addButton;
    private JButton deleteButton;
    private JButton zipButton;
    private JMenuBar menuBar = new JMenuBar();
    private JFileChooser chooser = new JFileChooser();
    JScrollPane scrollPane = new JScrollPane(list);

    public void initComponents() {

        Action addingAction = new ButtonAction("Add", "Add an entry to the archive", "ctrl A", new ImageIcon("add.png"));
        Action deletingAction = new ButtonAction("Delete", "Delete an entry from the archive", "ctrl D", new ImageIcon("delete.png"));
        Action zippingAction = new ButtonAction("Zip", "Zip all files", "ctrl Z");

        this.setJMenuBar(menuBar);
        JMenu menuFile = menuBar.add(new JMenu("File"));
        JMenuItem openMenu = menuFile.add(addingAction);
        JMenuItem deleteMenu = menuFile.add(deletingAction);
        JMenuItem zipMenu = menuFile.add(zippingAction);

        addButton = new JButton(addingAction);
        deleteButton = new JButton(deletingAction);
        zipButton = new JButton(zippingAction);
        list.setBorder(BorderFactory.createEtchedBorder());

        GroupLayout layout = new GroupLayout(this.getContentPane());

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(scrollPane, 150, 150, Short.MAX_VALUE)
                        .addContainerGap(0, Short.MAX_VALUE)
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(addButton)
                                        .addComponent(deleteButton)
                                        .addComponent(zipButton)
                        )
        );

        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(addButton)
                                        .addComponent(deleteButton)
                                        .addGap(0, 10, Short.MAX_VALUE)
                                        .addComponent(zipButton)
                        )
        );


        this.getContentPane().setLayout(layout);
        //this.pack();

    }


    public static void main(String[] args) {
        new Main().setVisible(true);
    }

    private class ButtonAction extends AbstractAction {

        public ButtonAction(String buttonName, String description, String keybordShortcut) {
            this.putValue(Action.NAME, buttonName);
            this.putValue(Action.SHORT_DESCRIPTION, description);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keybordShortcut));
        }

        public ButtonAction(String buttonName, String description, String keybordShortcut, Icon icon) {
            this(buttonName, description, keybordShortcut);
            this.putValue(Action.SMALL_ICON, icon);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Add")) {
                addEntryToArchive();
            } else if (e.getActionCommand().equals("Delete")) {
                deleteEntryFromArchive();
            } else if (e.getActionCommand().equals("Zip")) {
                createZipArchive();
            }
        }

        private void addEntryToArchive() {
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.setMultiSelectionEnabled(true);
            int tmp = chooser.showDialog(rootPane, "Add to archive");
            if (tmp == JFileChooser.APPROVE_OPTION) {
                File[] paths = chooser.getSelectedFiles();
                for (int i = 0; i < paths.length; i++) {
                    if (!(whetherEntryRepeats(paths[i].getPath())))
                        listModel.addElement(paths[i]);
                }
            }
        }

        private boolean whetherEntryRepeats(String tmpEntry) {
            for (int i = 0; i < listModel.getSize(); i++) {
                if (((File) listModel.get(i)).getPath().equals(tmpEntry)) {
                    return true;
                }
            }
            return false;
        }

        private void deleteEntryFromArchive() {
            int[] tmp = list.getSelectedIndices();
            for (int i = 0; i < tmp.length; i++) {
                listModel.remove(tmp[i] - i);
            }
        }

        private void createZipArchive() {
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            chooser.setSelectedFile(new File(System.getProperty("user.dir") + File.separator + "archive.zip"));
            int tmp = chooser.showDialog(rootPane, "Zip files");

            if (tmp == JFileChooser.APPROVE_OPTION) {
                byte[] tempData = new byte[BUFFOR];
                try {

                    ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(chooser.getSelectedFile()), BUFFOR));

                    for (int i = 0; i < listModel.getSize(); i++) {
                        if (!((File) listModel.get(i)).isDirectory())
                            zipping(zipOutputStream, (File) listModel.get(i), tempData, ((File) listModel.get(i)).getPath());
                        else {
                            printPaths((File) listModel.get(i));

                            for (int j = 0; j < pathList.size(); j++) {
                                zipping(zipOutputStream, (File) pathList.get(j), tempData, ((File) listModel.get(i)).getPath());
                            }
                            pathList.removeAll(pathList);
                        }
                    }
                    zipOutputStream.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            }
        }

        private void zipping(ZipOutputStream zipOutputStream, File filePath, byte[] tempData, String basePath) throws IOException {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath), BUFFOR);
            zipOutputStream.putNextEntry(new ZipEntry(filePath.getPath().substring(basePath.lastIndexOf(File.separator) + 1)));

            int counter;
            while ((counter = inputStream.read(tempData, 0, BUFFOR)) != -1) {
                zipOutputStream.write(tempData, 0, counter);
            }
            zipOutputStream.closeEntry();

            inputStream.close();
        }

        private void printPaths(File filePath) {
            String[] fileNames = filePath.list();
            for (int i = 0; i < fileNames.length; i++) {
                File p = new File(filePath.getPath(), fileNames[i]);
                if (p.isFile())
                    pathList.add(p);
                if(p.isDirectory())
                    printPaths(new File(p.getPath()));
            }
        }

        public static final int BUFFOR = 1024;
        ArrayList pathList = new ArrayList();
    }
}
