package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;

import main.java.memoranda.util.AppList;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.MimeType;
import main.java.memoranda.util.MimeTypesList;
import main.java.memoranda.util.Util;

/*$Id: ResourceTypePanel.java,v 1.8 2004/10/18 19:09:10 ivanrise Exp $*/
public class ResourceTypePanel extends JPanel {
    Border border1;
    TitledBorder titledBorder1;

    Border border2;
    TitledBorder titledBorder2;
    public String ext = "";
    boolean CANCELLED = true;
  JPanel jPanel1 = new JPanel();
  JButton newTypeB = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel areaPanel = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton editB = new JButton();
  JButton deleteB = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();
  public JList typesList = new JList();
  BorderLayout borderLayout3 = new BorderLayout();
  Border border3;

    public ResourceTypePanel() {
        super();
        try {
            jbInit();

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        border1 = BorderFactory.createLineBorder(SystemColor.controlText, 2);
        titledBorder1 = new TitledBorder(BorderFactory.createEmptyBorder(),Local.getString("Registered types"));
        border2 = BorderFactory.createLineBorder(Color.gray, 1);
        titledBorder2 = new TitledBorder(BorderFactory.createLineBorder(Color.gray, 1), Local.getString("Details"));
        border3 = BorderFactory.createEmptyBorder(0, 10, 0, 0);


        jPanel1.setBorder(titledBorder1);
    jPanel1.setLayout(borderLayout1);
    newTypeB.setMaximumSize(new Dimension(110, 25));
    newTypeB.setPreferredSize(new Dimension(110, 25));
    newTypeB.setText(Local.getString("New"));
    newTypeB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newTypeB_actionPerformed(e);
            }
        });
    areaPanel.setLayout(borderLayout2);
    jPanel2.setMaximumSize(new Dimension(120, 32767));
    jPanel2.setMinimumSize(new Dimension(120, 36));
    jPanel2.setPreferredSize(new Dimension(120, 36));
    jPanel2.setBorder(border3);
    editB.setText(Local.getString("Edit"));
    editB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editB_actionPerformed(e);
            }
        });
    editB.setEnabled(false);
    editB.setMaximumSize(new Dimension(110, 25));
    editB.setPreferredSize(new Dimension(110, 25));
    deleteB.setEnabled(false);
    deleteB.setMaximumSize(new Dimension(110, 25));
    deleteB.setPreferredSize(new Dimension(110, 25));
    deleteB.setText(Local.getString("Delete"));
    deleteB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteB_actionPerformed(e);
            }
        });
    typesList.setCellRenderer(new TypesListRenderer());
    typesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    typesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        typesList_valueChanged(e);
      }
    });
        initTypesList();

        //typesList.setCellRenderer(new TypesListRenderer());
    this.setLayout(borderLayout3);
    this.add(areaPanel, BorderLayout.CENTER);
    areaPanel.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(typesList, null);
    jPanel1.add(jPanel2, BorderLayout.EAST);
    jPanel2.add(newTypeB, null);
    jPanel2.add(editB, null);
    jPanel2.add(deleteB, null);
    typesList.setListData(MimeTypesList.getAllMimeTypes());
    //typesList.updateUI();

    }

    public void initTypesList() {
        /*Vector v = new Vector();
        icons = new Vector();
        Vector t = MimeTypesList.getAllMimeTypes();
        for (int i = 0; i < t.size(); i++) {
            MimeType mt = (MimeType)t.get(i);
            v.add(mt.getLabel());
            icons.add(mt.getIcon());
        }*/
        typesList.setListData(MimeTypesList.getAllMimeTypes());
        typesList.updateUI();
    }


    void newTypeB_actionPerformed(ActionEvent e) {
        EditTypeDialog dlg = new EditTypeDialog(App.getFrame(), Local.getString("New resource type"));
        Dimension dlgSize = new Dimension(420, 420);
        dlg.setSize(dlgSize);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.extField.setText(ext);
        dlg.descField.setText(ext);
        dlg.appPanel.argumentsField.setText("$1");
        dlg.iconLabel.setIcon(
            new ImageIcon(
                main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/mimetypes/default.png")));
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        String typeId = Util.generateId();
        MimeType mt = MimeTypesList.addMimeType(typeId);
        String[] exts = dlg.extField.getText().trim().split(" ");
        for (int i = 0; i < exts.length; i++)
            mt.addExtension(exts[i]);
        mt.setLabel(dlg.descField.getText());
        AppList appList = MimeTypesList.getAppList();
        if (dlg.appPanel.applicationField.getText().length() > 0) {
            File f = new File(dlg.appPanel.applicationField.getText());
            String appId = Util.generateId();
            appList.addApp(
                appId,
                f.getParent().replace('\\', '/'),
                f.getName().replace('\\', '/'),
                dlg.appPanel.argumentsField.getText());
            mt.setApp(appId);
        }
        if (dlg.iconPath.length() > 0)
            mt.setIconPath(dlg.iconPath);
        CurrentStorage.get().storeMimeTypesList();
        this.initTypesList();
        typesList.setSelectedValue(mt, true);
    }

    void deleteB_actionPerformed(ActionEvent e) {
        MimeType mt = (MimeType) typesList.getSelectedValue();
        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                Local.getString("Delete resource type")
                    + "\n'"
                    + mt.getLabel()
                    + "'\n"
                    + Local.getString("Are you sure?"),
                Local.getString("Delete resource type"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;
        MimeTypesList.removeMimeType(mt.getMimeTypeId());
        CurrentStorage.get().storeMimeTypesList();
        this.initTypesList();
    }

    void editB_actionPerformed(ActionEvent e) {
        EditTypeDialog dlg = new EditTypeDialog(App.getFrame(), Local.getString("Edit resource type"));
        Dimension dlgSize = new Dimension(420, 450);
        dlg.setSize(dlgSize);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        MimeType mt = (MimeType) typesList.getSelectedValue();
        String[] exts = mt.getExtensions();
        String extss = "";
        for (int i = 0; i < exts.length; i++)
            extss += exts[i] + " ";
        dlg.extField.setText(extss);
        dlg.descField.setText(mt.getLabel());
        dlg.iconLabel.setIcon(mt.getIcon());
        AppList appList = MimeTypesList.getAppList();
        dlg.appPanel.applicationField.setText(
            appList.getFindPath(mt.getAppId()) + "/" + appList.getExec(mt.getAppId()));
        dlg.appPanel.argumentsField.setText(appList.getCommandLinePattern(mt.getAppId()));
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        String typeId = mt.getMimeTypeId();
        MimeTypesList.removeMimeType(typeId);
        mt = MimeTypesList.addMimeType(typeId);
        exts = dlg.extField.getText().trim().split(" ");
        for (int i = 0; i < exts.length; i++)
            mt.addExtension(exts[i]);
        mt.setLabel(dlg.descField.getText());
        if (dlg.appPanel.applicationField.getText().length() > 0) {
            File f = new File(dlg.appPanel.applicationField.getText());
            String appId = Util.generateId();
            appList.addApp(
                appId,
                f.getParent().replace('\\', '/'),
                f.getName().replace('\\', '/'),
                dlg.appPanel.argumentsField.getText());
            mt.setApp(appId);
        }
        if (dlg.iconPath.length() > 0)
            mt.setIconPath(dlg.iconPath);
        CurrentStorage.get().storeMimeTypesList();
        this.initTypesList();
        typesList.setSelectedValue(mt, true);

    }


    class TypesListRenderer extends JLabel implements ListCellRenderer {

        public TypesListRenderer() {
            super();
        }

        public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

            MimeType mt = (MimeType) value;
            String[] exts = mt.getExtensions();
            String extstr = "";
            for (int j = 0; j < exts.length; j++) {
                extstr += "*." + exts[j];
                if (j != exts.length - 1)
                    extstr += ", ";
            }

            setOpaque(true);
            setText(mt.getLabel() + " (" + extstr + ")");
            setIcon(mt.getIcon());
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }

    void typesList_valueChanged(ListSelectionEvent e) {
      boolean en = typesList.getSelectedValue() != null;
      this.editB.setEnabled(en);
      this.deleteB.setEnabled(en);
    }

    void buildEditorConfigPanel(final PreferencesDialog preferencesDialog) throws Exception {
    	preferencesDialog.titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(
    			Color.white, new Color(156, 156, 158)), Local
    			.getString("Sound"));
    	preferencesDialog.setResizable(false);
    	// Build Tab1
    	preferencesDialog.jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    	preferencesDialog.jLabel1.setText(Local.getString("Window minimize action:"));
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 0;
    	preferencesDialog.gbc.gridy = 0;
    	preferencesDialog.gbc.insets = new Insets(10, 10, 0, 15);
    	preferencesDialog.gbc.anchor = GridBagConstraints.EAST;
    	preferencesDialog.enableSoundCB.setText(Local.getString("Enable sound notifications"));
    	preferencesDialog.enableSoundCB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.enableSoundCB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.soundPanel.setLayout(preferencesDialog.borderLayout1);
    	preferencesDialog.soundFileBrowseB.setText(Local.getString("Browse"));
    	preferencesDialog.soundFileBrowseB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.soundFileBrowseB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.gridLayout1.setRows(4);
    	preferencesDialog.jPanel1.setBorder(preferencesDialog.titledBorder1);
    	preferencesDialog.jPanel1.setLayout(preferencesDialog.gridLayout1);
    	preferencesDialog.soundBeepRB.setText(Local.getString("System beep"));
    	preferencesDialog.soundBeepRB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.soundBeepRB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.jLabel6.setText(Local.getString("Sound file") + ":");
    	preferencesDialog.soundDefaultRB.setText(Local.getString("Default"));
    	preferencesDialog.soundDefaultRB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.soundDefaultRB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.jPanel3.setLayout(preferencesDialog.borderLayout3);
    	preferencesDialog.soundCustomRB.setText(Local.getString("Custom"));
    	preferencesDialog.soundCustomRB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.soundCustomRB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.jPanel2.setLayout(preferencesDialog.borderLayout2);
    	preferencesDialog.soundPanel.add(preferencesDialog.jPanel2, BorderLayout.CENTER);
    	preferencesDialog.jPanel2.add(preferencesDialog.jPanel1, BorderLayout.NORTH);
    	preferencesDialog.jPanel1.add(preferencesDialog.soundDefaultRB, null);
    	preferencesDialog.jPanel1.add(preferencesDialog.soundBeepRB, null);
    	preferencesDialog.jPanel1.add(preferencesDialog.soundCustomRB, null);
    	preferencesDialog.soundGroup.add(preferencesDialog.soundDefaultRB);
    	preferencesDialog.soundGroup.add(preferencesDialog.soundBeepRB);
    	preferencesDialog.soundGroup.add(preferencesDialog.soundCustomRB);
    	preferencesDialog.jPanel1.add(preferencesDialog.jPanel3, null);
    	preferencesDialog.jPanel3.add(preferencesDialog.soundFile, BorderLayout.CENTER);
    	preferencesDialog.jPanel3.add(preferencesDialog.soundFileBrowseB, BorderLayout.EAST);
    	preferencesDialog.jPanel3.add(preferencesDialog.jLabel6, BorderLayout.WEST);
    	preferencesDialog.GeneralPanel.add(preferencesDialog.jLabel1, preferencesDialog.gbc);
    	preferencesDialog.minGroup.add(preferencesDialog.minTaskbarRB);
    	preferencesDialog.minTaskbarRB.setSelected(true);
    	preferencesDialog.minTaskbarRB.setText(Local.getString("Minimize to taskbar"));
    	preferencesDialog.minTaskbarRB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.minTaskbarRB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 0;
    	preferencesDialog.gbc.insets = new Insets(10, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.minTaskbarRB, preferencesDialog.gbc);
    	preferencesDialog.minGroup.add(preferencesDialog.minHideRB);
    	preferencesDialog.minHideRB.setText(Local.getString("Hide"));
    	preferencesDialog.minHideRB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.minHideRB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 1;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.minHideRB, preferencesDialog.gbc);
    	preferencesDialog.jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    	preferencesDialog.jLabel2.setText(Local.getString("Window close action:"));
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 0;
    	preferencesDialog.gbc.gridy = 2;
    	preferencesDialog.gbc.insets = new Insets(2, 10, 0, 15);
    	preferencesDialog.gbc.anchor = GridBagConstraints.EAST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.jLabel2, preferencesDialog.gbc);
    	preferencesDialog.closeGroup.add(preferencesDialog.closeExitRB);
    	preferencesDialog.closeExitRB.setSelected(true);
    	preferencesDialog.closeExitRB.setText(Local.getString("Close and exit"));
    	preferencesDialog.closeExitRB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.closeExitRB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 2;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.closeExitRB, preferencesDialog.gbc);
    
    	preferencesDialog.closeGroup.add(preferencesDialog.closeHideRB);
    	preferencesDialog.closeHideRB.setText(Local.getString("Hide"));
    	preferencesDialog.closeHideRB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.closeHideRB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 3;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.closeHideRB, preferencesDialog.gbc);
    	preferencesDialog.jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    	preferencesDialog.jLabel3.setText(Local.getString("Look and feel:"));
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 0;
    	preferencesDialog.gbc.gridy = 4;
    	preferencesDialog.gbc.insets = new Insets(2, 10, 0, 15);
    	preferencesDialog.gbc.anchor = GridBagConstraints.EAST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.jLabel3, preferencesDialog.gbc);
    
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 4;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 5;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.lfSystemRB, preferencesDialog.gbc);
    
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 6;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.lfJavaRB, preferencesDialog.gbc);
    	preferencesDialog.lfGroup.add(preferencesDialog.lfCustomRB);
    	preferencesDialog.lfCustomRB.setText(Local.getString("Custom"));
    	preferencesDialog.lfCustomRB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.lfCustomRB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 7;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.lfCustomRB, preferencesDialog.gbc);
    	preferencesDialog.classNameLabel.setEnabled(false);
    	preferencesDialog.classNameLabel.setText(Local.getString("L&F class name:"));
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 8;
    	preferencesDialog.gbc.insets = new Insets(2, 20, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.classNameLabel, preferencesDialog.gbc);
    	preferencesDialog.lfClassName.setEnabled(false);
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 9;
    	preferencesDialog.gbc.insets = new Insets(7, 20, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.gbc.fill = GridBagConstraints.HORIZONTAL;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.lfClassName, preferencesDialog.gbc);
    	preferencesDialog.jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
    	preferencesDialog.jLabel4.setText(Local.getString("Startup:"));
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 0;
    	preferencesDialog.gbc.gridy = 10;
    	preferencesDialog.gbc.insets = new Insets(2, 10, 0, 15);
    	preferencesDialog.gbc.anchor = GridBagConstraints.EAST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.jLabel4, preferencesDialog.gbc);
    	preferencesDialog.enSystrayChB.setText(Local.getString("Enable system tray icon"));
    	preferencesDialog.enSystrayChB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.enSystrayChB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 10;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.enSystrayChB, preferencesDialog.gbc);
    	preferencesDialog.startMinimizedChB.setText(Local.getString("Start minimized"));
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 11;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.startMinimizedChB, preferencesDialog.gbc);
    	preferencesDialog.enSplashChB.setText(Local.getString("Show splash screen"));
    	preferencesDialog.enSplashChB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.enSplashChB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 12;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.enSplashChB, preferencesDialog.gbc);
    	preferencesDialog.enL10nChB.setText(Local.getString("Enable localization"));
    	preferencesDialog.enL10nChB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.enL10nChB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 13;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.enL10nChB, preferencesDialog.gbc);
    	preferencesDialog.firstdow.setText(Local.getString("First day of week - Monday"));
    	preferencesDialog.firstdow.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    		}
    	});
    	
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 14;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.firstdow, preferencesDialog.gbc);
    	preferencesDialog.lblExit.setHorizontalAlignment(SwingConstants.RIGHT);
    	preferencesDialog.lblExit.setText(Local.getString("Exit") + ":");
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 0;
    	preferencesDialog.gbc.gridy = 15;
    	preferencesDialog.gbc.insets = new Insets(2, 10, 10, 15);
    	preferencesDialog.gbc.anchor = GridBagConstraints.EAST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.lblExit, preferencesDialog.gbc);
    	preferencesDialog.askConfirmChB.setSelected(true);
    	preferencesDialog.askConfirmChB.setText(Local.getString("Ask confirmation"));
    	
    	
    	preferencesDialog.askConfirmChB.addActionListener(new java.awt.event.ActionListener() {
    	
    	    public void actionPerformed(ActionEvent e) {
    			preferencesDialog.askConfirmChB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 15;
    	preferencesDialog.gbc.insets = new Insets(2, 0, 10, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.GeneralPanel.add(preferencesDialog.askConfirmChB, preferencesDialog.gbc);
    
    	// Build Tab2
    	preferencesDialog.rstPanelBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    	setBorder(preferencesDialog.rstPanelBorder);
    	preferencesDialog.resourcePanel.add(this, BorderLayout.CENTER);
    	preferencesDialog.rsbpBorder = new TitledBorder(BorderFactory.createEmptyBorder(5, 5, 5,
    			5), Local.getString("Web browser executable"));
    	preferencesDialog.rsBottomPanel.setBorder(preferencesDialog.rsbpBorder);
    	preferencesDialog.jLabel5.setText(Local.getString("Path") + ":");
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 0;
    	preferencesDialog.gbc.gridy = 0;
    	preferencesDialog.gbc.insets = new Insets(0, 5, 0, 5);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.rsBottomPanel.add(preferencesDialog.jLabel5, preferencesDialog.gbc);
    	preferencesDialog.browserPath.setPreferredSize(new Dimension(250, 25));
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 1;
    	preferencesDialog.gbc.gridy = 0;
    	preferencesDialog.gbc.insets = new Insets(0, 5, 0, 10);
    	preferencesDialog.gbc.anchor = GridBagConstraints.WEST;
    	preferencesDialog.gbc.fill = GridBagConstraints.HORIZONTAL;
    	preferencesDialog.rsBottomPanel.add(preferencesDialog.browserPath, preferencesDialog.gbc);
    	preferencesDialog.browseB.setText(Local.getString("Browse"));
    	preferencesDialog.browseB.setPreferredSize(new Dimension(110, 25));
    	preferencesDialog.browseB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.browseB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.gbc = new GridBagConstraints();
    	preferencesDialog.gbc.gridx = 2;
    	preferencesDialog.gbc.gridy = 0;
    	// gbc.insets = new Insets(0, 0, 0, 0);
    	preferencesDialog.gbc.anchor = GridBagConstraints.EAST;
    	preferencesDialog.rsBottomPanel.add(preferencesDialog.browseB, preferencesDialog.gbc);
    
    	preferencesDialog.resourcePanel.add(preferencesDialog.rsBottomPanel, BorderLayout.SOUTH);
    	
    	// Build editorConfigPanel
    	preferencesDialog.normalFontLabel.setText(Local.getString("Normal text font"));
    	preferencesDialog.normalFontLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    	preferencesDialog.headerFontLabel.setText(Local.getString("Header font"));
    	preferencesDialog.headerFontLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    	preferencesDialog.monoFontLabel.setText(Local.getString("Monospaced font"));
    	preferencesDialog.monoFontLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    	preferencesDialog.baseFontSizeLabel.setText(Local.getString("Base font size"));
    	preferencesDialog.baseFontSizeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    	preferencesDialog.antialiasChB.setText(Local.getString("Antialias text"));
    	JPanel bfsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
    	bfsPanel.add(preferencesDialog.baseFontSize);
    	preferencesDialog.econfPanel.add(preferencesDialog.normalFontLabel);
    	preferencesDialog.econfPanel.add(preferencesDialog.normalFontCB);
    	preferencesDialog.econfPanel.add(preferencesDialog.headerFontLabel);
    	preferencesDialog.econfPanel.add(preferencesDialog.headerFontCB);
    	preferencesDialog.econfPanel.add(preferencesDialog.monoFontLabel);
    	preferencesDialog.econfPanel.add(preferencesDialog.monoFontCB);
    	preferencesDialog.econfPanel.add(preferencesDialog.baseFontSizeLabel);
    	preferencesDialog.econfPanel.add(bfsPanel);
    	preferencesDialog.econfPanel.add(preferencesDialog.antialiasChB);
    	preferencesDialog.econfPanel.setBorder(BorderFactory.createEmptyBorder(10,5,10,10));
    	((GridLayout)preferencesDialog.econfPanel.getLayout()).setHgap(10);
    	((GridLayout)preferencesDialog.econfPanel.getLayout()).setVgap(5);
    	preferencesDialog.editorConfigPanel.add(preferencesDialog.econfPanel, BorderLayout.NORTH);
    	// Build TabbedPanel
    	preferencesDialog.tabbedPanel.add(preferencesDialog.GeneralPanel, Local.getString("General"));
    	preferencesDialog.tabbedPanel.add(preferencesDialog.resourcePanel, Local.getString("Resource types"));
    	preferencesDialog.tabbedPanel.add(preferencesDialog.soundPanel, Local.getString("Sound"));
    	preferencesDialog.tabbedPanel.add(preferencesDialog.editorConfigPanel, Local.getString("Editor"));
    
    	// Build TopPanel
    	preferencesDialog.topPanel.add(preferencesDialog.tabbedPanel, BorderLayout.CENTER);
    
    	// Build BottomPanel
    	preferencesDialog.okB.setMaximumSize(new Dimension(100, 25));
    	preferencesDialog.okB.setPreferredSize(new Dimension(100, 25));
    	preferencesDialog.okB.setText(Local.getString("Ok"));
    	preferencesDialog.okB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.okB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.getRootPane().setDefaultButton(preferencesDialog.okB);
    	preferencesDialog.bottomPanel.add(preferencesDialog.okB);
    	preferencesDialog.cancelB.setMaximumSize(new Dimension(100, 25));
    	preferencesDialog.cancelB.setPreferredSize(new Dimension(100, 25));
    	preferencesDialog.cancelB.setText(Local.getString("Cancel"));
    	preferencesDialog.cancelB.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			preferencesDialog.cancelB_actionPerformed(e);
    		}
    	});
    	preferencesDialog.bottomPanel.add(preferencesDialog.cancelB);
    
    	// Build Preferences-Dialog
    	preferencesDialog.getContentPane().add(preferencesDialog.topPanel, BorderLayout.NORTH);
    	preferencesDialog.getContentPane().add(preferencesDialog.bottomPanel, BorderLayout.SOUTH);
    	preferencesDialog.soundPanel.add(preferencesDialog.enableSoundCB, BorderLayout.NORTH);
    
    	// set all config-values
    	preferencesDialog.setValues();
    
    }



}